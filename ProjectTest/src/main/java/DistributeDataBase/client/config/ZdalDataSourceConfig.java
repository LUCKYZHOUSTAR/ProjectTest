/**
 * 
 */
package DistributeDataBase.client.config;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import DistributeDataBase.common.DBType;

/** 
* @ClassName: ZdalDataSourceConfig 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 上午9:53:58 
*  
*/
public abstract class ZdalDataSourceConfig {
    protected static final Logger  CONFIG_LOGGER = LoggerFactory.getLogger("zdal-client-config");
    protected String               appName;
    protected String               appDsName     = null;
    protected String               dbmode;
    protected String               configPath;
    protected ZdalConfig           zdalConfig    = null;
    protected AtomicBoolean        inited        = new AtomicBoolean(false);
    protected DataSourceConfigType dbConfigType  = null;
    protected DBType               dbType;

    protected void checkParameters() {
        if (StringUtils.isBlank(this.appName)) {
            throw new IllegalArgumentException("ERROR ## the appName is null");
        }
        CONFIG_LOGGER.warn("WARN ## the appName = " + this.appName);

        if (StringUtils.isBlank(this.appDsName)) {
            throw new IllegalArgumentException("ERROR ## the appDsName is null");
        }
        CONFIG_LOGGER.warn("WARN ## the appDsName = " + this.appDsName);

        if (StringUtils.isBlank(this.dbmode)) {
            throw new IllegalArgumentException("ERROR ## the dbmode is null");
        }
        CONFIG_LOGGER.warn("WARN ## the dbmode = " + this.dbmode);

        if (StringUtils.isBlank(this.configPath)) {
            throw new IllegalArgumentException("ERROR ## the configPath is null");
        }
        CONFIG_LOGGER.warn("WARN ## the configPath = " + this.configPath);
    }

    protected void initZdalDataSource() {
        long startInit = System.currentTimeMillis();
        this.zdalConfig = ZdalConfigurationLoader.getInstance().getZdalConfiguration(this.appName,
            this.dbmode, this.appDsName, this.configPath);

        this.dbConfigType = this.zdalConfig.getDataSourceConfigType();
        this.dbType = this.zdalConfig.getDbType();
        initDataSources(this.zdalConfig);
        this.inited.set(true);
        CONFIG_LOGGER.warn("WARN ## init ZdalDataSource [" + this.appDsName + "] success,cost "
                           + (System.currentTimeMillis() - startInit) + " ms");
    }

    protected abstract void initDataSources(ZdalConfig paramZdalConfig);

    public ZdalConfig getZdalConfig() {
        return this.zdalConfig;
    }

    public DataSourceConfigType getDbConfigType() {
        return this.dbConfigType;
    }

    public DBType getDbType() {
        return this.dbType;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppDsName() {
        return this.appDsName;
    }

    public void setAppDsName(String appDsName) {
        this.appDsName = appDsName;
    }

    public String getDbmode() {
        return this.dbmode;
    }

    public void setDbmode(String dbmode) {
        this.dbmode = dbmode.toLowerCase();
    }

    public String getConfigPath() {
        return this.configPath;
    }

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }
}
