/**
 * 
 */
package DistributeDataBase.rule.ruleengine.rule;

/** 
* @ClassName: ResultAndMappingKey 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午7:04:34 
*  
*/
public class ResultAndMappingKey {
    final String result;
    Object       mappingKey;
    String       mappingTargetColumn;

    public ResultAndMappingKey(String result) {
        this.result = result;
    }
}
