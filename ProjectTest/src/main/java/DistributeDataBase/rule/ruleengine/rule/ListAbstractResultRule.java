/**
 * 
 */
package DistributeDataBase.rule.ruleengine.rule;

import java.util.Map;
import java.util.Set;

import DistributeDataBase.common.sqljep.function.Comparative;

/** 
* @ClassName: ListAbstractResultRule 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午7:03:42 
*  
*/
public abstract class ListAbstractResultRule extends AbstractRule {
    public abstract Map<String, Field> eval(Map<String, Comparative> paramMap);

    public abstract Set<String> evalWithoutSourceTrace(Map<String, Set<Object>> paramMap,
                                                       String paramString, Set<Object> paramSet);
}
