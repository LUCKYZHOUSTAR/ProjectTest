/**
 * 
 */
package DistributeDataBase.rule.ruleengine.entities.abstractentities;

import java.util.Collections;
import java.util.List;


import DistributeDataBase.rule.bean.RuleChainImp;
import DistributeDataBase.rule.ruleengine.entities.convientobjectmaker.DefaultTableMapProvider;
import DistributeDataBase.rule.ruleengine.entities.convientobjectmaker.TableMapProvider;
import DistributeDataBase.rule.ruleengine.rule.ListAbstractResultRule;

/** 
* @ClassName: OneToManyEntry 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午7:13:37 
*  
*/
public class OneToManyEntry {
    private String                                 logicTableName;
    protected RuleChain                            transmitedtableRuleChain;
    protected List<ListAbstractResultRule>         tableRuleList;
    private TableMapProvider                       tableMapProvider = new DefaultTableMapProvider();
    ListSharedElement.DEFAULT_LIST_RESULT_STRAGETY defaultListResultStragety;
    private boolean                                isInited         = false;

    public List<ListAbstractResultRule> getTableRule() {
        if (this.transmitedtableRuleChain != null) {
            return this.transmitedtableRuleChain.getListResultRule();
        }
        return Collections.emptyList();
    }

    public void setTableRuleChain(RuleChain ruleChain) {
        this.transmitedtableRuleChain = ruleChain;
    }

    public RuleChain getTableRuleChain() {
        return this.transmitedtableRuleChain;
    }

    public void setTableRule(List<ListAbstractResultRule> tableRule) {
        this.tableRuleList = tableRule;
    }

    public void init() {
        if (this.isInited) {
            return;
        }
        this.isInited = true;
        if (this.tableRuleList != null) {
            RuleChainImp ruleChainImp = getRuleChain(this.tableRuleList);

            if (this.transmitedtableRuleChain == null)
                this.transmitedtableRuleChain = ruleChainImp;
            else {
                throw new IllegalArgumentException("ruleChain已经有数据了，但仍然尝试使用init来进行初始化");
            }
        }
        if (this.transmitedtableRuleChain != null)
            this.transmitedtableRuleChain.init();
    }

    public static RuleChainImp getRuleChain(List<ListAbstractResultRule> tableRuleList) {
        RuleChainImp ruleChainImp = new RuleChainImp();
        ruleChainImp.setDatabaseRuleChain(false);
        ruleChainImp.setListResultRule(tableRuleList);
        return ruleChainImp;
    }

    public TableMapProvider getTableMapProvider() {
        return this.tableMapProvider;
    }

    public void setTableMapProvider(TableMapProvider tableMapProvider) {
        if ((tableMapProvider != null) && (!(tableMapProvider instanceof DefaultTableMapProvider)))
            this.tableMapProvider = tableMapProvider;
    }

    public String getLogicTableName() {
        return this.logicTableName;
    }

    public void setLogicTableName(String logicTablename) {
        this.logicTableName = logicTablename;
    }

    public ListSharedElement.DEFAULT_LIST_RESULT_STRAGETY getDefaultListResultStragety() {
        return this.defaultListResultStragety;
    }

    public void setDefaultListResultStragety(ListSharedElement.DEFAULT_LIST_RESULT_STRAGETY defaultListResultStragety) {
        this.defaultListResultStragety = defaultListResultStragety;
    }
}
