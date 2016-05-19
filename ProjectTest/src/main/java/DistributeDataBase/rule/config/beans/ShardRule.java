/**
 * 
 */
package DistributeDataBase.rule.config.beans;

import java.util.HashMap;
import java.util.Map;

/** 
* @ClassName: ShardRule 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午6:48:01 
*  
*/
public class ShardRule implements Cloneable {
    private Map<String, TableRule> tableRules;
    private String                 defaultDbIndex;

    public Map<String, TableRule> getTableRules() {
        return this.tableRules;
    }

    public void setTableRules(Map<String, TableRule> tableRules) {
        this.tableRules = tableRules;
    }

    public String getDefaultDbIndex() {
        return this.defaultDbIndex;
    }

    public void setDefaultDbIndex(String defaultDbIndex) {
        this.defaultDbIndex = defaultDbIndex;
    }

    public ShardRule clone() throws CloneNotSupportedException {
        ShardRule r = (ShardRule) super.clone();

        Map tbrs = new HashMap(this.tableRules.size());
        for (Map.Entry e : this.tableRules.entrySet()) {
            TableRule tbr = ((TableRule) e.getValue()).clone();
            String[] oldIndexes = tbr.getDbIndexArray();
            String[] newIndexes = new String[oldIndexes.length];
            System.arraycopy(oldIndexes, 0, newIndexes, 0, oldIndexes.length);
            tbr.setDbIndexArray(newIndexes);
            tbrs.put(e.getKey(), tbr);
        }
        r.setDefaultDbIndex(this.defaultDbIndex);
        r.setTableRules(tbrs);
        return r;
    }

    public boolean equals(Object obj) {
        ShardRule shardRule = (ShardRule) obj;
        Map tableRules = shardRule.getTableRules();
        if ((tableRules == null) || (this.tableRules == null)
            || (tableRules.size() != this.tableRules.size())) {
            return false;
        }

        for (Object key : tableRules.keySet()) {
            TableRule tr = (TableRule) tableRules.get(key);
            TableRule tr2 = (TableRule) this.tableRules.get(key);
            if (tr2 == null) {
                return false;
            }
            if (tr2 != tr) {
                return false;
            }
        }
        return true;
    }
}