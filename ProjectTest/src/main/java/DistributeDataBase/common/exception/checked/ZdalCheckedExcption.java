/**
 * 
 */
package DistributeDataBase.common.exception.checked;

/** 
* @ClassName: ZdalCheckedExcption 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 上午10:28:30 
*  
*/
public class ZdalCheckedExcption extends Exception {

    /** 
    * @Fields serialVersionUID : 
    */
    private static final long serialVersionUID = -5089186069495454484L;

    public ZdalCheckedExcption() {
    }

    public ZdalCheckedExcption(Throwable throwable) {
        super(throwable);
    }

    public ZdalCheckedExcption(String message, Throwable cause) {
        super(message, cause);
    }

    public ZdalCheckedExcption(String arg) {
        super(arg);
    }

}
