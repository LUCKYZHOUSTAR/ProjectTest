/**     
 * @FileName: SocketInfo.java   
 * @Package:network.socket   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年3月29日 下午1:58:59   
 * @version V1.0     
 */
package network.socket;

import java.net.InetAddress;
import java.net.Socket;

/**  
 * @ClassName: SocketInfo   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年3月29日 下午1:58:59     
 */
public class SocketInfo {
    public static void main(String[] args) {
        try (Socket theSocket = new Socket(InetAddress.getLocalHost(), 1025)) {
            System.out.println("Connected to"+theSocket.getInetAddress()+"on port"+theSocket.getPort()+"From port"+theSocket.getLocalPort()+"of "+theSocket.getLocalAddress()+""+theSocket.getRemoteSocketAddress());
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
