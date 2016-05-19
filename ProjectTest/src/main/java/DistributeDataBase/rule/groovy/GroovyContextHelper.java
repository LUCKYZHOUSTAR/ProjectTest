/**
 * 
 */
package DistributeDataBase.rule.groovy;

import java.util.Map;

/** 
* @ClassName: GroovyContextHelper 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午6:54:05 
*  
*/
public class GroovyContextHelper {
    private static Map<String, Object> context;

    public static Map<String, Object> getContext() {
        return context;
    }

    public static void setContext(Map<String, Object> context) {
        context = context;
    }
}
