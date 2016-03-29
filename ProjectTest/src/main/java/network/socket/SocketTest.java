/**     
 * @FileName: SocketTest.java   
 * @Package:network.socket   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年3月29日 下午2:12:27   
 * @version V1.0     
 */
package network.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketOptions;

/**  
 * @ClassName: SocketTest   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年3月29日 下午2:12:27     
 */
public class SocketTest {

    
    public static void main(String[] args) {
       
        try {
            Socket socket=new Socket();
            SocketAddress address=new InetSocketAddress("", 90);
            socket.connect(address);
            //所连接系统的地址
            socket.getRemoteSocketAddress();
            //发起系统连接的地址
            socket.getLocalSocketAddress();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    
    /**
     * @throws IOException    
     * @Title: proxyAddress   
     * @Description:代理服务器 
     * @param   理服务器是用来提供网络出口，做防火墙、堡垒主机、实现应用层的过滤设置
     * @return void  
     * @throws   
     */
    public void proxyAddress() throws IOException{
        //代理服务器的地址
        SocketAddress proxyAddress=new InetSocketAddress("", 90);
        Proxy proxy=new Proxy(Type.SOCKS, proxyAddress);
        //Socket请求的地址
        Socket s=new Socket(proxy);
        SocketAddress remote=new InetSocketAddress(9034);
        s.connect(remote);
        s.shutdownInput();
        s.shutdownOutput();
        //判断是否已经关闭
        boolean connected=s.isConnected()&&!s.isClosed();
        s.setTcpNoDelay(true);
        //socket选项接口
//        SocketOptions
        
        s.setTcpNoDelay(true);
    }
}
