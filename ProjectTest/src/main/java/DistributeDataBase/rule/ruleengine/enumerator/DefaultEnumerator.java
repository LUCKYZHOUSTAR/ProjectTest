/**
 * 
 */
package DistributeDataBase.rule.ruleengine.enumerator;

import java.util.Set;

import DistributeDataBase.common.sqljep.function.Comparative;

/** 
* @ClassName: DefaultEnumerator 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午7:11:02 
*  
*/
public class DefaultEnumerator implements CloseIntervalFieldsEnumeratorHandler {
    public void mergeFeildOfDefinitionInCloseInterval(Comparative from, Comparative to,
                                                      Set<Object> retValue,
                                                      Integer cumulativeTimes,
                                                      Comparable<?> atomIncrValue) {
        throw new IllegalArgumentException("默认枚举器不支持穷举");
    }

    public void processAllPassableFields(Comparative source, Set<Object> retValue,
                                         Integer cumulativeTimes, Comparable<?> atomIncrValue) {
        throw new IllegalStateException(
            "在没有提供步长和叠加次数的前提下，不能够根据当前范围条件选出对应的定义域的枚举值，sql中不要出现> < >= <=");
    }
}
