/**     
 * @FileName: AllHeaders.java   
 * @Package:network.HttpConnection   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年3月29日 上午10:08:23   
 * @version V1.0     
 */
package network.HttpConnection;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**  
 * @ClassName: AllHeaders   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年3月29日 上午10:08:23     
 */
public class AllHeaders {
    public static void main(String args[]) {

        try {
            URL u = new URL("http://www.cafeaulait.org/books/jnp3/examples/15/AllHeaders.java");
            URLConnection uc = u.openConnection();
            for (int j = 1;; j++) {
                
                String header = uc.getHeaderField(j);
                if (header == null)
                    break;
                System.out.println(uc.getHeaderFieldKey(j) + ": " + header);
            } // end for
        } // end try
        catch (MalformedURLException ex) {
            System.err.println( " is not a URL I understand.");
        } catch (IOException ex) {
            System.err.println(ex);
        }
        System.out.println();

    } // end main
}
