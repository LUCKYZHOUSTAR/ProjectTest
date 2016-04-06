/**     
 * @FileName: RPCTest.java   
 * @Package:RPC.test   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月6日 下午1:45:43   
 * @version V1.0     
 */
package RPC.test;

import java.net.InetSocketAddress;

/**  
 * @ClassName: RPCTest   
 * @Description: RPC框架测试类草走
 * @author: LUCKY  
 * @date:2016年4月6日 下午1:45:43     
 */
public class RPCTest {

    public static void main(String[] args) {
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                try {
//                    //通过serversocket服务服务端的代码
//                    RPCExporter.exporter("localhost", 9088);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//        
        
        RpcImporter<EchoService> importer=new RpcImporter<>();
        EchoService echo=importer.importer(EchoServiceImpl.class, new InetSocketAddress("localhost", 9088));
        System.out.println(echo.echo("are you ok?"));
    }
}
