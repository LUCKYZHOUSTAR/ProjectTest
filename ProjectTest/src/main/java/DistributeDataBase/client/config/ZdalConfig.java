/**
 * 
 */
package DistributeDataBase.client.config;

import java.util.Map;

import org.jboss.netty.util.internal.ConcurrentHashMap;

import DistributeDataBase.common.DBType;
import DistributeDataBase.rule.config.beans.AppRule;

/** 
* @ClassName: ZdalConfig 
* @Description: 
* @author LUCKY
* @date 2016年5月16日 下午3:31:19 
*  
*/
public class ZdalConfig {

    private String                           appName;
    private String                           appDsName;
    private String                           dbmode;
    private DBType                           dbType               = DBType.MYSQL;

    private Map<String, DataSourceParameter> dataSourceParameters = new ConcurrentHashMap<>();

    private Map<String, String>              logicPhysicsDsNames  = new ConcurrentHashMap<>();
    private Map<String, String>              groupRules           = new ConcurrentHashMap<>();
    private Map<String, String>              failoverRules        = new ConcurrentHashMap<>();
    private AppRule                          appRootRule;
    private DataSourceConfigType             dataSourceConfigType;

    public Map<String, DataSourceParameter> getDataSourceParameters() {
        return this.dataSourceParameters;
    }

    public void setDataSourceParameters(Map<String, DataSourceParameter> dataSources) {
        this.dataSourceParameters = dataSources;
    }

    public Map<String, String> getGroupRules() {
        return this.groupRules;
    }

    public void setGroupRules(Map<String, String> readWriteRules) {
        this.groupRules = readWriteRules;
    }

    public DBType getDbType() {
        return this.dbType;
    }

    public void setDbType(DBType dbType) {
        this.dbType = dbType;
    }

    public String getAppDsName() {
        return this.appDsName;
    }

    public void setAppDsName(String appDsName) {
        this.appDsName = appDsName;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getDbmode() {
        return this.dbmode;
    }

    public void setDbmode(String dbmode) {
        this.dbmode = dbmode;
    }

    public Map<String, String> getLogicPhysicsDsNames() {
        return this.logicPhysicsDsNames;
    }

    public void setLogicPhysicsDsNames(Map<String, String> logicPhysicsDsNames) {
        this.logicPhysicsDsNames = logicPhysicsDsNames;
    }

    public AppRule getAppRootRule() {
        return this.appRootRule;
    }

    public void setAppRootRule(AppRule appRootRule) {
        this.appRootRule = appRootRule;
    }

    public DataSourceConfigType getDataSourceConfigType() {
        return this.dataSourceConfigType;
    }

    public void setDataSourceConfigType(DataSourceConfigType dataSourceConfigType) {
        this.dataSourceConfigType = dataSourceConfigType;
    }

    public Map<String, String> getFailoverRules() {
        return this.failoverRules;
    }

    public void setFailoverRules(Map<String, String> failoverRules) {
        this.failoverRules = failoverRules;
    }

}
