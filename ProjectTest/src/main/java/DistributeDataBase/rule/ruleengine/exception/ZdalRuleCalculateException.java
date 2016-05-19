/**
 * 
 */
package DistributeDataBase.rule.ruleengine.exception;

/** 
* @ClassName: ZdalRuleCalculateException 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午7:06:46 
*  
*/
public class ZdalRuleCalculateException extends RuntimeException {
    private static final long serialVersionUID = -1120481970898678244L;

    public ZdalRuleCalculateException() {
    }

    public ZdalRuleCalculateException(String message) {
        super(message);
    }

    public ZdalRuleCalculateException(String message, Throwable cause) {
        super(message, cause);
    }
}
