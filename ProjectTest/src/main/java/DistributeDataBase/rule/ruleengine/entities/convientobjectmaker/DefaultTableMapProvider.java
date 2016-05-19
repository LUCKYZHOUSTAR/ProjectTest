/**
 * 
 */
package DistributeDataBase.rule.ruleengine.entities.convientobjectmaker;

import java.util.HashMap;
import java.util.Map;

import DistributeDataBase.rule.bean.Table;
import DistributeDataBase.rule.ruleengine.entities.abstractentities.SharedElement;


/** 
* @ClassName: DefaultTableMapProvider 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午7:24:01 
*  
*/
public class DefaultTableMapProvider implements TableMapProvider {
    String logicTable;

    public Map<String, SharedElement> getTablesMap() {
        Table table = new Table();
        if (this.logicTable == null) {
            throw new IllegalArgumentException("没有表名生成因子");
        }
        table.setTableName(this.logicTable);
        Map returnMap = new HashMap();
        returnMap.put("0", table);
        return returnMap;
    }

    public void setLogicTable(String logicTable) {
        this.logicTable = logicTable;
    }

    public void setParentID(String parentID) {
    }
}