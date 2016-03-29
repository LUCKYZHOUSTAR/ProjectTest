/**     
 * @FileName: EncodingAwareSourceViewer.java   
 * @Package:network.HttpConnection   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年3月29日 上午9:58:30   
 * @version V1.0     
 */
package network.HttpConnection;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**  
 * @ClassName: EncodingAwareSourceViewer   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年3月29日 上午9:58:30     
 */
public class EncodingAwareSourceViewer {
    public static void main(String[] args) {

        try {
            // set default encoding
            String encoding = "ISO-8859-1";
            URL u = new URL(
                "http://www.cafeaulait.org/books/jnp3/examples/15/EncodingAwareSourceViewer.java");
            URLConnection uc = u.openConnection();
            String contentType = uc.getContentType();
            System.out.println(contentType);
            int encodingStart = contentType.indexOf("charset=");
            if (encodingStart != -1) {
                encoding = contentType.substring(encodingStart + 8);
            }
            InputStream in = new BufferedInputStream(uc.getInputStream());
            Reader r = new InputStreamReader(in, encoding);
            int c;
            while ((c = r.read()) != -1) {
                System.out.print((char) c);
            }
        } catch (MalformedURLException ex) {
            System.err.println(args[0] + " is not a parseable URL");
        } catch (IOException ex) {
            System.err.println(ex);
        }
    } // end main
}
