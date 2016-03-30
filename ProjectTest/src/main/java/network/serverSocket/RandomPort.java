/**     
 * @FileName: RandomPort.java   
 * @Package:network.serverSocket   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年3月30日 上午11:01:19   
 * @version V1.0     
 */
package network.serverSocket;

import java.io.IOException;
import java.net.ServerSocket;

/**  
 * @ClassName: RandomPort   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年3月30日 上午11:01:19     
 */
public class RandomPort {
    public static void main(String[] args) {

        try {
          ServerSocket server = new ServerSocket(0);
          System.out.println("This server runs on port " 
           + server.getLocalPort());
        }
        catch (IOException ex) {
          System.err.println(ex);
        }

      }
}
