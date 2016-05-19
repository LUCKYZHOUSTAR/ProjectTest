/**
 * 
 */
package DistributeDataBase.rule.ruleengine.entities.abstractentities;

import java.util.List;
import java.util.Set;

import DistributeDataBase.rule.ruleengine.rule.ListAbstractResultRule;

/** 
* @ClassName: RuleChain 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午7:17:04 
*  
*/
public interface RuleChain {
    public boolean isDatabaseRuleChain();

    public ListAbstractResultRule getRuleByIndex(int paramInt);

    public List<Set<String>> getRequiredArgumentSortByLevel();

    public List<ListAbstractResultRule> getListResultRule();

    public void init();
}
