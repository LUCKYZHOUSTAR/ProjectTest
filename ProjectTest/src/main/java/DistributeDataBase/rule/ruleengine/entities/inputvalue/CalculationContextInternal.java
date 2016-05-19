/**
 * 
 */
package DistributeDataBase.rule.ruleengine.entities.inputvalue;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import DistributeDataBase.common.sqljep.function.Comparative;
import DistributeDataBase.rule.ruleengine.entities.abstractentities.RuleChain;

/** 
* @ClassName: CalculationContextInternal 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午7:27:47 
*  
*/
public class CalculationContextInternal {
    public final RuleChain                ruleChain;
    protected Map<String, Set<Object>>    result = Collections.emptyMap();
    public final int                      index;
    public final Map<String, Comparative> sqlArgs;

    public CalculationContextInternal(RuleChain ruleChain, int index,
                                      Map<String, Comparative> sqlArgs) {
        this.ruleChain = ruleChain;
        this.index = index;
        this.sqlArgs = sqlArgs;
    }
}