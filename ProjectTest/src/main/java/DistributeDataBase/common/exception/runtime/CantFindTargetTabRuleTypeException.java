/**
 * 
 */
package DistributeDataBase.common.exception.runtime;

/** 
* @ClassName: CantFindTargetTabRuleTypeException 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 上午10:36:05 
*  
*/
public class CantFindTargetTabRuleTypeException extends ZdalRunTimeException {
    private static final long serialVersionUID = -7179888759169646552L;

    public CantFindTargetTabRuleTypeException(String msg) {
        super("无法根据输入的tableRule:" + msg + "找到对应的处理方法。");
    }
}
