/**
 * 
 */
package DistributeDataBase.rule.ruleengine.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import DistributeDataBase.common.sqljep.function.Comparative;
import DistributeDataBase.rule.ruleengine.enumerator.Enumerator;
import DistributeDataBase.rule.ruleengine.enumerator.EnumeratorImp;
import DistributeDataBase.rule.ruleengine.util.RuleUtils;

/** 
* @ClassName: CartesianProductBasedListResultRule 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午7:01:31 
*  
*/
public abstract class CartesianProductBasedListResultRule extends ListAbstractResultRule {
    private static final Logger log        = LoggerFactory
                                               .getLogger(CartesianProductBasedListResultRule.class);

    Enumerator                  enumerator = new EnumeratorImp();

    public Map<String, Field> eval(Map<String, Comparative> argumentsMap) {
        Map enumeratedMap = prepareEnumeratedMap(argumentsMap);
        if (log.isDebugEnabled()) {
            log.debug("Sampling filed message : " + enumeratedMap);
        }
        Map map = evalElement(enumeratedMap);
        decideWhetherOrNotToThrowSpecEmptySetRuntimeException(map);
        return map;
    }

    private void decideWhetherOrNotToThrowSpecEmptySetRuntimeException(Map<String, Field> map) {
        if (((map == null) || (map.isEmpty()))
            && (ruleRequireThrowRuntimeExceptionWhenSetIsEmpty()))
            throw new EmptySetRuntimeException();
    }

    public Map<String, Set<Object>> prepareEnumeratedMap(Map<String, Comparative> argumentsMap) {
        if (log.isDebugEnabled()) {
            log.debug("eval at CartesianProductRule ,param is " + argumentsMap);
        }

        Map enumeratedMap = RuleUtils.getSamplingField(argumentsMap, this.parameters);

        return enumeratedMap;
    }

    public Set<String> evalWithoutSourceTrace(Map<String, Set<Object>> enumeratedMap,
                                              String mappingTargetColumn, Set<Object> mappingKeys) {
        if (enumeratedMap.size() == 0) {
            return evalZeroArgumentExpression();
        }
        if (enumeratedMap.size() == 1) {
            return evalOneArgumentExpression(enumeratedMap, mappingTargetColumn, mappingKeys);
        }

        return evalMutiargumentsExpression(enumeratedMap, mappingTargetColumn, mappingKeys);
    }

    private Set<String> evalMutiargumentsExpression(Map<String, Set<Object>> enumeratedMap,
                                                    String mappingTargetColumn,
                                                    Set<Object> mappingKeys) {
        if ((mappingTargetColumn != null) || (mappingKeys != null)) {
            throw new IllegalArgumentException("多列枚举不支持使用映射规则");
        }

        CartesianProductCalculator cartiesianProductCalculator = new CartesianProductCalculator(
            enumeratedMap);

        Set set = new HashSet(16);
        for (SamplingField samplingField : cartiesianProductCalculator) {
            evalOnceAndAddToReturnSet(set, samplingField, 16);
        }

        return set;
    }

    private Set<String> evalZeroArgumentExpression() {
        List columns = new ArrayList(1);

        SamplingField samplingField = new SamplingField(columns, 1);

        Set set = new HashSet();

        evalOnceAndAddToReturnSet(set, samplingField, 0);

        if (((set == null) || (set.isEmpty()))
            && (ruleRequireThrowRuntimeExceptionWhenSetIsEmpty())) {
            throw new EmptySetRuntimeException();
        }
        return set;
    }

    private Set<String> evalOneArgumentExpression(Map<String, Set<Object>> enumeratedMap,
                                                  String mappingTargetColumn,
                                                  Set<Object> mappingKeys) {
        List columns = new ArrayList(1);
        Set enumeratedValues = null;
        for (Map.Entry entry : enumeratedMap.entrySet()) {
            columns.add(entry.getKey());
            enumeratedValues = (Set) entry.getValue();
        }

        SamplingField samplingField = new SamplingField(columns, 1);

        Set set = new HashSet(enumeratedValues.size());

        if (mappingKeys == null) {
            evalNormal(set, enumeratedValues, samplingField);
        } else {
            evalWithMappingKey(mappingTargetColumn, mappingKeys, set, enumeratedValues,
                samplingField);
        }

        if (((set == null) || (set.isEmpty()))
            && (ruleRequireThrowRuntimeExceptionWhenSetIsEmpty())) {
            throw new EmptySetRuntimeException();
        }
        return set;
    }

    private void evalWithMappingKey(String mappingTargetColumn, Set<Object> mappingKeys,
                                    Set<String> set, Set<Object> enumeratedValues,
                                    SamplingField samplingField) {
        samplingField.setMappingTargetKey(mappingTargetColumn);
        Iterator itr;
        Iterator i$;
        if (mappingKeys.size() == enumeratedValues.size()) {
            itr = mappingKeys.iterator();
            for (i$ = enumeratedValues.iterator(); i$.hasNext();) {
                Object value = i$.next();
                Object oneTargetKey = itr.next();
                samplingField.clear();
                samplingField.setMappingValue(oneTargetKey);
                samplingField.add(0, value);
                evalOnceAndAddToReturnSet(set, samplingField, enumeratedValues.size());
            }
        } else {
            throw new IllegalArgumentException("mapping映射后的targetKeys和输入的参数个数不等,mapping :"
                                               + mappingKeys + " " + "enumeratedValues is :"
                                               + enumeratedValues);
        }
    }

