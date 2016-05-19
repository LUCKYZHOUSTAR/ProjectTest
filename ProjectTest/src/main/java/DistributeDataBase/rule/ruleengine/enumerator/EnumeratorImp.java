/**
 * 
 */
package DistributeDataBase.rule.ruleengine.enumerator;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import DistributeDataBase.common.exception.runtime.NotSupportException;
import DistributeDataBase.common.sqljep.function.Comparative;
import DistributeDataBase.common.sqljep.function.ComparativeAND;
import DistributeDataBase.common.sqljep.function.ComparativeBaseList;
import DistributeDataBase.common.sqljep.function.ComparativeOR;

/** 
* @ClassName: EnumeratorImp 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午7:10:08 
*  
*/
public class EnumeratorImp implements Enumerator {
    private static final String                                              DEFAULT_ENUMERATOR = "DEFAULT_ENUMERATOR";
    protected static final Map<String, CloseIntervalFieldsEnumeratorHandler> enumeratorMap      = new HashMap();
    private boolean                                                          isDebug;

    public EnumeratorImp() {
        enumeratorMap.put(Integer.class.getName(), new IntegerPartDiscontinousRangeEnumerator());
        enumeratorMap.put(Long.class.getName(), new LongPartDiscontinousRangeEnumerator());
        enumeratorMap
            .put(java.util.Date.class.getName(), new DatePartDiscontinousRangeEnumerator());
        enumeratorMap.put(java.sql.Date.class.getName(), new DatePartDiscontinousRangeEnumerator());
        enumeratorMap.put(Timestamp.class.getName(), new DatePartDiscontinousRangeEnumerator());

        enumeratorMap.put("DEFAULT_ENUMERATOR", new DefaultEnumerator());

        this.isDebug = false;
    }

    public CloseIntervalFieldsEnumeratorHandler getCloseIntervalEnumeratorHandlerByComparative(Comparative comp,
                                                                                               boolean needMergeValueInCloseInterval) {
        if (!needMergeValueInCloseInterval) {
            return (CloseIntervalFieldsEnumeratorHandler) enumeratorMap.get("DEFAULT_ENUMERATOR");
        }
        if (comp == null) {
            throw new IllegalArgumentException("不知道当前值是什么类型的，无法找到对应的枚举器" + comp);
        }

        Comparable value = comp.getValue();

        if ((value instanceof ComparativeBaseList)) {
            ComparativeBaseList comparativeBaseList = (ComparativeBaseList) value;
            Iterator i$ = comparativeBaseList.getList().iterator();
            if (i$.hasNext()) {
                Comparative comparative = (Comparative) i$.next();
                return getCloseIntervalEnumeratorHandlerByComparative(comparative,
                    needMergeValueInCloseInterval);
            }

            throw new IllegalStateException("should not be here");
        }
        if ((value instanceof Comparative)) {
            return getCloseIntervalEnumeratorHandlerByComparative(comp,
                needMergeValueInCloseInterval);
        }

        CloseIntervalFieldsEnumeratorHandler enumeratorHandler = (CloseIntervalFieldsEnumeratorHandler) enumeratorMap
            .get(value.getClass().getName());

        if (enumeratorHandler != null) {
            return enumeratorHandler;
        }
        return (CloseIntervalFieldsEnumeratorHandler) enumeratorMap.get("DEFAULT_ENUMERATOR");
    }

    public Set<Object> getEnumeratedValue(Comparable condition, Integer cumulativeTimes,
                                          Comparable<?> atomIncrValue,
                                          boolean needMergeValueInCloseInterval) {
        Set retValue = null;
        if (!this.isDebug)
            retValue = new HashSet();
        else
            retValue = new TreeSet();
        try {
            process(condition, retValue, cumulativeTimes, atomIncrValue,
                needMergeValueInCloseInterval);
        } catch (EnumerationInterruptException e) {
            processAllPassableFields(e.getComparative(), retValue, cumulativeTimes, atomIncrValue,
                needMergeValueInCloseInterval);
        }

        return retValue;
    }

