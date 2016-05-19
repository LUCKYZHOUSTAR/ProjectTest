/**
 * 
 */
package DistributeDataBase.common.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
* @ClassName: NestThreadLocalMap 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午6:34:54 
*  
*/
public class NestThreadLocalMap {
    private static final Logger                             log           = LoggerFactory
                                                                              .getLogger(NestThreadLocalMap.class);

    protected static final ThreadLocal<Map<Object, Object>> threadContext = new MapThreadLocal();

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

    protected static Map<Object, Object> getContextMap() {
        return (Map) threadContext.get();
    }

    public static void reset() {
        getContextMap().clear();
    }

    private static class MapThreadLocal extends ThreadLocal<Map<Object, Object>> {
        protected Map<Object, Object> initialValue() {
            return new HashMap() {
                private static final long serialVersionUID = 3637958959138295593L;

                public Object put(Object key, Object value) {
                    if (NestThreadLocalMap.log.isDebugEnabled()) {
                        if (containsKey(key)) {
                            NestThreadLocalMap.log
                                .debug("Overwritten attribute to thread context: " + key + " = "
                                       + value);
                        } else {
                            NestThreadLocalMap.log.debug("Added attribute to thread context: "
                                                         + key + " = " + value);
                        }
                    }

                    return super.put(key, value);
                }
            };
        }
    }
}
