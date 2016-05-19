/**
 * 
 */
package DistributeDataBase.client.jdbc;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Map;
import java.util.logging.Logger;

import javax.sql.DataSource;

import DistributeDataBase.client.config.ZdalConfig;
import DistributeDataBase.client.config.ZdalConfigListener;
import DistributeDataBase.client.config.ZdalDataSourceConfig;

/** 
* @ClassName: AbstractZdalDataSource 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 上午9:52:21 
*  
*/
public class AbstractZdalDataSource extends ZdalDataSourceConfig 
implements DataSource,Closeable,ZdalConfigListener{

    
    
    
    
    
    
    
    
    @Override
    protected void initDataSources(ZdalConfig paramZdalConfig) {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see javax.sql.CommonDataSource#getLogWriter()
     */
    @Override
    public PrintWriter getLogWriter() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see javax.sql.CommonDataSource#setLogWriter(java.io.PrintWriter)
     */
    @Override
    public void setLogWriter(PrintWriter printwriter) throws SQLException {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see javax.sql.CommonDataSource#setLoginTimeout(int)
     */
    @Override
    public void setLoginTimeout(int i) throws SQLException {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see javax.sql.CommonDataSource#getLoginTimeout()
     */
    @Override
    public int getLoginTimeout() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see javax.sql.CommonDataSource#getParentLogger()
     */
    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see java.sql.Wrapper#unwrap(java.lang.Class)
     */
    @Override
    public <T> T unwrap(Class<T> class1) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
     */
    @Override
    public boolean isWrapperFor(Class<?> class1) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see DistributeDataBase.client.config.ZdalConfigListener#resetWeight(java.util.Map)
     */
    @Override
    public void resetWeight(Map<String, String> paramMap) {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see java.io.Closeable#close()
     */
    @Override
    public void close() throws IOException {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see javax.sql.DataSource#getConnection()
     */
    @Override
    public Connection getConnection() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see javax.sql.DataSource#getConnection(java.lang.String, java.lang.String)
     */
    @Override
    public Connection getConnection(String s, String s1) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

}
