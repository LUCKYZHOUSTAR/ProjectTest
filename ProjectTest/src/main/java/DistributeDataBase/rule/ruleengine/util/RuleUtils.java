/**
 * 
 */
package DistributeDataBase.rule.ruleengine.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import DistributeDataBase.common.sqljep.function.Comparative;
import DistributeDataBase.rule.bean.AdvancedParameter;
import DistributeDataBase.rule.bean.RuleChainImp;
import DistributeDataBase.rule.groovy.GroovyContextHelper;
import DistributeDataBase.rule.ruleengine.entities.abstractentities.SharedElement;
import DistributeDataBase.rule.ruleengine.enumerator.Enumerator;
import DistributeDataBase.rule.ruleengine.enumerator.EnumeratorImp;
import DistributeDataBase.rule.ruleengine.rule.ListAbstractResultRule;

/** 
* @ClassName: RuleUtils 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午6:58:47 
*  
*/
public class RuleUtils {
    private static final Enumerator enumerator = new EnumeratorImp();

    public static Map<String, Set<Object>> getSamplingField(Map<String, Comparative> argumentsMap,
                                                            Set<AdvancedParameter> param) {
        Map enumeratedMap = new HashMap(param.size());
        for (AdvancedParameter entry : param) {
            String key = entry.key;
            try {
                Set samplingField = enumerator.getEnumeratedValue(
                    (Comparable) argumentsMap.get(key), entry.cumulativeTimes,
                    entry.atomicIncreateValue, entry.needMergeValueInCloseInterval);

                enumeratedMap.put(key, samplingField);
            } catch (UnsupportedOperationException e) {
                throw new UnsupportedOperationException("当前列分库分表出现错误，出现错误的列名是:" + entry.getKey(), e);
            }
        }

        return enumeratedMap;
    }

    public static String placeHolder(int bit, int table) {
        if (bit < String.valueOf(table).length()) {
            return String.valueOf(table);
        }
        int max = (int) Math.pow(10.0D, bit);
        int placedNumber = max + table;
        String xxxfixWithPlaceHoder = String.valueOf(placedNumber).substring(1);
        return xxxfixWithPlaceHoder;
    }

    public static Map<String, SharedElement> getSharedElemenetMapBySharedElementList(List<? extends SharedElement> sharedElementList) {
        Map returnMap = new HashMap();
        int index;
        if (sharedElementList != null) {
            index = 0;
            for (SharedElement sharedElement : sharedElementList) {
                returnMap.put(String.valueOf(index), sharedElement);
                index++;
            }
        }
        return returnMap;
    }

    public static void getShardingRule(String rule,
                                       Class<? extends ListAbstractResultRule> ruleClass) {
        if (StringUtils.isBlank(rule))
            throw new IllegalArgumentException("The rule can not be null!");
    }

    public static RuleChainImp getRuleChainByRuleStringList(List<Object> rules,
                                                            Class<? extends ListAbstractResultRule> ruleClass,
                                                            boolean isDatabase) {
        if ((rules == null) || (rules.isEmpty())) {
            return null;
        }
        List list = new ArrayList();
        RuleChainImp ruleChainImp = new RuleChainImp();
        for (Iterator i$ = rules.iterator(); i$.hasNext();) {
            Object ruleString = i$.next();
            if ((ruleString instanceof String)) {
                ListAbstractResultRule listRule = getRuleInstance(ruleClass);

                listRule.setExpression(((String) ruleString).trim());

                listRule.setContext(GroovyContextHelper.getContext());

                list.add(listRule);
            } else if ((ruleString instanceof ListAbstractResultRule)) {
                list.add((ListAbstractResultRule) ruleString);
            } else {
                throw new IllegalArgumentException("not support rule type : "
                                                   + ruleString.getClass());
            }
        }

        ruleChainImp.setListResultRule(list);
        ruleChainImp.setDatabaseRuleChain(isDatabase);
        ruleChainImp.init();
        return ruleChainImp;
    }

    private static ListAbstractResultRule getRuleInstance(Class<? extends ListAbstractResultRule> ruleClass) {
        try {
            return (ListAbstractResultRule) ruleClass.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
