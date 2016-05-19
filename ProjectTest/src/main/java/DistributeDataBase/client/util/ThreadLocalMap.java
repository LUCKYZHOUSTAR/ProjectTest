/**
 * 
 */
package DistributeDataBase.client.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
* @ClassName: ThreadLocalMap 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 上午9:33:33 
*  
*/
public class ThreadLocalMap {

    private static final Logger                       log           = LoggerFactory
                                                                        .getLogger(ThreadLocalMap.class);

    protected static ThreadLocal<Map<Object, Object>> threadContext = new ThreadLocal<>();

    public static void put(Object key, Object value) {
        getContextMap().put(key, value);
    }

    public static Object remove(Object key) {
        return getContextMap().remove(key);
    }

    public static Object get(Object key) {
        return getContextMap().get(key);
    }

    public static boolean containsKey(Object key) {
        return getContextMap().containsKey(key);
    }

    public static void reset() {
        getContextMap().clear();
    }

    protected static Map<Object, Object> getContextMap() {
        return (Map) threadContext.get();

    }

    private static class MapThreadLocal extends ThreadLocal<Map<Object, Object>> {

        protected Map<Object, Object> initialValue() {
            return new HashMap<Object, Object>() {
                private static final long serialVersionUID = 3637958959138295593L;

                public Object put(Object key, Object value) {
                    if (ThreadLocalMap.log.isDebugEnabled()) {
                        if (containsKey(key)) {
                            ThreadLocalMap.log.debug("Overwritten attribute to thread context: "
                                                     + key + " = " + value);
                        } else {
                            ThreadLocalMap.log.debug("Added attribute to thread context: " + key
                                                     + " = " + value);
                        }
                    }
                    return super.put(key, value);
                }
            };
        }
    }

}
