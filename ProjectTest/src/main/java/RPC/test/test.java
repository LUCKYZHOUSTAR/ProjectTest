/**     
 * @FileName: test.java   
 * @Package:RPC.test   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月6日 下午1:57:20   
 * @version V1.0     
 */
package RPC.test;

/**  
 * @ClassName: test   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月6日 下午1:57:20     
 */
public class test {

    public static void main(String[] args) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    //通过serversocket服务服务端的代码
                    RPCExporter.exporter("localhost", 9088);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
