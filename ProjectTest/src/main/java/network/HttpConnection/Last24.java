/**     
 * @FileName: Last24.java   
 * @Package:network.HttpConnection   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年3月29日 上午10:11:03   
 * @version V1.0     
 */
package network.HttpConnection;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

/**  
 * @ClassName: Last24   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年3月29日 上午10:11:03     
 */
public class Last24 {
    public static void main(String[] args) {

        // Initialize a Date object with the current date and time
        Date today = new Date();
        long millisecondsPerDay = 24 * 60 * 60 * 1000;

        try {
            URL u = new URL("http://www.cafeaulait.org/books/jnp3/examples/15/Last24.java");
            URLConnection uc = u.openConnection();
            System.out.println("Will retrieve file if it's modified since "
                               + new Date(uc.getIfModifiedSince()));
             
            uc.setIfModifiedSince((new Date(today.getTime() - millisecondsPerDay)).getTime());
            System.out.println("Will retrieve file if it's modified since "
                               + new Date(uc.getIfModifiedSince()));
            InputStream in = new BufferedInputStream(uc.getInputStream());
            Reader r = new InputStreamReader(in);
            int c;
            while ((c = r.read()) != -1) {
                System.out.print((char) c);
            }
            System.out.println();

        } catch (Exception ex) {
            System.err.println(ex);
        }

    }
}
