/**
 * 
 */
package DistributeDataBase.rule.ruleengine.entities.convientobjectmaker;

import java.util.Map;

import org.springframework.orm.jpa.vendor.Database;

/** 
* @ClassName: DatabaseMapProvider 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午7:23:38 
*  
*/
public abstract interface DatabaseMapProvider {
    public abstract Map<String, Database> getDatabaseMap();
}