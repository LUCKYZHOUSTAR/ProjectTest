/**
 * 
 */
package DistributeDataBase.client.exceptions;

import java.sql.SQLFeatureNotSupportedException;

/** 
* @ClassName: ZdalFeatureNotSupportException 
* @Description: 
* @author LUCKY
* @date 2016年5月16日 下午7:39:43 
*  
*/
public class ZdalFeatureNotSupportException extends SQLFeatureNotSupportedException {

    /** 
    * @Fields serialVersionUID : 
    */
    private static final long serialVersionUID = 1L;

    public ZdalFeatureNotSupportException(String cause) {
        super(cause);
    }

}
