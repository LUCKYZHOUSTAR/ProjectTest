/**
 * 
 */
package DistributeDataBase.common.exception.runtime;

/** 
* @ClassName: InputStringIsNotValidException 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 上午10:34:23 
*  
*/
public class InputStringIsNotValidException extends ZdalRunTimeException{
    private static final long serialVersionUID = 6451499964787923727L;

    public InputStringIsNotValidException(String msg) {
        super(msg);
    }
}
