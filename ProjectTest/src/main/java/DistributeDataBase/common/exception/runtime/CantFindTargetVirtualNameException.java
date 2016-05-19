/**
 * 
 */
package DistributeDataBase.common.exception.runtime;

/** 
* @ClassName: CantFindTargetVirtualNameException 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 上午10:35:34 
*  
*/
public class CantFindTargetVirtualNameException extends ZdalRunTimeException {
    private static final long serialVersionUID = 5542737179921749267L;

    public CantFindTargetVirtualNameException(String virtualTabName) {
        super("can't find virtualTabName:" + virtualTabName);
    }
}
