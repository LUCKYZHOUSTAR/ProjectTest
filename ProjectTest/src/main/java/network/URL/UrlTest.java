/**     
 * @FileName: UrlTest.java   
 * @Package:network.URL   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年3月28日 下午4:06:23   
 * @version V1.0     
 */
package network.URL;

import java.net.MalformedURLException;
import java.net.URL;

/**  
 * @ClassName: UrlTest   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年3月28日 下午4:06:23     
 */
public class UrlTest {

    
    public static void main(String[] args)  {
      try {
        URL url=new URL("https", "www.baidu.com", "");
    } catch (MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
      
      
    }
}
