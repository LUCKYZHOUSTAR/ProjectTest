/**
 * 
 */
package DistributeDataBase.common.exception.sqlexceptionwrapper;

import java.sql.SQLException;

/** 
* @ClassName: ZdalSQLExceptionWrapper 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 上午10:37:15 
*  
*/
public class ZdalSQLExceptionWrapper extends SQLException {

    /** 
    * @Fields serialVersionUID : 
    */
    private static final long serialVersionUID = 6814870781802620080L;

    final SQLException        targetSQLException;
    final String              message;

    public ZdalSQLExceptionWrapper(String message, SQLException targetSQLESqlException) {
        if (targetSQLESqlException == null) {
            throw new IllegalArgumentException("必须填入SQLException");
        }
        this.targetSQLException = targetSQLESqlException;
        this.message = message;
    }

    public String getSQLState() {
        return this.targetSQLException.getSQLState();
    }

    public int getErrorCode() {
        return this.targetSQLException.getErrorCode();
    }

    public SQLException getNextException() {
        return this.targetSQLException.getNextException();
    }

    public void setNextException(SQLException ex) {
        this.targetSQLException.setNextException(ex);
    }

    public Throwable getCause() {
        return this.targetSQLException;
    }

    public String getMessage() {
        return this.message;
    }

}
