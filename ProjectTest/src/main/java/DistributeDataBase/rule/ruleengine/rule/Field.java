/**
 * 
 */
package DistributeDataBase.rule.ruleengine.rule;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/** 
* @ClassName: Field 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午7:03:19 
*  
*/
public class Field {
    public Map<String, Set<Object>> sourceKeys;
    public Set<Object>              mappingKeys;
    public String                   mappingTargetColumn;
    public static final Field       EMPTY_FIELD = new Field(0);

    public Field(int capacity) {
        this.sourceKeys = new HashMap(capacity);
    }
}
