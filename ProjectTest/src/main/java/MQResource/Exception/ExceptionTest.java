/**     
 * @FileName: ExceptionTest.java   
 * @Package:MQResource.Exception   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月20日 上午11:29:35   
 * @version V1.0     
 */
package MQResource.Exception;

import org.junit.Test;

/**  
 * @ClassName: ExceptionTest   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月20日 上午11:29:35     
 */
public class ExceptionTest {

    @Test
    public void Test(){
        Throwable e=new Throwable("空指针");
        try {
            int i=3;
            int b=0;
            int c=3/0;
        } catch (Exception e2) {
            System.out.println(new RemotingTimeoutException(e2.getMessage(),1000,e2).toString());
            System.out.println(new RemotingTimeoutException("我的异常").toString());
            new RemotingTimeoutException("出现异常了").printStackTrace();
        }
       
    }
}
