/**
 * 
 */
package DistributeDataBase.rule.ruleengine.enumerator;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import DistributeDataBase.common.sqljep.function.Comparative;
import DistributeDataBase.common.util.NestThreadLocalMap;
import DistributeDataBase.rule.ruleengine.rule.DateEnumerationParameter;

/** 
* @ClassName: DatePartDiscontinousRangeEnumerator 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午7:11:24 
*  
*/
public class DatePartDiscontinousRangeEnumerator extends PartDiscontinousRangeEnumerator {
    private static final long   LIMIT_UNIT_OF_DATE        = 1L;
    private static final String DATE_ENUMERATOR           = "DATE_ENUMERATOR";
    private static final int    DEFAULT_DATE_ATOMIC_VALUE = 1;

    protected Comparative changeGreater2GreaterOrEq(Comparative from) {
        if (from.getComparison() == 1) {
            Date gtDate = (Date) from.getValue();
            long gtOrEqDate = gtDate.getTime() + 1L;
            return new Comparative(2, new Date(gtOrEqDate));
        }

        return from;
    }

    protected Comparative changeLess2LessOrEq(Comparative to) {
        if (to.getComparison() == 7) {
            Date less = (Date) to.getValue();
            long lessOrEqDate = less.getTime() - 1L;
            return new Comparative(8, new Date(lessOrEqDate));
        }
        return to;
    }

    protected Comparable getOneStep(Comparable source, Comparable atomIncVal) {
        DateEnumerationParameter dateEnumerationParameter = getDateEnumerationParameter(atomIncVal);
        Calendar cal = getCalendar((Date) source);
        cal.add(dateEnumerationParameter.calendarFieldType,
            dateEnumerationParameter.atomicIncreatementNumber);

        return cal.getTime();
    }

    protected DateEnumerationParameter getDateEnumerationParameter(Comparable<?> comparable) {
        DateEnumerationParameter dateEnumerationParameter = null;
        if (comparable == null)
            dateEnumerationParameter = new DateEnumerationParameter(1);
        else if ((comparable instanceof Integer))
            dateEnumerationParameter = new DateEnumerationParameter(
                ((Integer) comparable).intValue());
        else if ((comparable instanceof DateEnumerationParameter))
            dateEnumerationParameter = (DateEnumerationParameter) comparable;
        else {
            throwNotSupportIllegalArgumentException(comparable);
        }
        return dateEnumerationParameter;
    }

    private Calendar getCalendar(Date date2BeSet) {
        Calendar cal = (Calendar) NestThreadLocalMap.get("DATE_ENUMERATOR");
        if (cal == null) {
            cal = Calendar.getInstance();
            NestThreadLocalMap.put("DATE_ENUMERATOR", cal);
        }
        cal.setTime(date2BeSet);
        return cal;
    }

    protected boolean inputCloseRangeGreaterThanMaxFieldOfDifination(Comparable from,
                                                                     Comparable to,
                                                                     Integer cumulativeTimes,
                                                                     Comparable<?> atomIncrValue) {
        if (cumulativeTimes == null) {
            return false;
        }
        Calendar cal = getCalendar((Date) from);
        int rangeSet = cumulativeTimes.intValue();
        if ((atomIncrValue instanceof Integer)) {
            cal.add(5, rangeSet * ((Integer) atomIncrValue).intValue());
        } else if ((atomIncrValue instanceof DateEnumerationParameter)) {
            DateEnumerationParameter dep = (DateEnumerationParameter) atomIncrValue;
            cal.add(dep.calendarFieldType, rangeSet * dep.atomicIncreatementNumber);
        } else if (atomIncrValue == null) {
            cal.add(5, rangeSet);
        } else {
            throwNotSupportIllegalArgumentException(atomIncrValue);
        }

        if (to.compareTo(cal.getTime()) >= 0) {
            return true;
        }
        return false;
    }

    private void throwNotSupportIllegalArgumentException(Object arg) {
        throw new IllegalArgumentException("不能识别的类型:" + arg + " .type is" + arg.getClass());
    }

    protected Set<Object> getAllPassableFields(Comparative begin, Integer cumulativeTimes,
                                               Comparable<?> atomicIncreationValue) {
        if (cumulativeTimes == null) {
            return Collections.emptySet();
        }
        Set returnSet = new HashSet(cumulativeTimes.intValue());
        DateEnumerationParameter dateEnumerationParameter = getDateEnumerationParameter(atomicIncreationValue);

        begin = changeGreater2GreaterOrEq(begin);
        begin = changeLess2LessOrEq(begin);

        Calendar cal = getCalendar((Date) begin.getValue());
        int comparasion = begin.getComparison();

        if (comparasion == 2) {
            returnSet.add(cal.getTime());

            for (int i = 0; i < cumulativeTimes.intValue() - 1; i++) {
                cal.add(dateEnumerationParameter.calendarFieldType,
                    dateEnumerationParameter.atomicIncreatementNumber);

                returnSet.add(cal.getTime());
            }
        } else if (comparasion == 8) {
            returnSet.add(cal.getTime());
            for (int i = 0; i < cumulativeTimes.intValue() - 1; i++) {
                cal.add(dateEnumerationParameter.calendarFieldType,
                    -dateEnumerationParameter.atomicIncreatementNumber);

                returnSet.add(cal.getTime());
            }
        }

        return returnSet;
    }

    public void processAllPassableFields(Comparative source, Set<Object> retValue,
                                         Integer cumulativeTimes, Comparable<?> atomIncrValue) {
        retValue.addAll(getAllPassableFields(source, cumulativeTimes, atomIncrValue));
    }
}
