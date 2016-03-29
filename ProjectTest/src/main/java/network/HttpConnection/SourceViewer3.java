/**     
 * @FileName: SourceViewer3.java   
 * @Package:network.HttpConnection   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年3月29日 上午10:18:00   
 * @version V1.0     
 */
package network.HttpConnection;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**  
 * @ClassName: SourceViewer3   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年3月29日 上午10:18:00     
 */
public class SourceViewer3 {
    public static void main(String[] args) {

        for (int i = 0; i < 1; i++) {
            try {

                //Open the URLConnection for reading
                URL u = new URL(
                    "http://www.cafeaulait.org/books/jnp3/examples/15/SourceViewer3.java");
                URLConnection connection = u.openConnection();
                connection.setReadTimeout(1000);
                connection.setDoInput(true);
                connection.setDoOutput(true);
//                connection.setRequestMethod("POST");
                connection.setConnectTimeout(1000);
                connection.setRequestProperty("Accept", "text/xml,text/javascript,text/html");
                connection.setRequestProperty("User-Agent", "top-sdk-java");
                connection.setRequestProperty("Content-Type", "ZH-CN");
                HttpURLConnection uc = (HttpURLConnection) u.openConnection();

                int code = uc.getResponseCode();
                String response = uc.getResponseMessage();
                System.out.println("HTTP/1.x " + code + " " + response);
                for (int j = 1;; j++) {
                    String header = uc.getHeaderField(j);
                    String key = uc.getHeaderFieldKey(j);
                    if (header == null || key == null)
                        break;
                    System.out.println(uc.getHeaderFieldKey(j) + ": " + header);
                } // end for
                InputStream in = new BufferedInputStream(uc.getInputStream());
                // chain the InputStream to a Reader
                Reader r = new InputStreamReader(in);
                int c;
                while ((c = r.read()) != -1) {
                    System.out.print((char) c);
                }
            } catch (MalformedURLException ex) {
                System.err.println(args[0] + " is not a parseable URL");
            } catch (IOException ex) {
                System.err.println(ex);
            }

        } //  end if

    } // end main

}
