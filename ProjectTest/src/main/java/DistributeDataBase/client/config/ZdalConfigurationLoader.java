/**
 * 
 */
package DistributeDataBase.client.config;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.common.utils.CollectionUtils;

import DistributeDataBase.client.config.Bean.AppDataSourceBean;
import DistributeDataBase.client.config.Bean.PhysicalDataSourceBean;
import DistributeDataBase.client.config.Bean.ZdalAppBean;
import DistributeDataBase.client.config.exceptions.ZdalConfigException;

/** 
* @ClassName: ZdalConfigurationLoader 
* @Description: 
* @author LUCKY
* @date 2016年5月16日 下午4:41:35 
*  
*/
public class ZdalConfigurationLoader {
    private static final Logger                  log          = LoggerFactory
                                                                  .getLogger("zdal-client-config");
    private static final ZdalConfigurationLoader instance     = new ZdalConfigurationLoader();

    private Map<String, Map<String, ZdalConfig>> appdsConfigs = new HashMap<String, Map<String, ZdalConfig>>();

    public static ZdalConfigurationLoader getInstance() {
        return instance;
    }

    public synchronized ZdalConfig getZdalConfiguration(String appName, String dbMode,
                                                        String appDsName, String configPath) {
        Map configs = this.appdsConfigs.get(appName);
        if ((configs == null) || (configs.isEmpty())) {
            Map maps = getZdalConfigurationFromLocal(appName, dbMode, appDsName, configPath);

            this.appdsConfigs.put(appName, maps);
            return (ZdalConfig) maps.get(appDsName);
        }
        return (ZdalConfig) configs.get(appDsName);
    }

    private synchronized Map<String, ZdalConfig> getZdalConfigurationFromLocal(String appName,
                                                                               String dbMode,
                                                                               String appDsName,
                                                                               String configPath) {
        List zdalConfigurationFilePathList = new ArrayList<>();
        String fileName = MessageFormat.format("{0}-{1}-ds.xml", new Object[] { appName, dbMode });

        String ruleFileName = MessageFormat.format("{0}-{1}-rule.xml", new Object[] { appName,
                dbMode });

        zdalConfigurationFilePathList.add(new StringBuilder().append(configPath).append("/")
            .append(fileName).toString());
        zdalConfigurationFilePathList.add(new StringBuilder().append(configPath).append("/")
            .append(ruleFileName).toString());
        if (zdalConfigurationFilePathList.isEmpty()) {
            throw new ZdalConfigException(new StringBuilder()
                .append("ERROR ## There is no local Zdal configuration files for ").append(appName)
                .append(" to initialize ZdalDataSource.").toString());
        }

        return loadZdalConfigurationContext(
            (String[]) zdalConfigurationFilePathList.toArray(new String[zdalConfigurationFilePathList
                .size()]), appName, dbMode);

    }

    private synchronized Map<String, ZdalConfig> loadZdalConfigurationContext(String[] fileNames,
                                                                              String appName,
                                                                              String dbMode) {
        Map zdalConfigMap = new HashMap<>();

        try {
            ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(fileNames);
            if (null == ctx.getBean(appName)) {
                throw new ZdalConfigException(new StringBuilder()
                    .append("ERROR ## It must has at least one app bean in ").append(appName)
                    .append(" Zdal configuration file ").toString());
            }
            ZdalAppBean appBean = (ZdalAppBean) ctx.getBean(appName);

            if (CollectionUtils.isEmpty(appBean.getAppDataSourceList())) {
                throw new ZdalConfigException(new StringBuilder()
                    .append("ERROR ## the configured appName is ").append(appBean.getAppName())
                    .append(" are not match with requested appName:").append(appName).toString());

            }

            if (!dbMode.equals(appBean.getDbmode())) {
                throw new ZdalConfigException(new StringBuilder()
                    .append("ERROR ## The configured dbMode is ").append(appBean.getDbmode())
                    .append(" are not match with requested dbMode: ").append(dbMode).toString());
            }

            for (AppDataSourceBean appDataSourceBean : appBean.getAppDataSourceList()) {
                zdalConfigMap.put(appDataSourceBean.getAppDataSourceName(),
                    populateZdalConfig(appBean, appDataSourceBean));
            }

            return zdalConfigMap;
        } catch (Exception e) {
            StringBuilder stb = new StringBuilder();
            stb.append("Error### Zdal failed to load Zdal datasource and rule context with files ");
            for (String fileName : fileNames) {
                stb.append(fileName).append(", ");
            }
            stb.append(" when Zdal was loading them.");
            log.error(stb.toString(), e);
            throw new ZdalConfigException(e);
        }
    }

    protected ZdalConfig populateZdalConfig(ZdalAppBean appBean, AppDataSourceBean appDataSourceBean) {
        ZdalConfig config = new ZdalConfig();

        config.setAppName(appBean.getAppName());
        config.setDbmode(appBean.getDbmode());
        config.setAppDsName(appDataSourceBean.getAppDataSourceName());
        config.setDbType(appDataSourceBean.getDbType());
        config.setDataSourceConfigType(appDataSourceBean.getDataSourceConfigType());

        config.setAppRootRule(appDataSourceBean.getAppRule());

        for (PhysicalDataSourceBean physicalDataSource : appDataSourceBean
            .getPhysicalDataSourceSet()) {

            config.getDataSourceParameters().put(physicalDataSource.getName(),
                DataSourceParameter.valueOf(physicalDataSource));
            if ((null != physicalDataSource.getLogicDbNameSet())
                && (!physicalDataSource.getLogicDbNameSet().isEmpty())) {
                for (String logicDBName : physicalDataSource.getLogicDbNameSet())
                    config.getLogicPhysicsDsNames().put(logicDBName, physicalDataSource.getName());
            }
        }
        PhysicalDataSourceBean physicalDataSource;
        config.setGroupRules(appDataSourceBean.getGroupDataSourceRuleMap());
        config.setFailoverRules(appDataSourceBean.getFailOverGroupRuleMap());

        return config;
    }
}
