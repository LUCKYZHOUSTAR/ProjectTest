/**
 * 
 */
package DistributeDataBase.client.config.exceptions;

/** 
* @ClassName: ZdalConfigException 
* @Description: 
* @author LUCKY
* @date 2016年5月16日 下午4:37:59 
*  
*/
public class ZdalConfigException extends RuntimeException {

    /** 
    * @Fields serialVersionUID : 
    */
    private static final long serialVersionUID = -7111635265648454895L;

    public ZdalConfigException(String cause) {
        super(cause);
    }

    public ZdalConfigException(Throwable t) {
        super(t);
    }

    public ZdalConfigException(String cause, Throwable t) {
        super(cause, t);
    }
}
