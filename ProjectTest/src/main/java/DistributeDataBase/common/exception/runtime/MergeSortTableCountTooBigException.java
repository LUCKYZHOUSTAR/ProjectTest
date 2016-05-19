/**
 * 
 */
package DistributeDataBase.common.exception.runtime;

/** 
* @ClassName: MergeSortTableCountTooBigException 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 上午10:33:56 
*  
*/
public class MergeSortTableCountTooBigException extends ZdalRunTimeException {
    private static final long serialVersionUID = 3821111668876729135L;

    public MergeSortTableCountTooBigException(String msg) {
        super(msg);
    }
}
