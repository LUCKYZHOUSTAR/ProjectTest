/**
 * 
 */
package DistributeDataBase.client.exceptions;

/** 
* @ClassName: ZdalClientException 
* @Description: 
* @author LUCKY
* @date 2016年5月16日 下午7:38:11 
*  
*/
public class ZdalClientException extends RuntimeException {

    /** 
    * @Fields serialVersionUID : 
    */
    private static final long serialVersionUID = 1L;

    public ZdalClientException(String cause) {
        super(cause);
    }

    public ZdalClientException(Throwable t) {
        super(t);
    }

    public ZdalClientException(String cause, Throwable t) {
        super(cause, t);
    }

}
