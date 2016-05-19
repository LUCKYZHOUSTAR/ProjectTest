/**
 * 
 */
package DistributeDataBase.rule.ruleengine.enumerator;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import DistributeDataBase.common.sqljep.function.Comparative;

/** 
* @ClassName: LongPartDiscontinousRangeEnumerator 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午7:09:20 
*  
*/
public class LongPartDiscontinousRangeEnumerator extends PartDiscontinousRangeEnumerator {
    private static final long LIMIT_UNIT_OF_LONG        = 1L;
    private static final long DEFAULT_LONG_ATOMIC_VALUE = 1L;

    protected Comparative changeGreater2GreaterOrEq(Comparative from) {
        if (from.getComparison() == 1) {
            long fromComparable = ((Long) from.getValue()).longValue();

            return new Comparative(2, Long.valueOf(fromComparable + 1L));
        }

        return from;
    }

    protected Comparative changeLess2LessOrEq(Comparative to) {
        if (to.getComparison() == 7) {
            long toComparable = ((Long) to.getValue()).longValue();

            return new Comparative(8, Long.valueOf(toComparable - 1L));
        }

        return to;
    }

    protected Comparable getOneStep(Comparable source, Comparable atomIncVal) {
        if (atomIncVal == null) {
            atomIncVal = Long.valueOf(1L);
        }
        long sourceLong = ((Long) source).longValue();

        int atomIncValLong = ((Integer) atomIncVal).intValue();

        return Long.valueOf(sourceLong + atomIncValLong);
    }

    protected boolean inputCloseRangeGreaterThanMaxFieldOfDifination(Comparable from,
                                                                     Comparable to,
                                                                     Integer cumulativeTimes,
                                                                     Comparable<?> atomIncrValue) {
        if (cumulativeTimes == null) {
            return false;
        }
        long fromLong = ((Long) from).longValue();
        long toLong = ((Long) to).longValue();
        int atomIncValLong = ((Integer) atomIncrValue).intValue();
        int size = cumulativeTimes.intValue();

        if (toLong - fromLong > atomIncValLong * size) {
            return true;
        }
        return false;
    }

    protected Set<Object> getAllPassableFields(Comparative begin, Integer cumulativeTimes,
                                               Comparable<?> atomicIncreationValue) {
        if (cumulativeTimes == null) {
            return Collections.emptySet();
        }
        if (atomicIncreationValue == null) {
            atomicIncreationValue = Long.valueOf(1L);
        }

        begin = changeGreater2GreaterOrEq(begin);
        begin = changeLess2LessOrEq(begin);

        Set returnSet = new HashSet(cumulativeTimes.intValue());
        long beginInt = ((Long) begin.getValue()).longValue();
        long atomicIncreateValueInt = ((Integer) atomicIncreationValue).intValue();
        int comparasion = begin.getComparison();

        if (comparasion == 2) {
            for (int i = 0; i < cumulativeTimes.intValue(); i++)
                returnSet.add(Long.valueOf(beginInt + atomicIncreateValueInt * i));
        } else if (comparasion == 8) {
            for (int i = 0; i < cumulativeTimes.intValue(); i++) {
                returnSet.add(Long.valueOf(beginInt - atomicIncreateValueInt * i));
            }
        }
        return returnSet;
    }

    public void processAllPassableFields(Comparative source, Set<Object> retValue,
                                         Integer cumulativeTimes, Comparable<?> atomIncrValue) {
        retValue.addAll(getAllPassableFields(source, cumulativeTimes, atomIncrValue));
    }
}