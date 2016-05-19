package DistributeDataBase.rule.bean;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import DistributeDataBase.rule.ruleengine.entities.abstractentities.ListSharedElement;
import DistributeDataBase.rule.ruleengine.entities.abstractentities.OneToManyEntry;
import DistributeDataBase.rule.ruleengine.entities.abstractentities.RuleChain;
import DistributeDataBase.rule.ruleengine.entities.abstractentities.SharedElement;
import DistributeDataBase.rule.ruleengine.entities.abstractentities.TablePropertiesSetter;
import DistributeDataBase.rule.ruleengine.entities.convientobjectmaker.TableMapProvider;
import DistributeDataBase.rule.ruleengine.entities.inputvalue.CalculationContextInternal;
import DistributeDataBase.rule.ruleengine.entities.retvalue.TargetDB;
import DistributeDataBase.rule.ruleengine.rule.ListAbstractResultRule;
import DistributeDataBase.rule.ruleengine.util.RuleUtils;

public class Database extends ListSharedElement
  implements TablePropertiesSetter
{
  protected static final Logger log = Logger.getLogger(Database.class);
  private String dataSourceKey;
  private String logicTableName;
  private List<ListAbstractResultRule> tableRuleList;
  private OneToManyEntry oneToManyEntry;
  private TableMapProvider tableMapProvider;

  public void init()
  {
    initTableRuleChain();
    initLogicTableName();
    initTableMapProvider();
    initDefaultListResultStragety();

    if (this.tableMapProvider != null) {
      Map beConstructedMap = getTableMapByTableMapProvider();
      putAutoConstructedMapIntoCurrentTagMap(beConstructedMap);
    }

    super.init();
  }

  private void initDefaultListResultStragety() {
    if (this.defaultListResultStragety == null)
      this.defaultListResultStragety = this.oneToManyEntry.getDefaultListResultStragety();
  }

  public void calculateTable(TargetDB targetDB, Field sourceTrace, Map<RuleChain, CalculationContextInternal> map)
  {
    CalculationContextInternal calculationContext = (CalculationContextInternal)map.get(this.listResultRule);
    Set resultSet = null;

    if (calculationContext == null)
    {
      if ((this.subSharedElement != null) && (this.subSharedElement.size() == 1))
      {
        resultSet = builSingleTable();
      }
      else
        resultSet = buildDefaultTable();
    }
    else if ((sourceTrace == null) || (sourceTrace.sourceKeys.isEmpty()))
    {
      ListAbstractResultRule rule = calculationContext.ruleChain.getRuleByIndex(calculationContext.index);

      Map argsFromSQL = getEnumeratedSqlArgsMap(calculationContext, rule);
      resultSet = rule.evalWithoutSourceTrace(argsFromSQL, null, null);
    }
    else {
      ListAbstractResultRule rule = calculationContext.ruleChain.getRuleByIndex(calculationContext.index);

      Map argsFromSQL = getEnumeratedSqlArgsMap(calculationContext, rule);

      Map sourceKeys = sourceTrace.sourceKeys;
      for (Map.Entry entry : sourceKeys.entrySet()) {
        if (argsFromSQL.containsKey(entry.getKey())) {
          argsFromSQL.put(entry.getKey(), entry.getValue());
          if (log.isDebugEnabled()) {
            log.debug("put entry: " + entry + " to args");
          }

        }

      }

      resultSet = rule.evalWithoutSourceTrace(argsFromSQL, sourceTrace.mappingTargetColumn, sourceTrace.mappingKeys);
    }

    buildTableNameSet(targetDB, resultSet);
  }

  private void buildTableNameSet(TargetDB targetDB, Set<String> resultSet) {
    for (String key : resultSet) {
      Table table = (Table)this.subSharedElement.get(key);
      if (table == null) {
        throw new IllegalArgumentException("cant find table by target index :" + key + " current sub tables is " + this.subSharedElement);
      }

      targetDB.addOneTable(table.getTableName());
    }
  }

  private Map<String, Set<Object>> getEnumeratedSqlArgsMap(CalculationContextInternal calculationContext, ListAbstractResultRule rule)
  {
    if (rule == null) {
      throw new IllegalStateException("should not be here");
    }

    Map argsFromSQL = RuleUtils.getSamplingField(calculationContext.sqlArgs, rule.getParameters());

    return argsFromSQL;
  }

  private Set<String> buildDefaultTable()
  {
    Set resultMap = new HashSet();
    for (String defaultIndex : this.defaultListResult) {
      resultMap.add(defaultIndex);
    }
    return resultMap;
  }

  private Set<String> builSingleTable() {
    Set resultMap = new HashSet(1);
    for (String key : this.subSharedElement.keySet()) {
      resultMap.add(key);
    }
    return resultMap;
  }

  void initTableMapProvider()
  {
    if (this.tableMapProvider == null)
      this.tableMapProvider = this.oneToManyEntry.getTableMapProvider();
  }

  void initLogicTableName()
  {
    String logicTable = this.oneToManyEntry.getLogicTableName();
    if ((this.logicTableName == null) || (this.logicTableName.length() == 0))
      this.logicTableName = logicTable;
  }

  void initTableRuleChain()
  {
    RuleChain ruleChain = this.oneToManyEntry.getTableRuleChain();

    if (this.tableRuleList != null) {
      if (this.listResultRule != null) {
        throw new IllegalArgumentException("有tableRuleList但又指定了ruleChain");
      }
      this.listResultRule = OneToManyEntry.getRuleChain(this.tableRuleList);
    }

    if (this.listResultRule == null) {
      this.listResultRule = ruleChain;
    }

    if (ruleChain != null)
      this.listResultRule.init();
    else
      log.warn("rule chain size is 0");
  }

  protected Map<String, SharedElement> getTableMapByTableMapProvider()
  {
    TableMapProvider provider = getTableMapProvider();
    provider.setParentID(getId());
    provider.setLogicTable(getLogicTableName());
    Map beConstructedMap = provider.getTablesMap();
    return beConstructedMap;
  }

  protected void putAutoConstructedMapIntoCurrentTagMap(Map<String, SharedElement> beingConstructedMap)
  {
    if (this.subSharedElement == null) {
      this.subSharedElement = beingConstructedMap;
    }
    else if (beingConstructedMap != null)
    {
      beingConstructedMap.putAll(this.subSharedElement);
      this.subSharedElement = beingConstructedMap;
    }
  }

  public void setTables(Map<String, SharedElement> tablesMap)
  {
    this.subSharedElement = tablesMap;
  }

  public Map<String, SharedElement> getTables()
  {
    return this.subSharedElement;
  }

  private Map<String, SharedElement> getTablesMapByStringList(List<String> tablesString)
  {
    List tables = null;
    tables = new ArrayList(tablesString.size());

    for (String tabName : tablesString) {
      Table tab = new Table();
      tab.setTableName(tabName);
      tables.add(tab);
    }
    Map returnMap = RuleUtils.getSharedElemenetMapBySharedElementList(tables);

    return returnMap;
  }

  protected void setTablesMapString(Map<String, String> tablesMapString)
  {
    Map beingConstructedMap = new HashMap(tablesMapString.size());

    for (Map.Entry entry : tablesMapString.entrySet()) {
      Table table = new Table();
      table.setTableName((String)entry.getValue());
      beingConstructedMap.put(entry.getKey(), table);
    }
    putAutoConstructedMapIntoCurrentTagMap(beingConstructedMap);
  }

  public void setTablesMapSimple(Object obj)
  {
    if ((obj instanceof Map))
      setTablesMapString((Map)obj);
    else if ((obj instanceof List))
      setTablesList((List)obj);
  }

  protected void setTablesList(List<String> tablesString)
  {
    if (tablesString.size() == 1) {
      String[] tokens = ((String)tablesString.get(0)).split(",");
      tablesString = new ArrayList();
      tablesString.addAll(Arrays.asList(tokens));
      putAutoConstructedMapIntoCurrentTagMap(getTablesMapByStringList(tablesString));
    } else {
      putAutoConstructedMapIntoCurrentTagMap(getTablesMapByStringList(tablesString));
    }
  }

  public String getDataSourceKey() {
    return this.dataSourceKey;
  }

  public void setDataSourceKey(String dataSourceKey) {
    this.dataSourceKey = dataSourceKey;
  }

  public String toString()
  {
    return "Database [dataSourceKey=" + this.dataSourceKey + ", defaultListResult=" + this.defaultListResult + ", defaultListResultStragety=" + this.defaultListResultStragety + ", listResultRule=" + this.listResultRule + ", subSharedElement=" + this.subSharedElement + "]";
  }

  public void setLogicTableName(String logicTable)
  {
    this.logicTableName = logicTable;
  }

  public void setTableMapProvider(TableMapProvider tableMapProvider) {
    this.tableMapProvider = tableMapProvider;
  }

  public void setTableRule(List<ListAbstractResultRule> tableRule) {
    this.tableRuleList = tableRule;
  }

  public String getLogicTableName() {
    return this.logicTableName;
  }

  public TableMapProvider getTableMapProvider() {
    return this.tableMapProvider;
  }

  public RuleChain getRuleChain() {
    return this.listResultRule;
  }

  public void setTableRuleChain(RuleChain ruleChain) {
    this.listResultRule = ruleChain;
  }

  public void put(OneToManyEntry oneToManyEntry) {
    this.oneToManyEntry = oneToManyEntry;
  }
}