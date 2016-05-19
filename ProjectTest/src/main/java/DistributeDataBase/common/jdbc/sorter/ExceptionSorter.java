/**
 * 
 */
package DistributeDataBase.common.jdbc.sorter;

import java.sql.SQLException;

/** 
* @ClassName: ExceptionSorter 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 上午10:40:40 
*  
*/
public interface ExceptionSorter {
    public static final int ROLLBACK_ERRORCODE = 999999;

    public boolean isExceptionFatal(SQLException paramSQLException);
}
