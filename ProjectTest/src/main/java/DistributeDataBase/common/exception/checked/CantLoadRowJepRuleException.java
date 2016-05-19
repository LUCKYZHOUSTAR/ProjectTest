/**
 * 
 */
package DistributeDataBase.common.exception.checked;

/** 
* @ClassName: CantLoadRowJepRuleException 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 上午10:30:14 
*  
*/
public class CantLoadRowJepRuleException extends ZdalCheckedExcption {

    /** 
    * @Fields serialVersionUID : 
    */
    private static final long serialVersionUID = -5458059199734414625L;

    public CantLoadRowJepRuleException(String expression, String vtable, String parameter) {
        super("无法通过param:" + parameter + "|virtualTableName:" + vtable + "|expression:"
              + expression + "找到指定的规则判断引擎");
    }
}
