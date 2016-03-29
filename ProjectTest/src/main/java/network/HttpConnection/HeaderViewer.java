/**     
 * @FileName: HeaderViewer.java   
 * @Package:network.HttpConnection   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年3月29日 上午10:06:44   
 * @version V1.0     
 */
package network.HttpConnection;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

/**  
 * @ClassName: HeaderViewer   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年3月29日 上午10:06:44     
 */
public class HeaderViewer {
    public static void main(String args[]) {

        try {
            URL u = new URL("http://www.cafeaulait.org/books/jnp3/examples/15/HeaderViewer.java");
            URLConnection uc = u.openConnection();
            System.out.println("Content-type: " + uc.getContentType());
            System.out.println("Content-encoding: " + uc.getContentEncoding());
            System.out.println("Date: " + new Date(uc.getDate()));
            System.out.println("Last modified: " + new Date(uc.getLastModified()));
            System.out.println("Expiration date: " + new Date(uc.getExpiration()));
            System.out.println("Content-length: " + uc.getContentLength());
        } // end try
        catch (MalformedURLException ex) {
            System.err.println( " is not a URL I understand");
        } catch (IOException ex) {
            System.err.println(ex);
        }
        System.out.println();
    } // end main
}
