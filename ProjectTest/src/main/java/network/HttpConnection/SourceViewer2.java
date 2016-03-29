/**     
 * @FileName: SourceViewer2.java   
 * @Package:network.HttpConnection   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年3月29日 上午9:53:26   
 * @version V1.0     
 */
package network.HttpConnection;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;

/**  
 * @ClassName: SourceViewer2   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年3月29日 上午9:53:26     
 */
public class SourceViewer2 {

    public static void main(String[] args) throws Exception {
        URL u=new URL(args[0]);
        URLConnection uc=u.openConnection();
        InputStream raw=uc.getInputStream();
        InputStream buffer=new BufferedInputStream(raw);
        Reader r=new InputStreamReader(buffer);
        int c=0;
        while((c=r.read())!=-1){
            System.out.println((char)c);
        }
        
        
    }
}
