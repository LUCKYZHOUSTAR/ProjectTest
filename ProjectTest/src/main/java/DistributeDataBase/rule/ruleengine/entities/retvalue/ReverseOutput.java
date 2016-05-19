/**
 * 
 */
package DistributeDataBase.rule.ruleengine.entities.retvalue;

import java.util.Collections;
import java.util.Map;

/** 
* @ClassName: ReverseOutput 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午7:30:20 
*  
*/
public class ReverseOutput {
    private String               sql;
    private String               table;
    private Map<Integer, Object> params = Collections.emptyMap();

    public String getSql() {
        return this.sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Map<Integer, Object> getParams() {
        return this.params;
    }

    public void setParams(Map<Integer, Object> params) {
        this.params = params;
    }

    public String getTable() {
        return this.table;
    }

    public void setTable(String table) {
        this.table = table;
    }
}