    private void evalNormal(Set<String> set, Set<Object> enumeratedValues,
                            SamplingField samplingField) {
        for (Iterator i$ = enumeratedValues.iterator(); i$.hasNext();) {
            Object value = i$.next();
            samplingField.clear();
            samplingField.add(0, value);
            evalOnceAndAddToReturnSet(set, samplingField, enumeratedValues.size());
        }
    }

    public Map<String, Field> evalElement(Map<String, Set<Object>> enumeratedMap) {
        if (enumeratedMap.size() == 1) {
            List columns = new ArrayList(1);
            Set enumeratedValues = null;
            for (Map.Entry entry : enumeratedMap.entrySet()) {
                columns.add(entry.getKey());
                enumeratedValues = (Set) entry.getValue();
            }

            SamplingField samplingField = new SamplingField(columns, 1);

            Map map = new HashMap(enumeratedValues.size());

            for (Iterator i$ = enumeratedValues.iterator(); i$.hasNext();) {
                Object value = i$.next();
                samplingField.clear();
                samplingField.add(0, value);
                evalOnceAndAddToReturnMap(map, samplingField, enumeratedValues.size());
            }

            return map;
        }

        if (enumeratedMap.size() == 0) {
            List columns = new ArrayList(1);
            SamplingField samplingField = new SamplingField(columns, 1);
            Map map = new HashMap(1);
            evalOnceAndAddToReturnMap(map, samplingField, 1);
            return map;
        }

        CartesianProductCalculator cartiesianProductCalculator = new CartesianProductCalculator(
            enumeratedMap);

        Map map = new HashMap(16);
        for (SamplingField samplingField : cartiesianProductCalculator) {
            evalOnceAndAddToReturnMap(map, samplingField, 16);
        }

        return map;
    }

    protected boolean ruleRequireThrowRuntimeExceptionWhenSetIsEmpty() {
        return false;
    }

    void evalOnceAndAddToReturnSet(Set<String> set, SamplingField samplingField, int valueSetSize) {
        ResultAndMappingKey resultAndMappingKey = evalueateSamplingField(samplingField);
        String targetIndex = resultAndMappingKey.result;

        if (targetIndex != null)
            set.add(targetIndex);
        else
            throw new IllegalArgumentException("规则引擎的结果不能为null");
    }

    void evalOnceAndAddToReturnMap(Map<String, Field> map, SamplingField samplingField,
                                   int valueSetSize) {
        ResultAndMappingKey returnAndMappingKey = evalueateSamplingField(samplingField);
        if (returnAndMappingKey != null) {
            String dbIndexStr = returnAndMappingKey.result;
            if (StringUtils.isBlank(dbIndexStr)) {
                throw new IllegalArgumentException("根据dbRule计算出的结果不能为null");
            }
            String[] dbIndexes = dbIndexStr.split(",");
            List values;
            Field colMap;
            int index;
            for (String dbIndex : dbIndexes) {
                List lists = samplingField.getColumns();
                values = samplingField.getEnumFields();

                colMap = prepareColumnMap(map, samplingField, dbIndex,
                    returnAndMappingKey.mappingTargetColumn, returnAndMappingKey.mappingKey);

                index = 0;
                for (String column : lists) {
                    Object value = values.get(index);
                    Set set = prepareEnumeratedSet(valueSetSize, colMap, column);
                    set.add(value);
                    index++;
                }
            }
        }
    }

    private Set<Object> prepareEnumeratedSet(int valueSetSize, Field colMap, String column) {
        Set set = (Set) colMap.sourceKeys.get(column);
        if (set == null) {
            set = new HashSet(valueSetSize);
            colMap.sourceKeys.put(column, set);
        }
        return set;
    }

    private Field prepareColumnMap(Map<String, Field> map, SamplingField samplingField,
                                   String targetIndex, String mappngTargetColumn,
                                   Object mappingValue) {
        Field colMap = (Field) map.get(targetIndex);
        if (colMap == null) {
            int size = samplingField.getColumns().size();
            colMap = new Field(size);
            map.put(targetIndex, colMap);
        }

        if ((mappngTargetColumn != null) && (colMap.mappingTargetColumn == null)) {
            colMap.mappingTargetColumn = mappngTargetColumn;
        }
        if (mappingValue != null) {
            if (colMap.mappingKeys == null) {
                colMap.mappingKeys = new HashSet();
            }
            colMap.mappingKeys.add(mappingValue);
        }

        return colMap;
    }

    public abstract ResultAndMappingKey evalueateSamplingField(SamplingField paramSamplingField);
}