    protected void process(Comparable<?> condition, Set<Object> retValue, Integer cumulativeTimes,
                           Comparable<?> atomIncrValue, boolean needMergeValueInCloseInterval) {
        if (condition == null)
            retValue.add(null);
        else if ((condition instanceof ComparativeOR)) {
            processComparativeOR(condition, retValue, cumulativeTimes, atomIncrValue,
                needMergeValueInCloseInterval);
        } else if ((condition instanceof ComparativeAND)) {
            processComparativeAnd(condition, retValue, cumulativeTimes, atomIncrValue,
                needMergeValueInCloseInterval);
        } else if ((condition instanceof Comparative)) {
            processComparativeOne(condition, retValue, cumulativeTimes, atomIncrValue,
                needMergeValueInCloseInterval);
        } else
            retValue.add(condition);
    }

    private boolean containsEquvilentRelation(Comparative comp) {
        int comparasion = comp.getComparison();

        if ((comparasion == 3) || (comparasion == 2) || (comparasion == 8)) {
            return true;
        }
        return false;
    }

    private void processComparativeAnd(Comparable<?> condition, Set<Object> retValue,
                                       Integer cumulativeTimes, Comparable<?> atomIncrValue,
                                       boolean needMergeValueInCloseInterval) {
        List andList = ((ComparativeAND) condition).getList();

        if (andList.size() == 2) {
            Comparable arg1 = (Comparable) andList.get(0);
            Comparable arg2 = (Comparable) andList.get(1);

            Comparative compArg1 = valid2varableInAndIsNotComparativeBaseList(arg1);

            Comparative compArg2 = valid2varableInAndIsNotComparativeBaseList(arg2);

            int compResult = 0;
            try {
                compResult = compArg1.getValue().compareTo(compArg2.getValue());
            } catch (NullPointerException e) {
                throw new RuntimeException("and条件中有一个值为null", e);
            }

            if (compResult == 0) {
                if ((containsEquvilentRelation(compArg1)) && (containsEquvilentRelation(compArg2))) {
                    retValue.add(compArg1.getValue());
                }

            } else if (compResult < 0) {
                processTwoDifferentArgsInComparativeAnd(retValue, compArg1, compArg2,
                    cumulativeTimes, atomIncrValue, needMergeValueInCloseInterval);
            } else {
                processTwoDifferentArgsInComparativeAnd(retValue, compArg2, compArg1,
                    cumulativeTimes, atomIncrValue, needMergeValueInCloseInterval);
            }
        } else {
            throw new IllegalArgumentException("目前只支持一个and节点上有两个子节点");
        }
    }

    private void processTwoDifferentArgsInComparativeAnd(Set<Object> retValue, Comparative from,
                                                         Comparative to, Integer cumulativeTimes,
                                                         Comparable<?> atomIncrValue,
                                                         boolean needMergeValueInCloseInterval) {
        if (isCloseInterval(from, to)) {
            mergeFeildOfDefinitionInCloseInterval(from, to, retValue, cumulativeTimes,
                atomIncrValue, needMergeValueInCloseInterval);
        } else {
            Comparable temp = compareAndGetIntersactionOneValue(from, to);
            if (temp != null) {
                retValue.add(temp);
            } else if ((from.getComparison() == 8) || (from.getComparison() == 7)) {
                if ((to.getComparison() == 8) || (to.getComparison() == 7)) {
                    processAllPassableFields(from, retValue, cumulativeTimes, atomIncrValue,
                        needMergeValueInCloseInterval);
                }

            } else if ((to.getComparison() == 2) || (to.getComparison() == 1)) {
                if ((from.getComparison() == 2) || (from.getComparison() == 1)) {
                    processAllPassableFields(to, retValue, cumulativeTimes, atomIncrValue,
                        needMergeValueInCloseInterval);
                }

            } else {
                throw new IllegalArgumentException("should not be here");
            }
        }
    }

    protected static Comparable compareAndGetIntersactionOneValue(Comparative from, Comparative to) {
        if ((from.getComparison() == 3) && ((to.getComparison() == 7) || (to.getComparison() == 8))) {
            return from.getValue();
        }

        if ((to.getComparison() == 3)
            && ((from.getComparison() == 1) || (from.getComparison() == 2))) {
            return to.getValue();
        }

        return null;
    }

