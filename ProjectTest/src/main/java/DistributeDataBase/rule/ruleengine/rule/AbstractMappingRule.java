/**
 * 
 */
package DistributeDataBase.rule.ruleengine.rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import DistributeDataBase.rule.bean.AdvancedParameter;
import DistributeDataBase.rule.groovy.GroovyListRuleEngine;

/** 
* @ClassName: AbstractMappingRule 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午7:01:10 
*  
*/
public abstract class AbstractMappingRule extends CartesianProductBasedListResultRule {
    private static final Logger    logger     = LoggerFactory.getLogger(AbstractMappingRule.class);

    protected GroovyListRuleEngine targetRule = new GroovyListRuleEngine();

    private String                 targetKey  = null;

    public ResultAndMappingKey evalueateSamplingField(SamplingField samplingField) {
        List columns = samplingField.getColumns();
        List enumFields = samplingField.getEnumFields();
        if ((columns != null) && (columns.size() == 1)) {
            Object target = null;
            if ((samplingField.getMappingValue() != null)
                && (samplingField.getMappingTargetKey().equals(this.targetKey))) {
                target = samplingField.getMappingValue();
            } else
                target = get(this.targetKey, (String) columns.get(0), enumFields.get(0));

            if (target == null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("target value is null");
                }
                return null;
            }
            Map argumentMap = new HashMap(1);

            argumentMap.put(this.targetKey, target);
            if (logger.isDebugEnabled()) {
                logger.debug(new StringBuilder().append("invoke target rule ,value is ")
                    .append(target).toString());
            }

            String resultString = this.targetRule.imvokeMethod(new Object[] { argumentMap });
            ResultAndMappingKey result = null;
            if (resultString != null) {
                result = new ResultAndMappingKey(resultString);
                result.mappingKey = target;
                result.mappingTargetColumn = this.targetKey;
            } else {
                throw new IllegalArgumentException("规则引擎的结果不能为null");
            }
            return result;
        }
        throw new IllegalStateException(new StringBuilder().append("列名不符要求:columns:")
            .append(columns).toString());
    }

    protected boolean ruleRequireThrowRuntimeExceptionWhenSetIsEmpty() {
        return true;
    }

    protected abstract Object get(String paramString1, String paramString2, Object paramObject);

    public CartesianProductBasedListResultRule getTargetRule() {
        return this.targetRule;
    }

    protected void initInternal() {
        if (this.targetRule == null) {
            throw new IllegalArgumentException("target rule is null");
        }

        this.targetRule.initRule();

        Set advancedParameters = this.targetRule.getParameters();
        if (advancedParameters.size() != 1) {
            throw new IllegalArgumentException("目标规则的参数必须为1个，才能使用映射规则");
        }

        AdvancedParameter advancedParameter = (AdvancedParameter) advancedParameters.iterator()
            .next();
        this.targetKey = advancedParameter.key;
        if ((this.targetKey == null) || (this.targetKey.length() == 0)) {
            throw new IllegalArgumentException("target key is null .");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("parse mapping rule , target rule is ").append(this.targetRule)
            .append("target target key is ").append(this.targetKey);

        if (logger.isDebugEnabled())
            logger.debug(sb.toString());
    }

    public void setExpression(String expression) {
        this.targetRule.setExpression(expression);
    }
}