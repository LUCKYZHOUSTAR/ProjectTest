/**
 * 
 */
package DistributeDataBase.rule.ruleengine.entities.abstractentities;

import java.util.List;

import DistributeDataBase.rule.ruleengine.entities.convientobjectmaker.TableMapProvider;
import DistributeDataBase.rule.ruleengine.rule.ListAbstractResultRule;

/** 
* @ClassName: TablePropertiesSetter 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午7:15:14 
*  
*/
public interface TablePropertiesSetter {
    public abstract void setTableMapProvider(TableMapProvider paramTableMapProvider);

    public abstract void setTableRule(List<ListAbstractResultRule> paramList);

    public abstract void setLogicTableName(String paramString);

    public abstract void setTableRuleChain(RuleChain paramRuleChain);
}
