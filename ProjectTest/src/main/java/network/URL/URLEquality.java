/**     
 * @FileName: URLEquality.java   
 * @Package:network.URL   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年3月28日 下午5:06:42   
 * @version V1.0     
 */
package network.URL;

import java.net.MalformedURLException;
import java.net.URL;

/**  
 * @ClassName: URLEquality   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年3月28日 下午5:06:42     
 */
public class URLEquality {
public static void main(String[] args) {
    try {
        URL ibiblio = new URL ("http://www.ibiblio.org/");
        URL metalab = new URL("http://metalab.unc.edu/");
        if (ibiblio.equals(metalab)) {
          System.out.println(ibiblio + " is the same as " + metalab);
        }
        else {
          System.out.println(ibiblio + " is not the same as " + metalab);
        }
      }
      catch (MalformedURLException ex) {
        System.err.println(ex);
      }

    }
}

