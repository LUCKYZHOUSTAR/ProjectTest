package DistributeDataBase.rule.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import DistributeDataBase.rule.groovy.GroovyListRuleEngine;
import DistributeDataBase.rule.ruleengine.entities.abstractentities.RuleChain;
import DistributeDataBase.rule.ruleengine.entities.convientobjectmaker.DefaultTableMapProvider;
import DistributeDataBase.rule.ruleengine.util.RuleUtils;

public class SimpleLogicTable extends LogicTable
{
  private static final Logger log = Logger.getLogger(SimpleLogicTable.class);
  String databases;
  String shardingKey;
  List<Object> tableRuleStringList;
  List<Object> databaseRuleStringList;
  boolean isSimpleTableMapPropertiesChanged;
  private SimpleTableMapProvider simpleTableMapProvider;
  SimpleListDatabaseMapProvider simpleDatabaseMapProvider;
  boolean useAutoGeneratingRule;

  public SimpleLogicTable()
  {
    this.simpleTableMapProvider = new SimpleTableMapProvider();

    this.simpleDatabaseMapProvider = new SimpleListDatabaseMapProvider();

    this.useAutoGeneratingRule = true;
  }

  protected boolean canUseAutoGenerationRule()
  {
    if (!this.useAutoGeneratingRule) {
      return false;
    }
    if (this.shardingKey == null) {
      return false;
    }
    return true;
  }

  protected void valid(int databasesSize, int tableSizeForEachDatabase) {
    if ((databasesSize == 0) || (tableSizeForEachDatabase == 0))
    {
      return;
    }
    int dividend = 0;
    int divisor = 0;
    if (databasesSize > tableSizeForEachDatabase) {
      dividend = databasesSize;
      divisor = tableSizeForEachDatabase;
    } else {
      dividend = tableSizeForEachDatabase;
      divisor = databasesSize;
    }
    if (dividend % divisor != 0)
      throw new IllegalArgumentException("分表个数必须是分库个数的倍数,分库是:" + databasesSize + "分表是:" + tableSizeForEachDatabase);
  }

  AutoGenerationRuleHandler decideAutoGenerationRuleHandler(int databaseSize, int tableSizeForEachDatabase)
  {
    if ((databaseSize <= 0) || (tableSizeForEachDatabase <= 0)) {
      throw new IllegalArgumentException("最少需要一个库,一张表");
    }
    if (databaseSize == 1) {
      if (tableSizeForEachDatabase == 1)
      {
        return new NoneAGRuleHandler();
      }

      return new TableAGRuleHandler();
    }

    if (tableSizeForEachDatabase == 1)
    {
      return new DatabaseAGRuleHandler();
    }

    return new DatabaseAndTableAGRuleHandler();
  }

  protected void processAutoGenerationRule()
  {
    if (!canUseAutoGenerationRule()) {
      return;
    }
    int databaseSize = this.simpleDatabaseMapProvider.getDatasourceKeys().size();
    int tablesNumberForEachDatabases = getTablesNumberForEachDatabases();
    valid(databaseSize, tablesNumberForEachDatabases);

    AutoGenerationRuleHandler agrHandler = decideAutoGenerationRuleHandler(databaseSize, tablesNumberForEachDatabases);

    String dbRule = agrHandler.getDatabaseRule(this.shardingKey, databaseSize, tablesNumberForEachDatabases);

    if ((dbRule != null) && (this.databaseRuleStringList == null)) {
      this.databaseRuleStringList = new ArrayList(1);
      if (log.isDebugEnabled()) {
        log.debug("auto generation rule for database: " + dbRule);
      }
      this.databaseRuleStringList.add(dbRule);
    }
    String tableRule = agrHandler.getTableRule(this.shardingKey, databaseSize, tablesNumberForEachDatabases);

    if (tableRule != null) {
      this.tableRuleStringList = new ArrayList(1);
      if (log.isDebugEnabled()) {
        log.debug("auto generation rule for database: " + tableRule);
      }
      this.tableRuleStringList.add(tableRule);
    }
  }

  protected int getTablesNumberForEachDatabases() {
    int tablesNumberForEachDatabases = this.simpleTableMapProvider.getTablesNumberForEachDatabases();
    if (tablesNumberForEachDatabases == -1)
    {
      tablesNumberForEachDatabases = this.simpleTableMapProvider.getTo() - this.simpleTableMapProvider.getFrom() + 1;
    }

    return tablesNumberForEachDatabases;
  }

