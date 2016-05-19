/**
 * 
 */
package DistributeDataBase.rule.config.beans;

/** 
* @ClassName: MappingRuleBean 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午6:51:05 
*  
*/
public class MappingRuleBean {
    private String parameter;
    private String expression;
    private String mappingRuleBeanId;

    public String getParameter() {
        return this.parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getMappingRuleBeanId() {
        return this.mappingRuleBeanId;
    }

    public void setMappingRuleBeanId(String mappingRuleBeanId) {
        this.mappingRuleBeanId = mappingRuleBeanId;
    }
}
