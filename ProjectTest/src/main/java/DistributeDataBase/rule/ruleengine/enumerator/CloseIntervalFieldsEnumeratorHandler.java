/**
 * 
 */
package DistributeDataBase.rule.ruleengine.enumerator;

import java.util.Set;

import DistributeDataBase.common.sqljep.function.Comparative;

/** 
* @ClassName: CloseIntervalFieldsEnumeratorHandler 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午7:08:11 
*  
*/
public abstract interface CloseIntervalFieldsEnumeratorHandler {
    public abstract void processAllPassableFields(Comparative paramComparative,
                                                  Set<Object> paramSet, Integer paramInteger,
                                                  Comparable<?> paramComparable);

    public abstract void mergeFeildOfDefinitionInCloseInterval(Comparative paramComparative1,
                                                               Comparative paramComparative2,
                                                               Set<Object> paramSet,
                                                               Integer paramInteger,
                                                               Comparable<?> paramComparable);
}