  public void init()
  {
    boolean isDatabase = true;

    if (superClassDatabaseProviderIsNull())
    {
      setSimpleDatabaseMapToSuperLogicTable();
    }

    if ((superClassTableMapProviderIsNull()) && 
      (this.isSimpleTableMapPropertiesChanged)) {
      setTableMapProvider(this.simpleTableMapProvider);
    }

    processAutoGenerationRule();

    if (superClassDatabaseRuleIsNull()) {
      RuleChain rc = RuleUtils.getRuleChainByRuleStringList(this.databaseRuleStringList, GroovyListRuleEngine.class, isDatabase);

      this.listResultRule = rc;
    }

    if (transmitterTableRuleIsNull()) {
      RuleChain rc = RuleUtils.getRuleChainByRuleStringList(this.tableRuleStringList, GroovyListRuleEngine.class, !isDatabase);

      setTableRuleChain(rc);
    }
    super.init();
  }

  private boolean transmitterTableRuleIsNull() {
    return (getTableRule() == null) || (getTableRule().isEmpty());
  }

  private boolean superClassDatabaseRuleIsNull() {
    return this.listResultRule == null;
  }

  private boolean superClassTableMapProviderIsNull() {
    return (getTableMapProvider() == null) || ((getTableMapProvider() instanceof DefaultTableMapProvider));
  }

  protected void setSimpleDatabaseMapToSuperLogicTable()
  {
    if (this.databases == null) {
      return;
    }
    String[] databasesTokens = this.databases.split(",");
    this.simpleDatabaseMapProvider.setDatasourceKeys(Arrays.asList(databasesTokens));
    setDatabaseMapProvider(this.simpleDatabaseMapProvider);
  }

  private boolean superClassDatabaseProviderIsNull() {
    return getDatabaseMapProvider() == null;
  }

  public void setLogicTable(String logicTable)
  {
    setLogicTableName(logicTable);
  }

  public void setPadding(String padding) {
    this.isSimpleTableMapPropertiesChanged = true;
    this.simpleTableMapProvider.setPadding(padding);
  }

  public void setParentID(String parentID)
  {
    this.isSimpleTableMapPropertiesChanged = true;
    this.simpleTableMapProvider.setParentID(parentID);
  }

  public void setStep(int step)
  {
    this.isSimpleTableMapPropertiesChanged = true;
    this.simpleTableMapProvider.setStep(step);
  }

  public void setTableFactor(String tableFactor)
  {
    setLogicTableName(tableFactor);
  }

  public void setTablesNumberForEachDatabases(int tablesNumberForEachDatabases)
  {
    this.isSimpleTableMapPropertiesChanged = true;
    this.simpleTableMapProvider.setTablesNumberForEachDatabases(tablesNumberForEachDatabases);
  }

  public void setFrom(int from)
  {
    this.isSimpleTableMapPropertiesChanged = true;
    this.simpleTableMapProvider.setFrom(from);
  }

  public void setTo(int to)
  {
    this.isSimpleTableMapPropertiesChanged = true;
    this.simpleTableMapProvider.setTo(to);
  }

  public void setType(String type)
  {
    this.isSimpleTableMapPropertiesChanged = true;
    this.simpleTableMapProvider.setType(type);
  }

  public void setWidth(int width)
  {
    this.isSimpleTableMapPropertiesChanged = true;
    this.simpleTableMapProvider.setWidth(width);
  }

  public String getDatabases() {
    return this.databases;
  }

  public void setDatabases(String databases) {
    this.databases = databases;
  }

  public List<Object> getTableRuleStringList() {
    return this.tableRuleStringList;
  }

  public void setTableRuleStringList(List<Object> tableRuleStringList) {
    this.tableRuleStringList = tableRuleStringList;
  }

  public List<Object> getDatabaseRuleStringList() {
    return this.databaseRuleStringList;
  }

  public void setDatabaseRuleStringList(List<Object> databaseRuleStringList) {
    this.databaseRuleStringList = databaseRuleStringList;
  }

  public boolean isUseAutoGeneratingRule() {
    return this.useAutoGeneratingRule;
  }

  public void setUseAutoGeneratingRule(boolean useAutoGeneratingRule) {
    this.useAutoGeneratingRule = useAutoGeneratingRule;
  }

  public String getShardingKey() {
    return this.shardingKey;
  }

