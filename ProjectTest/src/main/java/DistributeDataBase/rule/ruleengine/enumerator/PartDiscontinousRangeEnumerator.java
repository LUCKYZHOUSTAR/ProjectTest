/**
 * 
 */
package DistributeDataBase.rule.ruleengine.enumerator;

import java.util.Set;

import DistributeDataBase.common.sqljep.function.Comparative;

/** 
* @ClassName: PartDiscontinousRangeEnumerator 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午7:08:49 
*  
*/
public abstract class PartDiscontinousRangeEnumerator implements
                                                     CloseIntervalFieldsEnumeratorHandler {
    protected abstract Comparable getOneStep(Comparable paramComparable1,
                                             Comparable paramComparable2);

    protected abstract Comparative changeGreater2GreaterOrEq(Comparative paramComparative);

    protected abstract Comparative changeLess2LessOrEq(Comparative paramComparative);

    protected abstract boolean inputCloseRangeGreaterThanMaxFieldOfDifination(Comparable paramComparable1,
                                                                              Comparable paramComparable2,
                                                                              Integer paramInteger,
                                                                              Comparable<?> paramComparable);

    protected abstract Set<Object> getAllPassableFields(Comparative paramComparative,
                                                        Integer paramInteger,
                                                        Comparable<?> paramComparable);

    public void mergeFeildOfDefinitionInCloseInterval(Comparative from, Comparative to,
                                                      Set<Object> retValue,
                                                      Integer cumulativeTimes,
                                                      Comparable<?> atomIncrValue) {
        if ((cumulativeTimes == null) || (atomIncrValue == null)) {
            throw new IllegalArgumentException("当原子增参数或叠加参数为空时，不支持在sql中使用范围选择，如id>? and id<?");
        }
        from = changeGreater2GreaterOrEq(from);

        to = changeLess2LessOrEq(to);

        Comparable fromComparable = from.getValue();
        Comparable toComparable = to.getValue();

        if (inputCloseRangeGreaterThanMaxFieldOfDifination(fromComparable, toComparable,
            cumulativeTimes, atomIncrValue)) {
            if (retValue != null) {
                retValue.addAll(getAllPassableFields(from, cumulativeTimes, atomIncrValue));
                return;
            }
            throw new IllegalArgumentException("待写入的参数set为null");
        }

        if (fromComparable.compareTo(toComparable) == 0) {
            retValue.add(fromComparable);
            return;
        }

        int rangeSize = cumulativeTimes.intValue();

        retValue.add(fromComparable);
        Comparable enumedFoD = fromComparable;
        for (int i = 0; i < rangeSize; i++) {
            enumedFoD = getOneStep(enumedFoD, atomIncrValue);
            int compareResult = enumedFoD.compareTo(toComparable);
            if (compareResult == 0) {
                retValue.add(toComparable);
                return;
            }
            if (compareResult > 0) {
                retValue.add(toComparable);
                return;
            }

            retValue.add(enumedFoD);
        }
    }
}