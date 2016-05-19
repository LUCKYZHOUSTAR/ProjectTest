/**
 * 
 */
package DistributeDataBase.rule.ruleengine.entities.convientobjectmaker;

import java.util.Map;

import DistributeDataBase.rule.ruleengine.entities.abstractentities.SharedElement;

/** 
* @ClassName: TableMapProvider 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午7:24:40 
*  
*/
public abstract interface TableMapProvider {
    public abstract Map<String, SharedElement> getTablesMap();

    public abstract void setParentID(String paramString);

    public abstract void setLogicTable(String paramString);
}