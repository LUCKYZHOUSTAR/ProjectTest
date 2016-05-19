/**
 * 
 */
package DistributeDataBase.rule.ruleengine;

import java.util.Map;
import java.util.Set;

import DistributeDataBase.common.DBType;
import DistributeDataBase.common.exception.checked.ZdalCheckedExcption;
import DistributeDataBase.common.sqljep.function.Comparative;
import DistributeDataBase.rule.ruleengine.entities.retvalue.PartitionElement;
import DistributeDataBase.rule.ruleengine.entities.retvalue.TargetDBMetaData;

/** 
* @ClassName: DBRuleProvider 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午6:57:58 
*  
*/
public abstract interface DBRuleProvider {
    public abstract TargetDBMetaData getDBAndTabs(String paramString,
                                                  Map<String, Comparative> paramMap)
                                                                                    throws ZdalCheckedExcption;

    public abstract TargetDBMetaData getDBAndTabs(String paramString1, String paramString2,
                                                  Set<String> paramSet) throws ZdalCheckedExcption;

    public abstract PartitionElement getPartitionColumns(String paramString);

    public abstract TargetDBMetaData getDBAndTabs(String paramString,
                                                  Map<String, Comparative> paramMap, int paramInt1,
                                                  int paramInt2) throws ZdalCheckedExcption;

    public abstract DBType getDBType();
}