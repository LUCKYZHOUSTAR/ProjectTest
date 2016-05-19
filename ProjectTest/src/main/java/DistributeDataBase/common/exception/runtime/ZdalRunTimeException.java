/**
 * 
 */
package DistributeDataBase.common.exception.runtime;

/** 
* @ClassName: ZdalRunTimeException 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 上午10:32:48 
*  
*/
public class ZdalRunTimeException extends RuntimeException {
    private static final long serialVersionUID = -2139691156552402165L;

    public ZdalRunTimeException(String arg) {
        super(arg);
    }

    public ZdalRunTimeException() {
    }

    public ZdalRunTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZdalRunTimeException(Throwable throwable) {
        super(throwable);
    }
}
