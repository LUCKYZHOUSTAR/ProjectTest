/**
 * 
 */
package DistributeDataBase.client.config;

import java.util.HashMap;
import java.util.Map;

import DistributeDataBase.client.config.Bean.PhysicalDataSourceBean;

/** 
* @ClassName: DataSourceParameter 
* @Description: 数据源的参数信息
* @author LUCKY
* @date 2016年5月16日 下午3:42:31 
*  
*/
public class DataSourceParameter {

    private String              jdbcUrl               = "";
    private String              userName              = "";
    private String              password              = "";

    /*最小连接数*/
    private int                 mixConn;
    private int                 maxConn;

    private String              driverClass           = "";
    private int                 blockingTimeoutMillis = 180;

    private int                 idleTimeOutMinutes    = 30;
    private int                 preparedStatementCacheSize;
    private int                 queryTimeOut          = 30;
    private boolean             prefill;

    /*连接属性的集合操作*/
    private Map<String, String> connectionProperties  = new HashMap<String, String>();

    public Map<String, String> getConnectionProperties() {
        return connectionProperties;
    }

    public void setConnectionProperties(Map<String, String> connectionProperties) {
        this.connectionProperties = connectionProperties;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMixConn() {
        return mixConn;
    }

    public void setMixConn(int mixConn) {
        this.mixConn = mixConn;
    }

    public int getMaxConn() {
        return maxConn;
    }

    public void setMaxConn(int maxConn) {
        this.maxConn = maxConn;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public int getIdleTimeOutMinutes() {
        return idleTimeOutMinutes;
    }

    public void setIdleTimeOutMinutes(int idleTimeOutMinutes) {
        this.idleTimeOutMinutes = idleTimeOutMinutes;
    }

    public int getPreparedStatementCacheSize() {
        return preparedStatementCacheSize;
    }

    public void setPreparedStatementCacheSize(int preparedStatementCacheSize) {
        this.preparedStatementCacheSize = preparedStatementCacheSize;
    }

    public int getQueryTimeOut() {
        return queryTimeOut;
    }

    public void setQueryTimeOut(int queryTimeOut) {
        this.queryTimeOut = queryTimeOut;
    }

    public boolean isPrefill() {
        return prefill;
    }

    public void setPrefill(boolean prefill) {
        this.prefill = prefill;
    }

    public int getBlockingTimeoutMillis() {
        return blockingTimeoutMillis;
    }

    public void setBlockingTimeoutMillis(int blockingTimeoutMillis) {
        this.blockingTimeoutMillis = blockingTimeoutMillis;
    }

    public static DataSourceParameter valueOf(PhysicalDataSourceBean bean) {
        DataSourceParameter paramter = new DataSourceParameter();
        paramter.setBlockingTimeoutMillis(bean.getBlockingTimeoutMillis());
        paramter.setDriverClass(bean.getDriverClass());
        paramter.setIdleTimeOutMinutes(bean.getIdleTimeoutMinutes());
        paramter.setJdbcUrl(bean.getJdbcUrl());
        paramter.setMaxConn(bean.getMaxConn());
        paramter.setMixConn(bean.getMinConn());
        paramter.setPassword(bean.getPassword());
        paramter.setPreparedStatementCacheSize(bean.getPreparedStatementCacheSize());
        paramter.setQueryTimeOut(bean.getQueryTimeout());
        paramter.setUserName(bean.getUserName());
        paramter.setPrefill(bean.isPrefill());
        paramter.setConnectionProperties(bean.getConnectionProperties());
        return paramter;
    }

}
