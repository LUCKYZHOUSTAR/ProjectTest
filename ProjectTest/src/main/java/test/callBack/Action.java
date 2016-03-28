/**     
 * @FileName: Action.java   
 * @Package:test.callBack   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年3月26日 上午10:35:19   
 * @version V1.0     
 */
package test.callBack;

/**  
 * @ClassName: Action   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年3月26日 上午10:35:19     
 */
public class Action {

    private CallBackInterface callBackInterface;

    
    /**   
     * @return callBackInterface   
     */
    public CallBackInterface getCallBackInterface() {
        return callBackInterface;
    }


    /**     
     * @param callBackInterface the callBackInterface to set     
     */
    public void setCallBackInterface(CallBackInterface callBackInterface) {
        this.callBackInterface = callBackInterface;
    }


    public void registerListener(CallBackInterface callBackInterface){
        this.callBackInterface=callBackInterface;
    }
    public void doAction(int d) {

        if (d > 0) {
            callBackInterface.success();
        } else {
            callBackInterface.error();
        }
        
        
    }
}
