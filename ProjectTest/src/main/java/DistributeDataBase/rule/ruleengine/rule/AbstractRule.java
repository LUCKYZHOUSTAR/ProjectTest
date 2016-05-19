/**
 * 
 */
package DistributeDataBase.rule.ruleengine.rule;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import DistributeDataBase.rule.bean.AdvancedParameter;

/** 
* @ClassName: AbstractRule 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午6:59:53 
*  
*/
public abstract class AbstractRule {
    private static final Logger      log    = LoggerFactory.getLogger(AbstractRule.class);
    protected Set<AdvancedParameter> parameters;
    private boolean                  inited = false;
    protected String                 expression;

    protected abstract void initInternal();

    public void initRule() {
        if (this.inited) {
            if (log.isDebugEnabled())
                log.debug("rule has inited");
        } else {
            initInternal();
            this.inited = true;
        }
    }

    public Set<AdvancedParameter> getParameters() {
        return this.parameters;
    }

    public void setParameters(Set<String> parameters) {
        if (this.parameters == null) {
            this.parameters = new HashSet();
        }
        for (String str : parameters) {
            AdvancedParameter advancedParameter = getAdvancedParamByParamToken(str);
            this.parameters.add(advancedParameter);
        }
    }

    public void setAdvancedParameter(Set<AdvancedParameter> parameters) {
        if (this.parameters == null) {
            this.parameters = new HashSet();
        }
        for (AdvancedParameter keyAndAtomIncValue : parameters)
            this.parameters.add(keyAndAtomIncValue);
    }

    public void setAdvancedParameter(AdvancedParameter parameter) {
        if (this.parameters == null) {
            this.parameters = new HashSet();
        }
        this.parameters.add(parameter);
    }

    public String getExpression() {
        return this.expression;
    }

    protected AdvancedParameter getAdvancedParamByParamToken(String paramToken) {
        AdvancedParameter param = new AdvancedParameter();
        String[] paramTokens = paramToken.split(",");

        int tokenLength = paramTokens.length;
        switch (tokenLength) {
            case 1:
                param.key = paramTokens[0];
                break;
            case 3:
                param.key = paramTokens[0];
                try {
                    Comparable atomicIncreateValue = getIncreatementValueByString(paramTokens);
                    param.atomicIncreateValue = atomicIncreateValue;
                    param.cumulativeTimes = Integer.valueOf(paramTokens[2]);

                    if (param.atomicIncreateValue != null)
                        param.needMergeValueInCloseInterval = true;
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("输入的参数不为Integer类型,参数为:" + paramToken, e);
                }

            default:
                throw new IllegalArgumentException("错误的参数个数，必须为1个或者3个，3个的时候为允许使用枚举时的数据");
        }
        return param;
    }

    private Comparable<?> getIncreatementValueByString(String[] paramTokens) {
        Comparable atomicIncreateValue = null;
        String atomicIncreateValueField = paramTokens[1];
        String[] fields = StringUtils.split(atomicIncreateValueField, "_");
        int length = fields.length;
        switch (length) {
            case 2:
                int calendarFieldType = 0;
                String fieldString = StringUtils.trim(fields[1]);

                if (StringUtils.equalsIgnoreCase("date", fieldString))
                    calendarFieldType = 5;
                else if (StringUtils.equalsIgnoreCase("month", fieldString))
                    calendarFieldType = 2;
                else if (StringUtils.equalsIgnoreCase("YEAR", fieldString)) {
                    calendarFieldType = 1;
                }
                DateEnumerationParameter dateEP = new DateEnumerationParameter(Integer.valueOf(
                    fields[0]).intValue(), calendarFieldType);

                atomicIncreateValue = dateEP;
                break;
            default:
                atomicIncreateValue = Integer.valueOf(paramTokens[1]);
        }

        return atomicIncreateValue;
    }

    public void setExpression(String expression) {
        if (expression != null)
            this.expression = expression;
    }

    public void setParameter(String parameterArray) {
        if ((parameterArray != null) && (parameterArray.length() != 0)) {
            String[] paramArray = parameterArray.split("\\|");
            Set paramSet = new HashSet(Arrays.asList(paramArray));
            setParameters(paramSet);
        }
    }

    public void setContext(Map<String, Object> context) {
    }
}
