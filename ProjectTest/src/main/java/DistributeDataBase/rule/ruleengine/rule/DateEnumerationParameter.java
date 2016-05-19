/**
 * 
 */
package DistributeDataBase.rule.ruleengine.rule;

/** 
* @ClassName: DateEnumerationParameter 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午7:02:31 
*  
*/
public class DateEnumerationParameter
implements Comparable
{
public final int atomicIncreatementNumber;
public final int calendarFieldType;

public DateEnumerationParameter(int atomicIncreateNumber)
{
  this.atomicIncreatementNumber = atomicIncreateNumber;
  this.calendarFieldType = 5;
}

public DateEnumerationParameter(int atomicIncreateNumber, int calendarFieldType) {
  this.atomicIncreatementNumber = atomicIncreateNumber;
  this.calendarFieldType = calendarFieldType;
}

public int compareTo(Object o)
{
  throw new IllegalArgumentException("should not be here !");
}
}