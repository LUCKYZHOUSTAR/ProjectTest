/**
 * 
 */
package DistributeDataBase.rule.ruleengine.enumerator;

import java.util.HashSet;
import java.util.Set;

import DistributeDataBase.common.sqljep.function.Comparative;

/** 
* @ClassName: IntegerPartDiscontinousRangeEnumerator 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午7:09:48 
*  
*/
public class IntegerPartDiscontinousRangeEnumerator extends PartDiscontinousRangeEnumerator {
    private static final int LIMIT_UNIT_OF_INT    = 1;
    public static final int  DEFAULT_ATOMIC_VALUE = 1;

    protected Comparative changeGreater2GreaterOrEq(Comparative from) {
        if (from.getComparison() == 1) {
            int fromComparable = ((Integer) from.getValue()).intValue();

            return new Comparative(2, Integer.valueOf(fromComparable + 1));
        }

        return from;
    }

    protected Comparative changeLess2LessOrEq(Comparative to) {
        if (to.getComparison() == 7) {
            int toComparable = ((Integer) to.getValue()).intValue();

            return new Comparative(8, Integer.valueOf(toComparable - 1));
        }

        return to;
    }

    protected Comparable getOneStep(Comparable source, Comparable atomIncVal) {
        if (atomIncVal == null) {
            atomIncVal = Integer.valueOf(1);
        }
        int sourceInt = ((Integer) source).intValue();

        int atomIncValInt = ((Integer) atomIncVal).intValue();

        return Integer.valueOf(sourceInt + atomIncValInt);
    }

    protected boolean inputCloseRangeGreaterThanMaxFieldOfDifination(Comparable from,
                                                                     Comparable to,
                                                                     Integer cumulativeTimes,
                                                                     Comparable<?> atomIncrValue) {
        if (cumulativeTimes == null) {
            return false;
        }
        if (atomIncrValue == null) {
            atomIncrValue = Integer.valueOf(1);
        }
        int fromInt = ((Integer) from).intValue();
        int toInt = ((Integer) to).intValue();
        int atomIncValInt = ((Integer) atomIncrValue).intValue();
        int size = cumulativeTimes.intValue();

        if (toInt - fromInt > atomIncValInt * size) {
            return true;
        }
        return false;
    }

    protected Set<Object> getAllPassableFields(Comparative begin, Integer cumulativeTimes,
                                               Comparable<?> atomicIncreationValue) {
        if (cumulativeTimes == null) {
            throw new IllegalStateException(
                "在没有提供叠加次数的前提下，不能够根据当前范围条件选出对应的定义域的枚举值，sql中不要出现> < >= <=");
        }

        if (atomicIncreationValue == null) {
            atomicIncreationValue = Integer.valueOf(1);
        }

        begin = changeGreater2GreaterOrEq(begin);
        begin = changeLess2LessOrEq(begin);

        Set returnSet = new HashSet(cumulativeTimes.intValue());
        int beginInt = ((Integer) begin.getValue()).intValue();
        int comparasion = begin.getComparison();

        int atomicIncreateValueInt = ((Integer) atomicIncreationValue).intValue();
        if (comparasion == 2) {
            for (int i = 0; i < cumulativeTimes.intValue(); i++)
                returnSet.add(Integer.valueOf(beginInt + atomicIncreateValueInt * i));
        } else if (comparasion == 8) {
            for (int i = 0; i < cumulativeTimes.intValue(); i++) {
                returnSet.add(Integer.valueOf(beginInt - atomicIncreateValueInt * i));
            }
        }
        return returnSet;
    }

    public void processAllPassableFields(Comparative source, Set<Object> retValue,
                                         Integer cumulativeTimes, Comparable<?> atomIncrValue) {
        retValue.addAll(getAllPassableFields(source, cumulativeTimes, atomIncrValue));
    }
}
