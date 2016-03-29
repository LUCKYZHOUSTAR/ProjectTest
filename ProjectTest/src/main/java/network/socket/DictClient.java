/**     
 * @FileName: DictClient.java   
 * @Package:network.socket   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年3月29日 下午2:03:35   
 * @version V1.0     
 */
package network.socket;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;

/**  
 * @ClassName: DictClient   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年3月29日 下午2:03:35     
 */
public class DictClient {

    public static void main(String[] args) {

        try(Socket socket=new Socket("", 9098)) {
            socket.setSoTimeout(15000);
            OutputStream out=socket.getOutputStream();
            Writer writer=new OutputStreamWriter(out);
            InputStream in=socket.getInputStream();
            BufferedReader reader=new BufferedReader(new InputStreamReader(in));
            
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
