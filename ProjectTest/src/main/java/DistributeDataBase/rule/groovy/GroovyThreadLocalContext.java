/**
 * 
 */
package DistributeDataBase.rule.groovy;

import java.util.Map;

/** 
* @ClassName: GroovyThreadLocalContext 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午6:55:05 
*  
*/
public class GroovyThreadLocalContext {
    private static ThreadLocal<Map<String, Object>> context = new ThreadLocal();

    public static Map<String, Object> getContext() {
        return (Map) context.get();
    }

    public static void setContext(Map<String, Object> context) {
        DistributeDataBase.rule.groovy.GroovyThreadLocalContext.context.set(context);
    }
}
