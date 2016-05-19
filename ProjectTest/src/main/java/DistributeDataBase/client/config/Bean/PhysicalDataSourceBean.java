/**
 * 
 */
package DistributeDataBase.client.config.Bean;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;

/** 
* @ClassName: PhysicalDataSourceBean 
* @Description: 
* @author LUCKY
* @date 2016年5月16日 下午3:47:11 
*  
*/
public class PhysicalDataSourceBean implements InitializingBean {

    private static final String ORACLE_DRIVER_CLASS   = "oracle.jdbc.OracleDriver";
    private static final String MYSQL_DRIVER_CLASS    = "com.mysql.jdbc.Driver";
    private static final String DB2_DRIVER_CLASS      = "com.ibm.db2.jcc.DB2Driver";

    private String              name                  = "";
    private Set<String>         logicDbNameSet;
    private String              jdbcUrl               = "";
    private String              userName              = "";
    private String              password              = "";
    private int                 minConn;
    private int                 maxConn;
    private String              driverClass           = "";
    private int                 blockingTimeoutMillis = 180;
    private int                 idleTimeoutMinutes    = 30;
    private int                 preparedStatementCacheSize;
    private int                 queryTimeout          = 30;
    private Map<String, String> connectionProperties  = new HashMap();
    private boolean             prefill;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getLogicDbNameSet() {
        return this.logicDbNameSet;
    }

    public void setLogicDbNameSet(Set<String> logicDbNameSet) {
        this.logicDbNameSet = logicDbNameSet;
    }

