/**
 * 
 */
package DistributeDataBase.rule.bean;

import DistributeDataBase.rule.ruleengine.entities.abstractentities.SharedElement;

/** 
* @ClassName: Table 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午7:32:36 
*  
*/
public class Table extends SharedElement {
    private String tableName;

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        if (this.tableName == null)
            this.tableName = tableName;
        else
            throw new IllegalArgumentException("you can't modify this element");
    }

    public String toString() {
        return "table:" + this.tableName;
    }
}