  public void setShardingKey(String shardingKey) {
    this.shardingKey = shardingKey;
  }

  public void setSimpleTableMapProvider(SimpleTableMapProvider simpleTableMapProvider) {
    this.simpleTableMapProvider = simpleTableMapProvider;
  }

  public String toString()
  {
    return "SimpleLogicTable [databaseRuleStringList=" + this.databaseRuleStringList + ", databases=" + this.databases + ", isSimpleTableMapPropertiesChanged=" + this.isSimpleTableMapPropertiesChanged + ", shardingKey=" + this.shardingKey + ", simpleDatabaseMapProvider=" + this.simpleDatabaseMapProvider + ", simpleTableMapProvider=" + this.simpleTableMapProvider + ", tableRuleStringList=" + this.tableRuleStringList + ", useAutoGeneratingRule=" + this.useAutoGeneratingRule + ", defaultListResult=" + this.defaultListResult + ", defaultListResultStragety=" + this.defaultListResultStragety + ", listResultRule=" + this.listResultRule + ", subSharedElement=" + this.subSharedElement + "]";
  }

  static abstract interface AutoGenerationRuleHandler
  {
    public abstract String getTableRule(String paramString, int paramInt1, int paramInt2);

    public abstract String getDatabaseRule(String paramString, int paramInt1, int paramInt2);
  }

  static class DatabaseAndTableAGRuleHandler
    implements SimpleLogicTable.AutoGenerationRuleHandler
  {
    public String getDatabaseRule(String databaseShardingKey, int databaseSize, int tableSizeForEachDatabase)
    {
      int tablesSize = databaseSize * tableSizeForEachDatabase;
      StringBuilder sb = new StringBuilder();
      sb.append("(#").append(databaseShardingKey).append("#");
      if (tablesSize != 0) {
        sb.append(" % ").append(tablesSize);
      }
      sb.append(")");
      if (tableSizeForEachDatabase != 0) {
        sb.append(".intdiv(").append(tableSizeForEachDatabase).append(")");
      }
      return sb.toString();
    }

    public String getTableRule(String tableShardingKey, int databaseSize, int tableSizeForEachDatabase)
    {
      int tablesSize = databaseSize * tableSizeForEachDatabase;

      StringBuilder sb = new StringBuilder();

      sb.append("(#").append(tableShardingKey).append("#");
      if (tablesSize != 0) {
        sb.append(" % ").append(tablesSize);
      }
      sb.append(")");
      if (tableSizeForEachDatabase != 0) {
        sb.append(" % ").append(tableSizeForEachDatabase);
      }
      return sb.toString();
    }
  }

  static class NoneAGRuleHandler
    implements SimpleLogicTable.AutoGenerationRuleHandler
  {
    public String getDatabaseRule(String databaseShardingKey, int tablesSize, int tableSizeForEachDatabase)
    {
      return null;
    }

    public String getTableRule(String tableShardingKey, int tablesSize, int tableSizeForEachDatabase)
    {
      return null;
    }
  }

  static class DatabaseAGRuleHandler extends SimpleLogicTable.DatabaseAndTableAGRuleHandler
    implements SimpleLogicTable.AutoGenerationRuleHandler
  {
    public String getTableRule(String tableShardingKey, int databaseSize, int tableSizeForEachDatabase)
    {
      return null;
    }

    public String getDatabaseRule(String databaseShardingKey, int databaseSize, int tableSizeForEachDatabase)
    {
      StringBuilder sb = new StringBuilder();
      sb.append("#").append(databaseShardingKey).append("#");
      if (databaseSize != 0) {
        sb.append(" % ").append(databaseSize);
      }
      sb.append("");
      return sb.toString();
    }
  }

  static class TableAGRuleHandler extends SimpleLogicTable.DatabaseAndTableAGRuleHandler
    implements SimpleLogicTable.AutoGenerationRuleHandler
  {
    public String getTableRule(String tableShardingKey, int databaseSize, int tableSizeForEachDatabase)
    {
      StringBuilder sb = new StringBuilder();

      sb.append("#").append(tableShardingKey).append("#");
      if (tableSizeForEachDatabase != 0) {
        sb.append(" % ").append(tableSizeForEachDatabase);
      }
      return sb.toString();
    }

    public String getDatabaseRule(String databaseShardingKey, int tablesSize, int tableSizeForEachDatabase)
    {
      return null;
    }
  }
}