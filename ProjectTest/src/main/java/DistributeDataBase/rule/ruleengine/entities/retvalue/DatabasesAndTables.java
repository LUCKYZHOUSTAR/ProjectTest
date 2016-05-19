/**
 * 
 */
package DistributeDataBase.rule.ruleengine.entities.retvalue;

import java.util.Set;

/** 
* @ClassName: DatabasesAndTables 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午7:28:43 
*  
*/
public abstract interface DatabasesAndTables {
    public abstract String getDbIndex();

    public abstract Set<String> getTableNames();
}
