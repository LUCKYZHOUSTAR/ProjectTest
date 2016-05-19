/**
 * 
 */
package DistributeDataBase.client.config.Bean;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;

import DistributeDataBase.client.config.DataSourceConfigType;
import DistributeDataBase.common.DBType;
import DistributeDataBase.rule.config.beans.AppRule;

/** 
* @ClassName: AppDataSourceBean 
* @Description: 
* @author LUCKY
* @date 2016年5月16日 下午4:19:38 
*  
*/
public class AppDataSourceBean implements InitializingBean {

    private String                      appDataSourceName;
    private String                      dataBaseType;
    private DBType                      dbType;
    private String                      configType;
    private DataSourceConfigType        dataSourceConfigType;
    private Set<PhysicalDataSourceBean> physicalDataSourceSet;

    private Map<String, String>         groupDataSourceRuleMap = new HashMap<String, String>();

    private Map<String, String>         failOverGroupRuleMap   = new HashMap<>();

    private AppRule                     appRule;

    public AppRule getAppRule() {
        return this.appRule;
    }

    public void setAppRule(AppRule appRule) {
        this.appRule = appRule;
    }

    public String getAppDataSourceName() {
        return this.appDataSourceName;
    }

    public void setAppDataSourceName(String appDataSourceName) {
        this.appDataSourceName = appDataSourceName;
    }

    public String getDataBaseType() {
        return this.dataBaseType;
    }

    public void setDataBaseType(String dataBaseType) {
        this.dataBaseType = dataBaseType;
    }

    public String getConfigType() {
        return this.configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }

    public Map<String, String> getGroupDataSourceRuleMap() {
        return this.groupDataSourceRuleMap;
    }

    public void setGroupDataSourceRuleMap(Map<String, String> groupDataSourceRuleMap) {
        this.groupDataSourceRuleMap = groupDataSourceRuleMap;
    }

    public Map<String, String> getFailOverGroupRuleMap() {
        return this.failOverGroupRuleMap;
    }

    public void setFailOverGroupRuleMap(Map<String, String> failOverGroupRuleMap) {
        this.failOverGroupRuleMap = failOverGroupRuleMap;
    }

    public Set<PhysicalDataSourceBean> getPhysicalDataSourceSet() {
        return this.physicalDataSourceSet;
    }

    public void setPhysicalDataSourceSet(Set<PhysicalDataSourceBean> physicalDataSourceSet) {
        this.physicalDataSourceSet = physicalDataSourceSet;
    }

    public DBType getDbType() {
        return this.dbType;
    }

    public void setDbType(DBType dbType) {
        this.dbType = dbType;
    }

    public DataSourceConfigType getDataSourceConfigType() {
        return this.dataSourceConfigType;
    }

