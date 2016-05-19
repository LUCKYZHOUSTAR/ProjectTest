/**
 * 
 */
package DistributeDataBase.common.jdbc.sorter;

import java.io.Serializable;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
* @ClassName: OracleExceptionSorter 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 上午10:41:27 
*  
*/
public class OracleExceptionSorter implements ExceptionSorter, Serializable {

    /** 
    * @Fields serialVersionUID : 
    */
    private static final long   serialVersionUID = -3410747711582182424L;

    private static final Logger logger           = LoggerFactory
                                                     .getLogger(OracleExceptionSorter.class);

    @Override
    public boolean isExceptionFatal(SQLException e) {
        int error_code = Math.abs(e.getErrorCode());
        if (logger.isDebugEnabled()) {
            logger.debug("INFO ## the errorCode = " + error_code + ",error_text = "
                         + e.getMessage());
        }

        if ((error_code == 28) || (error_code == 600) || (error_code == 1012)
            || (error_code == 1014) || (error_code == 1033) || (error_code == 1034)
            || (error_code == 1035) || (error_code == 1089) || (error_code == 1090)
            || (error_code == 1092) || (error_code == 1094) || (error_code == 2396)
            || (error_code == 3106) || (error_code == 3111) || (error_code == 3113)
            || (error_code == 3114) || ((error_code >= 12100) && (error_code <= 12299))
            || (error_code == 17002) || (error_code == 17008) || (error_code == 28000)
            || (error_code == 17410) || (error_code == 17447) || (error_code == 17401)
            || (error_code == 3137) || (error_code == 999999)) {
            return true;
        }

        if (e.getMessage() == null) {
            return false;
        }
        String error_text = e.getMessage().toUpperCase();

        if (((error_code < 20000) || (error_code >= 21000))
            && ((error_text.indexOf("NO DATASOURCE") > -1)
                || (error_text.indexOf("COULD NOT CREATE CONNECTION") > -1)
                || (error_text.indexOf("NO ALIVE DATASOURCE") > -1)
                || (error_text.indexOf("SOCKET") > -1)
                || (error_text.indexOf("CONNECTION HAS ALREADY BEEN CLOSED") > -1)
                || (error_text.indexOf("BROKEN PIPE") > -1)
                || ((error_text.indexOf("TNS") > -1) && (error_text.indexOf("ORA-") > -1))
                || (error_text.indexOf("Closed Connection") > -1)
                || (error_text.indexOf("关闭的连接") > -1) || (error_text.indexOf("套接字") > -1))) {
            return true;
        }

        return false;
    }
}
