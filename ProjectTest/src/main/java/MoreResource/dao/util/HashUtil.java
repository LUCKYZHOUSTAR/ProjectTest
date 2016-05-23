
package MoreResource.dao.util;

/** 
* @ClassName: HashUtil 
* @Description: 
* @author LUCKY
* @date 2016年5月19日 下午4:06:31 
*  
*/
public final class HashUtil {

    /**
     * a quite simple FNV hash
     *
     * @param bytes
     * @return
     */
    public static int quickHash(byte[] bytes) {
        if (bytes.length == 0) {
            return 0;
        }

        int h = 0;
        int len = bytes.length;
        for (int i = 0; i < len; i++) {
            h = (31 * h) ^ bytes[i];
        }

        return h & 0x3FF;
    }
}