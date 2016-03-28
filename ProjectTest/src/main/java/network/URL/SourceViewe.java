/**     
 * @FileName: SourceViewe.java   
 * @Package:network.URL   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年3月28日 下午5:03:42   
 * @version V1.0     
 */
package network.URL;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

/**  
 * @ClassName: SourceViewe   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年3月28日 下午5:03:42     
 */
public class SourceViewe {

    public static void main(String[] args) {
        if (args.length > 0) {
            try {
                //Open the URL for reading
                URL lUrl=new URL("https://www.baidu.com/");
//                URL u = new URL(80);
                InputStream in = lUrl.openStream();
                // buffer the input to increase performance 
                in = new BufferedInputStream(in);
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
    }
}
