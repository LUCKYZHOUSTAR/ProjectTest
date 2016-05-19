/**
 * 
 */
package DistributeDataBase.common.exception.runtime;

/** 
* @ClassName: NotSupportException 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 上午10:33:21 
*  
*/
public class NotSupportException extends ZdalRunTimeException {

    /** 
    * @Fields serialVersionUID : 
    */
    private static final long serialVersionUID = -4656876088405718771L;

    public NotSupportException(String msg) {
        super("not support yet." + msg);
    }
}
