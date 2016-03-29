/**     
 * @FileName: DayTimeClient.java   
 * @Package:network.socket   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年3月29日 下午1:50:10   
 * @version V1.0     
 */
package network.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

/**  
 * @ClassName: DayTimeClient   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年3月29日 下午1:50:10     
 */
public class DayTimeClient {

    public static void main(String[] args) {
        String hostName = "time.nist.gov";
        Socket socket = null;
        try {
            InetAddress inetaddress = InetAddress.getLocalHost();

            //一旦设置的地址，就进行了连接，如果连接出现异常的话，会跑出exception信息
            socket = new Socket(inetaddress, 8900);
            InputStream in=socket.getInputStream();
            //设置超时的时间
            socket.setSoTimeout(15000000);
            StringBuilder time=new StringBuilder();
            InputStreamReader reader=new InputStreamReader(in);
            for(int c=reader.read();c!=-1;c=reader.read()){
                time.append((char)c);
            }
        } catch (Exception e) {
            if(socket!=null){
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
