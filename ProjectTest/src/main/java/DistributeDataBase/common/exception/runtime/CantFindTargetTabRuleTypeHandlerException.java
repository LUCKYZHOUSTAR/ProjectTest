/**
 * 
 */
package DistributeDataBase.common.exception.runtime;

/** 
* @ClassName: CantFindTargetTabRuleTypeHandlerException 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 上午10:35:51 
*  
*/
public class CantFindTargetTabRuleTypeHandlerException extends ZdalRunTimeException {
    private static final long serialVersionUID = -4073830327289870566L;

    public CantFindTargetTabRuleTypeHandlerException(String msg) {
        super("无法找到" + msg + "对应的处理器");
    }
}
