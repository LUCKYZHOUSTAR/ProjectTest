/**
 * 
 */
package DistributeDataBase.common.exception.runtime;

/** 
* @ClassName: CantfindConfigFileByPathException 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 上午10:34:59 
*  
*/
public class CantfindConfigFileByPathException extends ZdalRunTimeException {
    private static final long serialVersionUID = -3338684575935778495L;

    public CantfindConfigFileByPathException(String path) {
        super("无法根据path:" + path + "找到指定的xml文件");
    }
}