    protected static boolean isCloseInterval(Comparative from, Comparative to) {
        int fromComparasion = from.getComparison();

        int toComparasion = to.getComparison();

        if (((fromComparasion == 1) || (fromComparasion == 2))
            && ((toComparasion == 7) || (toComparasion == 8))) {
            return true;
        }
        return false;
    }

    private Comparative valid2varableInAndIsNotComparativeBaseList(Comparable<?> arg) {
        if ((arg instanceof ComparativeBaseList)) {
            throw new IllegalArgumentException("在一组and条件中只支持两个范围的值共同决定分表，不支持3个");
        }

        if ((arg instanceof Comparative)) {
            Comparative comp = (Comparative) arg;
            int comparison = comp.getComparison();

            if (comparison == 0) {
                return valid2varableInAndIsNotComparativeBaseList(comp.getValue());
            }

            return comp;
        }

        throw new IllegalArgumentException("input value is not a comparative: " + arg);
    }

    private void processComparativeOne(Comparable<?> condition, Set<Object> retValue,
                                       Integer cumulativeTimes, Comparable<?> atomIncrValue,
                                       boolean needMergeValueInCloseInterval) {
        Comparative comp = (Comparative) condition;
        int comparison = comp.getComparison();
        switch (comparison) {
            case 0:
                process(comp.getValue(), retValue, cumulativeTimes, atomIncrValue,
                    needMergeValueInCloseInterval);

                break;
            case 3:
                retValue.add(comp.getValue());
                break;
            case 1:
            case 2:
            case 7:
            case 8:
                throw new EnumerationInterruptException(comp);
            case 4:
            case 5:
            case 6:
            default:
                throw new NotSupportException("not support yet");
        }
    }

    private void processComparativeOR(Comparable<?> condition, Set<Object> retValue,
                                      Integer cumulativeTimes, Comparable<?> atomIncrValue,
                                      boolean needMergeValueInCloseInterval) {
        List<Comparative> orList = ((ComparativeOR) condition).getList();

        for (Comparative comp : orList) {
            process(comp, retValue, cumulativeTimes, atomIncrValue, needMergeValueInCloseInterval);
        }
    }

    protected void mergeFeildOfDefinitionInCloseInterval(Comparative from, Comparative to,
                                                         Set<Object> retValue,
                                                         Integer cumulativeTimes,
                                                         Comparable<?> atomIncrValue,
                                                         boolean needMergeValueInCloseInterval) {
        if (!needMergeValueInCloseInterval) {
            throw new IllegalArgumentException(
                "请打开规则的needMergeValueInCloseInterval选项，以支持分库分表条件中使用> < >= <=");
        }

        CloseIntervalFieldsEnumeratorHandler closeIntervalFieldsEnumeratorHandler = getCloseIntervalEnumeratorHandlerByComparative(
            from, needMergeValueInCloseInterval);

        closeIntervalFieldsEnumeratorHandler.mergeFeildOfDefinitionInCloseInterval(from, to,
            retValue, cumulativeTimes, atomIncrValue);
    }

    protected void processAllPassableFields(Comparative source, Set<Object> retValue,
                                            Integer cumulativeTimes, Comparable<?> atomIncrValue,
                                            boolean needMergeValueInCloseInterval) {
        if (!needMergeValueInCloseInterval) {
            throw new IllegalArgumentException(
                "请打开规则的needMergeValueInCloseInterval选项，以支持分库分表条件中使用> < >= <=");
        }

        CloseIntervalFieldsEnumeratorHandler closeIntervalFieldsEnumeratorHandler = getCloseIntervalEnumeratorHandlerByComparative(
            source, needMergeValueInCloseInterval);

        closeIntervalFieldsEnumeratorHandler.processAllPassableFields(source, retValue,
            cumulativeTimes, atomIncrValue);
    }

    public boolean isDebug() {
        return this.isDebug;
    }

    public void setDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }
}
