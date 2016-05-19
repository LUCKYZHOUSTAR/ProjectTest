/**
 * 
 */
package DistributeDataBase.common.exception.runtime;

/** 
* @ClassName: CantIdentifyNumberExcpetion 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 上午10:35:17 
*  
*/
public class CantIdentifyNumberExcpetion extends ZdalRunTimeException {
    private static final long serialVersionUID = 7861250013675710468L;

    public CantIdentifyNumberExcpetion(String input, String input1, Throwable e) {
        super("关键字：" + input + "或：" + input1 + "不能识别为一个数，请重新设定", e);
    }
}
