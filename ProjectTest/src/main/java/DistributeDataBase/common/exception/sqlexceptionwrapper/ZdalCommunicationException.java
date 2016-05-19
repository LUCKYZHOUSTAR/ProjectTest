/**
 * 
 */
package DistributeDataBase.common.exception.sqlexceptionwrapper;

import java.sql.SQLException;

/** 
* @ClassName: ZdalCommunicationException 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 上午10:38:55 
*  
*/
public class ZdalCommunicationException extends ZdalSQLExceptionWrapper {
    private static final long serialVersionUID = -3502922457609932248L;

    public ZdalCommunicationException(String message, SQLException targetSQLESqlException) {
        super(message, targetSQLESqlException);
    }
}
