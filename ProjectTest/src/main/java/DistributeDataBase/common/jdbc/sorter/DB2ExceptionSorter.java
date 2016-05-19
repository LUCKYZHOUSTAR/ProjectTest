/**
 * 
 */
package DistributeDataBase.common.jdbc.sorter;

import java.io.Serializable;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
* @ClassName: DB2ExceptionSorter 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 上午10:46:50 
*  
*/
public class DB2ExceptionSorter implements ExceptionSorter, Serializable {
    private static final Logger  logger           = LoggerFactory
                                                      .getLogger(DB2ExceptionSorter.class);

    private static final boolean trace            = logger.isTraceEnabled();
    private static final long    serialVersionUID = -4724550353693159378L;

    @Override
    public boolean isExceptionFatal(SQLException e) {
        int code = Math.abs(e.getErrorCode());
        boolean isFatal = false;

        if (code == 4499) {
            isFatal = true;
        }

        if (trace) {
            logger.trace("Evaluated SQL error code " + code + " isException returned " + isFatal);
        }

        return isFatal;
    }
}