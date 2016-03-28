/**     
 * @FileName: QueryString.java   
 * @Package:network.URL   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年3月28日 下午5:10:07   
 * @version V1.0     
 */
package network.URL;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**  
 * @ClassName: QueryString   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年3月28日 下午5:10:07     
 */
public class QueryString {
    private StringBuffer query = new StringBuffer();

    public QueryString() {
    }

    public QueryString(String name, String value) {
        encode(name, value);
    }

    public synchronized void add(String name, String value) {
        query.append('&');
        encode(name, value);
    }

    private synchronized void encode(String name, String value) {
        try {
            query.append(URLEncoder.encode(name, "UTF-8"));
            query.append('=');
            query.append(URLEncoder.encode(value, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("Broken VM does not support UTF-8");
        }
    }

    public String getQuery() {
        return query.toString();
    }

    public String toString() {
        return getQuery();
    }

}
