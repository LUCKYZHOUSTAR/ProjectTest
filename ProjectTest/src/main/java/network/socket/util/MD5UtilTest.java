/**     
 * @FileName: MD5Util.java   
 * @Package:com.jiedaibao.coscore.biz   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月1日 下午4:31:34   
 * @version V1.0     
 */
package network.socket.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**  
 * @ClassName: MD5Util   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月1日 下午4:31:34     
 */
public class MD5UtilTest {
    public static String hex(byte[] array) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            sb.append(Integer.toHexString(array[i] & 0xFF | 0x100).substring(1, 3));
        }
        return sb.toString().toLowerCase();
    }

    public static String md5Hex(String message) {
        return md5Hex(message, "CP1252");
    }

    public static String md5Hex(String message, String encode) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return hex(md.digest(message.getBytes(encode)));
        } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
        } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
        }
        return message;
    }
}
