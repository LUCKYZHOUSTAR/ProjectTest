/**     
 * @FileName: URLPrinter.java   
 * @Package:network.HttpConnection   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年3月29日 上午10:10:29   
 * @version V1.0     
 */
package network.HttpConnection;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**  
 * @ClassName: URLPrinter   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年3月29日 上午10:10:29     
 */
public class URLPrinter {
    public static void main(String args[]) {

        try {
            URL u = new URL("http://www.oreilly.com/");
            URLConnection uc = u.openConnection();
            System.out.println(uc.getURL());
        } catch (IOException ex) {
            System.err.println(ex);
        }

    }
}