    public void setDataSourceConfigType(DataSourceConfigType dataSourceConfigType) {
        this.dataSourceConfigType = dataSourceConfigType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((appDataSourceName == null) ? 0 : appDataSourceName.hashCode());
        result = prime * result + ((appRule == null) ? 0 : appRule.hashCode());
        result = prime * result + ((configType == null) ? 0 : configType.hashCode());
        result = prime * result + ((dataBaseType == null) ? 0 : dataBaseType.hashCode());
        result = prime * result
                 + ((dataSourceConfigType == null) ? 0 : dataSourceConfigType.hashCode());
        result = prime * result + ((dbType == null) ? 0 : dbType.hashCode());
        result = prime * result
                 + ((failOverGroupRuleMap == null) ? 0 : failOverGroupRuleMap.hashCode());
        result = prime * result
                 + ((groupDataSourceRuleMap == null) ? 0 : groupDataSourceRuleMap.hashCode());
        result = prime * result
                 + ((physicalDataSourceSet == null) ? 0 : physicalDataSourceSet.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AppDataSourceBean other = (AppDataSourceBean) obj;
        if (appDataSourceName == null) {
            if (other.appDataSourceName != null)
                return false;
        } else if (!appDataSourceName.equals(other.appDataSourceName))
            return false;
        if (appRule == null) {
            if (other.appRule != null)
                return false;
        } else if (!appRule.equals(other.appRule))
            return false;
        if (configType == null) {
            if (other.configType != null)
                return false;
        } else if (!configType.equals(other.configType))
            return false;
        if (dataBaseType == null) {
            if (other.dataBaseType != null)
                return false;
        } else if (!dataBaseType.equals(other.dataBaseType))
            return false;
        if (dataSourceConfigType != other.dataSourceConfigType)
            return false;
        if (dbType != other.dbType)
            return false;
        if (failOverGroupRuleMap == null) {
            if (other.failOverGroupRuleMap != null)
                return false;
        } else if (!failOverGroupRuleMap.equals(other.failOverGroupRuleMap))
            return false;
        if (groupDataSourceRuleMap == null) {
            if (other.groupDataSourceRuleMap != null)
                return false;
        } else if (!groupDataSourceRuleMap.equals(other.groupDataSourceRuleMap))
            return false;
        if (physicalDataSourceSet == null) {
            if (other.physicalDataSourceSet != null)
                return false;
        } else if (!physicalDataSourceSet.equals(other.physicalDataSourceSet))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "AppDataSourceBean [appDataSourceName=" + appDataSourceName + ", dataBaseType="
               + dataBaseType + ", dbType=" + dbType + ", configType=" + configType
               + ", dataSourceConfigType=" + dataSourceConfigType + ", physicalDataSourceSet="
               + physicalDataSourceSet + ", groupDataSourceRuleMap=" + groupDataSourceRuleMap
               + ", failOverGroupRuleMap=" + failOverGroupRuleMap + ", appRule=" + appRule + "]";
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (StringUtils.isBlank(this.appDataSourceName)) {
            throw new IllegalArgumentException("ERROR ## the appDataSourceName is null");
        }

        if (StringUtils.isBlank(this.dataBaseType)) {
            throw new IllegalArgumentException("ERROR ## the dataBaseType is null of "
                                               + this.appDataSourceName);
        }

        this.dbType = DBType.convert(this.dataBaseType);

        if (StringUtils.isBlank(this.configType)) {
            throw new IllegalArgumentException("ERROR ## the configType is null of "
                                               + this.appDataSourceName);
        }

        this.dataSourceConfigType = DataSourceConfigType.typeOf(this.configType);
        if ((this.physicalDataSourceSet == null) || (this.physicalDataSourceSet.isEmpty())) {
            throw new IllegalArgumentException("ERROR ## the physicalDataSourceSet is empty of "
                                               + this.appDataSourceName);
        }

        Map tmps = new HashMap<>();
        for (PhysicalDataSourceBean bean : this.physicalDataSourceSet) {
            tmps.put(bean.getName(), bean);
        }

        if (tmps.size() != this.physicalDataSourceSet.size()) {
            throw new IllegalArgumentException(
                "ERROR ## the physicalDataSourceSet has same name of " + this.appDataSourceName);
        }

        if (this.dataSourceConfigType.isShardGroup()
            && ((this.groupDataSourceRuleMap == null) || (this.groupDataSourceRuleMap.isEmpty()))) {
            throw new IllegalArgumentException(
                "ERROR ## the dataSourceConfigType is Shard_Group,must have groupDataSourceRuleMap properties of "
                        + this.appDataSourceName);

        }

        if ((this.dataSourceConfigType.isShardFailover())
            && ((this.failOverGroupRuleMap == null) || (this.failOverGroupRuleMap.isEmpty()))) {
            throw new IllegalArgumentException(
                "ERROR ## the dataSourceConfigType is Shard_failover,must have failOverGroupRuleMap properties of "
                        + this.appDataSourceName);
        }
        if ((this.dataSourceConfigType.isShard()) || (this.dataSourceConfigType.isShardFailover())
            || (this.dataSourceConfigType.isShardGroup())) {
            if (this.appRule == null)
                throw new IllegalArgumentException(
                    "ERROR ## the dataSourceConfigType is Shard,Shard_group,shard_failover must have sharding rule of AppRule properties of "
                            + this.appDataSourceName);
        }
    }

}
