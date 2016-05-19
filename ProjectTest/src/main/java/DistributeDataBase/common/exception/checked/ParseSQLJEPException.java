/**
 * 
 */
package DistributeDataBase.common.exception.checked;

/** 
* @ClassName: ParseSQLJEPException 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 上午10:31:28 
*  
*/
public class ParseSQLJEPException extends ZdalCheckedExcption {
    private static final long serialVersionUID = 7724677712426352259L;

    public ParseSQLJEPException(Throwable th) {
        super("调用sqlJep的parseExpression的时候发生错误" + th.getMessage());
    }
}