    public String getJdbcUrl() {
        return this.jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMinConn() {
        return this.minConn;
    }

    public void setMinConn(int minConn) {
        this.minConn = minConn;
    }

    public int getMaxConn() {
        return this.maxConn;
    }

    public void setMaxConn(int maxConn) {
        this.maxConn = maxConn;
    }

    public String getDriverClass() {
        return this.driverClass;
    }

    public int getBlockingTimeoutMillis() {
        return this.blockingTimeoutMillis;
    }

    public void setBlockingTimeoutMillis(int blockingTimeoutMillis) {
        this.blockingTimeoutMillis = blockingTimeoutMillis;
    }

    public int getIdleTimeoutMinutes() {
        return this.idleTimeoutMinutes;
    }

    public void setIdleTimeoutMinutes(int idleTimeoutMinutes) {
        this.idleTimeoutMinutes = idleTimeoutMinutes;
    }

    public int getPreparedStatementCacheSize() {
        return this.preparedStatementCacheSize;
    }

    public void setPreparedStatementCacheSize(int preparedStatementCacheSize) {
        this.preparedStatementCacheSize = preparedStatementCacheSize;
    }

    public int getQueryTimeout() {
        return this.queryTimeout;
    }

    public void setQueryTimeout(int queryTimeout) {
        this.queryTimeout = queryTimeout;
    }

    public Map<String, String> getConnectionProperties() {
        return this.connectionProperties;
    }

    public void setConnectionProperties(Map<String, String> connectionProperties) {
        this.connectionProperties = connectionProperties;
    }

    public boolean isPrefill() {
        return this.prefill;
    }

    public void setPrefill(boolean prefill) {
        this.prefill = prefill;
    }

    /*
     * 通过继承InitializingBean，在设置完属性后，会进行调用该方法
     * */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (StringUtils.isBlank(this.name)) {
            throw new IllegalArgumentException("ERROR ## the physicalDataSource's name is null");
        }

        if (this.logicDbNameSet == null || this.logicDbNameSet.isEmpty()) {
            this.logicDbNameSet = new HashSet<>();
            this.logicDbNameSet.add(this.name);
        }

        if (StringUtils.isBlank(this.jdbcUrl)) {
            throw new IllegalArgumentException("ERROR ## the jdbcUrl is null of " + this.name);
        }

        if (this.jdbcUrl.contains("oracle"))
            this.driverClass = "oracle.jdbc.OracleDriver";
        else if (this.jdbcUrl.contains("mysql"))
            this.driverClass = "com.mysql.jdbc.Driver";
        else if (this.jdbcUrl.contains("db2"))
            this.driverClass = "com.ibm.db2.jcc.DB2Driver";
        else {
            throw new IllegalArgumentException(
                "ERROR ## the jdbcUrl is invalidate,must contain [oracle,mysql,db2] of "
                        + this.name);
        }

        if (StringUtils.isBlank(this.userName)) {
            throw new IllegalArgumentException("ERROR ## the userName is null of " + this.userName);
        }

        if (StringUtils.isBlank(this.password)) {
            throw new IllegalArgumentException("ERROR ## the password is null of " + this.password);
        }

        if (this.minConn < 0) {
            throw new IllegalArgumentException("ERROR ## the minConn = " + this.minConn
                                               + " must >=0 of " + this.minConn);
        }

        if (this.maxConn < 0) {
            throw new IllegalArgumentException("ERROR ## the maxConn = " + this.maxConn
                                               + " must >=0 of " + this.name);
        }

        if (this.minConn > this.maxConn) {
            throw new IllegalArgumentException("ERROR ## the maxConn[" + this.maxConn
                                               + "] must >= minConn[" + this.minConn + " of "
                                               + this.name);
        }

        if (this.blockingTimeoutMillis < 0) {
            throw new IllegalArgumentException("ERROR ## the blockingTimeoutMillis = "
                                               + this.blockingTimeoutMillis + " must >= 0 of "
                                               + this.name);
        }

        if (this.idleTimeoutMinutes <= 0) {
            throw new IllegalArgumentException("ERROR ## the idleTimeoutMinutes = "
                                               + this.idleTimeoutMinutes + " must > 0 of "
                                               + this.name);
        }

        if (this.preparedStatementCacheSize < 0) {
            throw new IllegalArgumentException("ERROR ## the preparedStatementCacheSize = "
                                               + this.preparedStatementCacheSize + " must >=0 of "
                                               + this.name);
        }

        if (this.queryTimeout <= 0)
            throw new IllegalArgumentException("ERROR ## the queryTimeout  = " + this.queryTimeout
                                               + " must > 0 of " + this.name);

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + blockingTimeoutMillis;
        result = prime * result
                 + ((connectionProperties == null) ? 0 : connectionProperties.hashCode());
        result = prime * result + ((driverClass == null) ? 0 : driverClass.hashCode());
        result = prime * result + idleTimeoutMinutes;
        result = prime * result + ((jdbcUrl == null) ? 0 : jdbcUrl.hashCode());
        result = prime * result + ((logicDbNameSet == null) ? 0 : logicDbNameSet.hashCode());
        result = prime * result + maxConn;
        result = prime * result + minConn;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + (prefill ? 1231 : 1237);
        result = prime * result + preparedStatementCacheSize;
        result = prime * result + queryTimeout;
        result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
        PhysicalDataSourceBean other = (PhysicalDataSourceBean) obj;
        if (blockingTimeoutMillis != other.blockingTimeoutMillis)
            return false;
        if (connectionProperties == null) {
            if (other.connectionProperties != null)
                return false;
        } else if (!connectionProperties.equals(other.connectionProperties))
            return false;
        if (driverClass == null) {
            if (other.driverClass != null)
                return false;
        } else if (!driverClass.equals(other.driverClass))
            return false;
        if (idleTimeoutMinutes != other.idleTimeoutMinutes)
            return false;
        if (jdbcUrl == null) {
            if (other.jdbcUrl != null)
                return false;
        } else if (!jdbcUrl.equals(other.jdbcUrl))
            return false;
        if (logicDbNameSet == null) {
            if (other.logicDbNameSet != null)
                return false;
        } else if (!logicDbNameSet.equals(other.logicDbNameSet))
            return false;
        if (maxConn != other.maxConn)
            return false;
        if (minConn != other.minConn)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (prefill != other.prefill)
            return false;
        if (preparedStatementCacheSize != other.preparedStatementCacheSize)
            return false;
        if (queryTimeout != other.queryTimeout)
            return false;
        if (userName == null) {
            if (other.userName != null)
                return false;
        } else if (!userName.equals(other.userName))
            return false;
        return true;
    }

}
