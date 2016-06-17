/**
 * 
 */
package network.tcpip;

import java.nio.channels.SocketChannel;

/** 
* @ClassName: TCPEchoClientNonblocking 
* @Description: 
* @author LUCKY
* @date 2016年6月14日 上午10:14:57 
*  
*/
public class TCPEchoClientNonblocking {

    public static void main(String[] args) throws Exception {
        //创建一个通道
        SocketChannel clntChan = SocketChannel.open();
        clntChan.configureBlocking(false);
        //向客户端发起连接
        
    }

}
