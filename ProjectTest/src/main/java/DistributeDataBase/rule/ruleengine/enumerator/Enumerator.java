/**
 * 
 */
package DistributeDataBase.rule.ruleengine.enumerator;

import java.util.Set;

/** 
* @ClassName: Enumerator 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午7:07:43 
*  
*/
public abstract interface Enumerator
{
  public abstract Set<Object> getEnumeratedValue(Comparable paramComparable, Integer paramInteger, Comparable<?> paramComparable1, boolean paramBoolean);
}
