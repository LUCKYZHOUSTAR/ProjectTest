/**
 * 
 */
package DistributeDataBase.rule.groovy.staticmethod;

import java.util.Calendar;
import java.util.Date;

import DistributeDataBase.common.util.NestThreadLocalMap;

/** 
* @ClassName: GroovyStaticMethod 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午6:52:32 
*  
*/
public class GroovyStaticMethod {
    public static final String  GROOVY_STATIC_METHOD_CALENDAR = "GROOVY_STATIC_METHOD_CALENDAR";
    private static final long[] pow10                         = { 1L, 10L, 100L, 1000L, 10000L,
            100000L, 1000000L, 10000000L, 100000000L, 1000000000L, 10000000000L, 100000000000L,
            1000000000000L, 10000000000000L, 100000000000000L, 1000000000000000L,
            10000000000000000L, 100000000000000000L, 1000000000000000000L };

    public static int dayofweek(Date date, int offset) {
        Calendar cal = getCalendar();
        cal.setTime(date);
        return cal.get(7) + offset;
    }

    public static int dayofweek(Date date) {
        return dayofweek(date, -1);
    }

    private static Calendar getCalendar() {
        Calendar cal = (Calendar) NestThreadLocalMap.get("GROOVY_STATIC_METHOD_CALENDAR");
        if (cal == null) {
            cal = Calendar.getInstance();
            NestThreadLocalMap.put("GROOVY_STATIC_METHOD_CALENDAR", cal);
        }
        return cal;
    }

    private static long getModRight(long targetID, int size, int bitNumber) {
        if (bitNumber < size) {
            throw new IllegalArgumentException("输入的位数比要求的size还小");
        }
        return size == 0 ? 0L : targetID / pow10[(bitNumber - size)];
    }

    public static long left(long targetID, int bitNumber, int st, int ed) {
        long end = getModRight(targetID, ed, bitNumber);
        return end % pow10[(ed - st)];
    }

    public static long left(long targetID, int st, int ed) {
        long end = getModRight(targetID, ed, 19);
        return end % pow10[(ed - st)];
    }

    public static long right(long targetID, int st, int ed) {
        long right = targetID % pow10[ed];
        return right / pow10[st];
    }

    public static String right(String right, int rightLength) {
        int length = right.length();
        int start = length - rightLength;
        return right.substring(start < 0 ? 0 : start);
    }

    public static void main(String[] args) {
    }
}
