/**
 * 
 */
package DistributeDataBase.rule.groovy;

import java.util.Map;

/** 
* @ClassName: GroovyRuleUtils 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午6:54:38 
*  
*/
public class GroovyRuleUtils {
    public static final String RULE_CONTEXT         = "context";
    public static final String IMPORT_STATIC_METHOD = "import static com.alipay.zdal.rule.groovy.staticmethod.GroovyStaticMethod.*;";

    protected static String buildArgumentsOutput(Map<Object, Object> var) {
        StringBuilder sb = new StringBuilder();
        if (var == null) {
            return "do not have variable";
        }
        for (Map.Entry entry : var.entrySet()) {
            sb.append("[").append(entry.getKey()).append("=").append(entry.getValue())
                .append("|type:")
                .append(entry.getValue() == null ? null : entry.getValue().getClass()).append("]");
        }

        return sb.toString();
    }
}
