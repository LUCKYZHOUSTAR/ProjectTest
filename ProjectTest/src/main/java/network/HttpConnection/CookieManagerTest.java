/**     
 * @FileName: CookieManager.java   
 * @Package:network.HttpConnection   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年3月29日 上午9:24:16   
 * @version V1.0     
 */
package network.HttpConnection;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;

/**  
 * @ClassName: CookieManager   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年3月29日 上午9:24:16     
 */
public class CookieManagerTest {

    public static void main(String[] args) {
        CookieManager manager=new CookieManager();
        //接受所有的Cookie
        manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(manager);
        
        CookieStore store=manager.getCookieStore();
        
        
    }
}
