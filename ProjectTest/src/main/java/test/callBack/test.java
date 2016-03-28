/**     
 * @FileName: test.java   
 * @Package:test.callBack   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年3月26日 上午10:35:14   
 * @version V1.0     
 */
package test.callBack;

/**  
 * @ClassName: test   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年3月26日 上午10:35:14     
 */
public class test {

    public static void main(String[] args) {
        Action action=new Action();
        action.registerListener(new CallBackInterface() {

            @Override
            public void success() {
                // TODO Auto-generated method stub
                System.out.println("回调成功的函数");
            }

            @Override
            public void error() {
                // TODO Auto-generated method stub
                System.out.println("回调失败的函数信息");
            }
            
        });
        
        
        action.doAction(-1);
    }
    
}
