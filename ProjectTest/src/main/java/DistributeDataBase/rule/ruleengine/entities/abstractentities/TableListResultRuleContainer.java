/**
 * 
 */
package DistributeDataBase.rule.ruleengine.entities.abstractentities;

import java.util.List;

import DistributeDataBase.rule.ruleengine.rule.ListAbstractResultRule;

/** 
* @ClassName: TableListResultRuleContainer 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午7:16:22 
*  
*/
public interface TableListResultRuleContainer {
    public abstract boolean setTableListResultRule(List<ListAbstractResultRule> paramList);

}
