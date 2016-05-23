package DistributeDataBase.client.jdbc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;

import DistributeDataBase.client.config.DataSourceParameter;
import DistributeDataBase.client.config.ZdalConfig;
import DistributeDataBase.client.config.ZdalConfigListener;
import DistributeDataBase.client.config.ZdalDataSourceConfig;
import DistributeDataBase.client.config.controller.ZdalSignalResource;
import DistributeDataBase.client.controller.SpringBasedDispatcherImpl;
import DistributeDataBase.client.dataSource.keyweight.ZdalDataSourceKeyWeightRandom;
import DistributeDataBase.client.dataSource.keyweight.ZdalDataSourceKeyWeightRumtime;
import DistributeDataBase.client.exceptions.ZdalClientException;
import DistributeDataBase.common.Closable;
import DistributeDataBase.common.DBType;
import DistributeDataBase.common.RuntimeConfigHolder;
import DistributeDataBase.common.jdbc.sorter.DB2ExceptionSorter;
import DistributeDataBase.common.jdbc.sorter.MySQLExceptionSorter;
import DistributeDataBase.common.jdbc.sorter.OracleExceptionSorter;
import DistributeDataBase.common.util.TableSuffixTypeEnum;
import DistributeDataBase.parser.DefaultSQLParser;
import DistributeDataBase.parser.SQLParser;
import DistributeDataBase.rule.bean.GroovyTableDatabaseMapProvider;
import DistributeDataBase.rule.bean.LogicTable;
import DistributeDataBase.rule.bean.SimpleLogicTable;
import DistributeDataBase.rule.bean.SimpleNoneTableMapProvider;
import DistributeDataBase.rule.bean.SimpleTableDatabaseMapProvider;
import DistributeDataBase.rule.bean.SimpleTableMapProvider;
import DistributeDataBase.rule.bean.SimpleTableTwoColumnsMapProvider;
import DistributeDataBase.rule.bean.ZdalRoot;
import DistributeDataBase.rule.config.beans.AppRule;
import DistributeDataBase.rule.config.beans.ShardRule;
import DistributeDataBase.rule.config.beans.Suffix;
import DistributeDataBase.rule.config.beans.SuffixManager;
import DistributeDataBase.rule.config.beans.TableRule;
import DistributeDataBase.rule.ruleengine.entities.abstractentities.ListSharedElement;

public abstract class AbstractZdalDataSource extends ZdalDataSourceConfig
  implements DataSource, Closable, ZdalConfigListener
{
  private RuntimeConfigHolder<ZdalRuntime> runtimeConfigHolder;
  private SqlDispatcher writeDispatcher;
  private SqlDispatcher readDispatcher;
  private Map<String, ? extends Object> dataSourcePoolConfig;
  private Map<String, ? extends Object> rwDataSourcePoolConfig;
  private Map<String, ZdalDataSourceKeyWeightRandom> keyWeightMapConfig;
  private Map<String, String> keyWeightConfig;
  private int retryingTimes;
  private AppRule appRule;
  protected Map<String, ZDataSource> dataSourcesMap;
  private ZdalSignalResource zdalSignalResource;
  private static final Pattern weightPattern_r = Pattern.compile("[Rr](\\d*)");

  private static final Pattern weightPattern_w = Pattern.compile("[Ww](\\d*)");

  private static final Pattern weightPattern_p = Pattern.compile("[Pp](\\d*)");

  private static final Pattern weightPattern_q = Pattern.compile("[Qq](\\d*)");

  public AbstractZdalDataSource()
  {
    this.runtimeConfigHolder = new RuntimeConfigHolder();

    this.retryingTimes = 4;

    this.dataSourcesMap = new HashMap();

    this.zdalSignalResource = null;
  }

  public final void close()
    throws Throwable
  {
    if (!this.dataSourcesMap.isEmpty()) {
      for (DataSource dataSource : this.dataSourcesMap.values()) {
        try {
          ((ZDataSource)dataSource).destroy();
        } catch (Throwable e) {
          CONFIG_LOGGER.error("##Error, ZdalDataSource tried to close datasource occured unexpected exception.", e);
        }

      }

      this.dataSourcesMap.clear();
    }

    if (this.zdalSignalResource != null)
      this.zdalSignalResource.close();
  }

  public void resetWeight(Map<String, String> keyWeights)
  {
    resetZdalDataSource(keyWeights);
  }

  protected final void initDataSources(ZdalConfig zdalConfig)
  {
    if ((zdalConfig.getDataSourceParameters() == null) || (zdalConfig.getDataSourceParameters().isEmpty()))
    {
      throw new ZdalClientException("ERROR ## the datasource parameter is empty");
    }

    for (Map.Entry entry : zdalConfig.getDataSourceParameters().entrySet()) {
      try
      {
        ZDataSource zDataSource = new ZDataSource(createDataSourceDO((DataSourceParameter)entry.getValue(), zdalConfig.getDbType(), new StringBuilder().append(this.appDsName).append(".").append((String)entry.getKey()).toString()));

        this.dataSourcesMap.put(entry.getKey(), zDataSource);
      } catch (Exception e) {
        throw new ZdalClientException(new StringBuilder().append("ERROR ## create dsName = ").append((String)entry.getKey()).append(" dataSource failured").toString(), e);
      }
    }

    if (this.dbConfigType.isShard()) {
      this.dataSourcePoolConfig = getFailoverDataSourcePoolConfig(zdalConfig.getLogicPhysicsDsNames());

      this.appRule = zdalConfig.getAppRootRule();
      this.appRule.init();
      initForAppRule(this.appRule);
    } else if (this.dbConfigType.isShardFailover()) {
      this.dataSourcePoolConfig = getFailoverDataSourcePoolConfig(zdalConfig.getLogicPhysicsDsNames());

      this.keyWeightConfig = zdalConfig.getFailoverRules();
      this.appRule = zdalConfig.getAppRootRule();
      this.appRule.init();
      initForAppRule(this.appRule);
      CONFIG_LOGGER.warn(new StringBuilder().append("WARN ## the shardFailoverWeight of ").append(this.appDsName).append(" is :").append(getReceivDataStr(this.keyWeightConfig)).toString());
    }
    else if (this.dbConfigType.isShardGroup()) {
      this.rwDataSourcePoolConfig = zdalConfig.getGroupRules();
      this.appRule = zdalConfig.getAppRootRule();
      this.appRule.init();
      initForAppRule(this.appRule);
      CONFIG_LOGGER.warn(new StringBuilder().append("WARN ## the shardGroupWeight of ").append(this.appDsName).append(" is :").append(getReceivDataStr(zdalConfig.getGroupRules())).toString());
    }
    else if (this.dbConfigType.isGroup()) {
      this.rwDataSourcePoolConfig = zdalConfig.getGroupRules();
      initForLoadBalance(zdalConfig.getDbType());
      CONFIG_LOGGER.warn(new StringBuilder().append("WARN ## the GroupWeight of ").append(this.appDsName).append(" is :").append(getReceivDataStr(zdalConfig.getGroupRules())).toString());
    }

    initConfigListener();
  }

  private void initConfigListener()
  {
    this.zdalSignalResource = new ZdalSignalResource(this);
  }

  private void initForLoadBalance(DBType dbType) {
    Map dsSelectors = buildRwDbSelectors(this.rwDataSourcePoolConfig);
    this.runtimeConfigHolder.set(new ZdalRuntime(dsSelectors));
    setDbTypeForDBSelector(dbType);
  }

  private Map<String, DataSource> getFailoverDataSourcePoolConfig(Map<String, String> logicPhysicsDsNames)
  {
    Map logicDataSourcesMap = new HashMap();
    for (Map.Entry mEntry : logicPhysicsDsNames.entrySet()) {
      String key = ((String)mEntry.getKey()).trim();
      String value = ((String)mEntry.getValue()).trim();
      logicDataSourcesMap.put(key, this.dataSourcesMap.get(value));
    }
    return logicDataSourcesMap;
  }

  protected void initForAppRule(AppRule appRule) {
    Map dsSelectors = this.rwDataSourcePoolConfig == null ? buildDbSelectors(this.dataSourcePoolConfig) : buildRwDbSelectors(this.rwDataSourcePoolConfig);

    this.runtimeConfigHolder.set(new ZdalRuntime(dsSelectors));

    if ((this.keyWeightConfig != null) && (!this.keyWeightConfig.isEmpty()))
    {
      Map dataSourceKeyConfig = this.rwDataSourcePoolConfig == null ? this.dataSourcePoolConfig : this.rwDataSourcePoolConfig;

      this.keyWeightMapConfig = ZdalDataSourceKeyWeightRumtime.buildKeyWeightConfig(this.keyWeightConfig, dataSourceKeyConfig);

      if (this.keyWeightMapConfig == null) {
        throw new IllegalStateException("数据源key按分组权重配置错误,zdal初始化失败！");
      }
    }
    initForDispatcher(appRule);
  }

  private void initForDispatcher(AppRule appRule) {
    SQLParser parser = new DefaultSQLParser();
    this.writeDispatcher = buildSqlDispatcher(appRule.getMasterRule(), parser);
    this.readDispatcher = buildSqlDispatcher(appRule.getSlaveRule(), parser);
  }

  private SqlDispatcher buildSqlDispatcher(ShardRule shardRule, SQLParser parser) {
    if (shardRule == null) {
      return null;
    }
    ZdalRoot zdalRoot = new ZdalRoot();
    zdalRoot.setDBType(this.dbType);
    Map logicTableMap = new HashMap();
    if (shardRule.getTableRules() != null) {
      for (Map.Entry e : shardRule.getTableRules().entrySet()) {
        setDbTypeForDbIndex(this.dbType, ((TableRule)e.getValue()).getDbIndexArray());
        LogicTable logicTable = toLogicTable((TableRule)e.getValue());
        logicTable.setLogicTableName((String)e.getKey());
        logicTable.setDBType(this.dbType);

        logicTableMap.put(e.getKey(), logicTable);
      }
    }
    zdalRoot.setLogicTableMap(logicTableMap);
    if (shardRule.getDefaultDbIndex() != null) {
      zdalRoot.setDefaultDBSelectorID(shardRule.getDefaultDbIndex());
    }
    zdalRoot.init(this.appDsName);
    return buildSqlDispatcher(parser, zdalRoot);
  }

  private void setDbTypeForDbIndex(DBType dbType, String[] dbIndexes) {
    Map dbSelectors = ((ZdalRuntime)this.runtimeConfigHolder.get()).dbSelectors;

    for (String dbIndex : dbIndexes) {
      DBSelector dbs = (DBSelector)dbSelectors.get(dbIndex);
      if (dbs == null) {
        throw new IllegalArgumentException(new StringBuilder().append("规则配置错误：[").append(dbIndex).append("]在dataSourcePool中没有配置").toString());
      }
      dbs.setDbType(dbType);

      if ((dbs instanceof PriorityDbGroupSelector)) {
        EquityDbManager[] equityDbmanager = ((PriorityDbGroupSelector)dbs).getPriorityGroups();

        if (equityDbmanager == null) {
          throw new IllegalArgumentException("优先级的对等库并未初始化，请检查配置！");
        }
        for (int i = 0; i < equityDbmanager.length; i++)
          equityDbmanager[i].setDbType(dbType);
      }
    }
  }

  private void setDbTypeForDBSelector(DBType dbType)
  {
    Map dbSelectors = ((ZdalRuntime)this.runtimeConfigHolder.get()).dbSelectors;
    int i = 0;
    String[] dbIndexes = new String[dbSelectors.size()];
    for (Map.Entry dbselector : dbSelectors.entrySet()) {
      dbIndexes[(i++)] = ((String)dbselector.getKey()).trim();
    }
    setDbTypeForDbIndex(dbType, dbIndexes);
  }

  private SimpleTableMapProvider getTableMapProvider(TableRule tableRule)
  {
    SimpleTableMapProvider simpleTableMapProvider = null;
    SuffixManager suffixManager = tableRule.getSuffixManager();
    Suffix suf = suffixManager.getSuffix(0);
    if (suf.getTbType().equals(TableSuffixTypeEnum.twoColumnForEachDB.getValue())) {
      simpleTableMapProvider = new SimpleTableTwoColumnsMapProvider();
      SimpleTableTwoColumnsMapProvider twoColumns = (SimpleTableTwoColumnsMapProvider)simpleTableMapProvider;
      Suffix suf2 = suffixManager.getSuffix(1);
      twoColumns.setFrom2(suf2.getTbSuffixFrom());
      twoColumns.setTo2(suf2.getTbSuffixTo());
      twoColumns.setWidth2(suf2.getTbSuffixWidth());
      twoColumns.setPadding2(suf2.getTbSuffixPadding());
    } else if (TableSuffixTypeEnum.dbIndexForEachDB.getValue().equals(suf.getTbType())) {
      simpleTableMapProvider = new SimpleTableDatabaseMapProvider();
    } else if ((TableSuffixTypeEnum.groovyTableList.getValue().equals(suf.getTbType())) || (TableSuffixTypeEnum.groovyThroughAllDBTableList.getValue().equals(suf.getTbType())) || (TableSuffixTypeEnum.groovyAdjustTableList.getValue().equals(suf.getTbType())))
    {
      simpleTableMapProvider = new GroovyTableDatabaseMapProvider();
      try {
        GroovyTableDatabaseMapProvider groovyTableDatabaseMapProvider = (GroovyTableDatabaseMapProvider)simpleTableMapProvider;
        groovyTableDatabaseMapProvider.setTbType(suf.getTbType());
        groovyTableDatabaseMapProvider.setExpression(suffixManager.getExpression());
        groovyTableDatabaseMapProvider.setTbPreffix(tableRule.getTbPreffix());

        groovyTableDatabaseMapProvider.setDbNumber(tableRule.getDbIndexCount());
      } catch (TableRule.ParseException e) {
        throw new ZdalClientException("ERROR ## Tbsuffix的配置有问题！，请检查", e);
      }
    }
    else if (TableSuffixTypeEnum.none.getValue().equals(suf.getTbType())) {
      simpleTableMapProvider = new SimpleNoneTableMapProvider();
    } else {
      simpleTableMapProvider = new SimpleTableMapProvider();
    }
    return simpleTableMapProvider;
  }

  private LogicTable toLogicTable(TableRule tableRule) {
    SimpleLogicTable st = new SimpleLogicTable();
    st.setAllowReverseOutput(tableRule.isAllowReverseOutput());
    st.setDatabases(tableRule.getDbIndexes());
    if (tableRule.getDbRuleArray() != null) {
      List dbRules = new ArrayList(tableRule.getDbRuleArray().length);
      for (Object obj : tableRule.getDbRuleArray()) {
        dbRules.add((String)obj);
      }
      st.setDatabaseRuleStringList(dbRules);
    }
    if (tableRule.getTbRuleArray() != null) {
      List tbRules = new ArrayList(tableRule.getTbRuleArray().length);
      for (Object obj : tableRule.getTbRuleArray()) {
        tbRules.add((String)obj);
      }
      st.setTableRuleStringList(tbRules);

      st.setSimpleTableMapProvider(getTableMapProvider(tableRule));
      SuffixManager suffixManager = tableRule.getSuffixManager();
      Suffix suf = suffixManager.getSuffix(0);

      st.setFrom(suf.getTbSuffixFrom());
      st.setTo(suf.getTbSuffixTo());
      st.setWidth(suf.getTbSuffixWidth());
      st.setPadding(suf.getTbSuffixPadding());
      st.setTablesNumberForEachDatabases(suf.getTbNumForEachDb());
    }
    if (tableRule.getUniqueKeyArray() != null) {
      st.setUniqueKeys(Arrays.asList(tableRule.getUniqueKeyArray()));
    }
    if (tableRule.isDisableFullTableScan())
      st.setDefaultListResultStragety(ListSharedElement.DEFAULT_LIST_RESULT_STRAGETY.NONE);
    else {
      st.setDefaultListResultStragety(ListSharedElement.DEFAULT_LIST_RESULT_STRAGETY.FULL_TABLE_SCAN);
    }

    return st;
  }

  private SpringBasedDispatcherImpl buildSqlDispatcher(SQLParser parser, ZdalRoot zdalRoot) {
    if (zdalRoot != null) {
      SpringBasedDispatcherImpl dispatcher = new SpringBasedDispatcherImpl();
      dispatcher.setParser(parser);
      dispatcher.setRoot(zdalRoot);
      return dispatcher;
    }
    return null;
  }

  private DBSelector buildDbSelector(String dbIndex, DataSource[] dataSourceArray)
  {
    Map map = new HashMap(dataSourceArray.length);
    for (int i = 0; i < dataSourceArray.length; i++) {
      map.put(new StringBuilder().append(dbIndex).append("_").append(i).toString(), dataSourceArray[i]);
    }
    EquityDbManager dbSelector = new EquityDbManager(dbIndex, map);
    dbSelector.setAppDsName(this.appDsName);
    return dbSelector;
  }

  private DBSelector buildDbSelector(String dbIndex, List<DataSource> dataSourceList) {
    Map map = new HashMap(dataSourceList.size());
    int i = 0; for (int n = dataSourceList.size(); i < n; i++) {
      map.put(new StringBuilder().append(dbIndex).append("_").append(i).toString(), dataSourceList.get(i));
    }
    EquityDbManager dbSelector = new EquityDbManager(dbIndex, map);
    dbSelector.setAppDsName(this.appDsName);
    return dbSelector;
  }

  private Map<String, DBSelector> buildDbSelectors(Map<String, ? extends Object> dataSourcePool)
  {
    Map dsSelectors = new HashMap();
    for (Map.Entry e : dataSourcePool.entrySet()) {
      if ((e.getValue() instanceof ZdalConfigListener)) {
        OneDBSelector selector = new OneDBSelector((String)e.getKey(), (ZdalConfigListener)e.getValue());
        selector.setAppDsName(this.appDsName);
        dsSelectors.put(e.getKey(), selector);
      } else if ((e.getValue() instanceof DataSource[])) {
        dsSelectors.put(e.getKey(), buildDbSelector((String)e.getKey(), (DataSource[])e.getValue()));
      }
      else if ((e.getValue() instanceof List)) {
        dsSelectors.put(e.getKey(), buildDbSelector((String)e.getKey(), (List)e.getValue()));
      }
      else if ((e.getValue() instanceof DBSelector)) {
        dsSelectors.put(e.getKey(), (DBSelector)e.getValue());
      } else if ((e.getValue() instanceof String)) {
        String[] dbs = ((String)e.getValue()).split(",");
        if (dbs.length == 1) {
          int index = dbs[0].indexOf(":");
          String dsbeanId = index == -1 ? dbs[0] : dbs[0].substring(0, index);
          DataSource dataSource = getDataSourceObject(dsbeanId);
          OneDBSelector selector = new OneDBSelector((String)e.getKey(), dataSource);
          selector.setAppDsName(this.appDsName);
          dsSelectors.put(e.getKey(), selector);
        } else {
          DataSource[] dsArray = new ZdalConfigListener[dbs.length];
          for (int i = 0; i < dbs.length; i++) {
            dsArray[i] = getDataSourceObject(dbs[i]);
          }
          dsSelectors.put(e.getKey(), buildDbSelector((String)e.getKey(), dsArray));
        }
      }
      ((DBSelector)dsSelectors.get(e.getKey())).setDbType(this.dbType);
    }
    return dsSelectors;
  }

  private DataSource getDataSourceObject(String dsName)
  {
    DataSource dataSource = null;
    if (StringUtils.isBlank(dsName)) {
      throw new IllegalArgumentException("The dsName can't be null!");
    }

    dataSource = (ZdalConfigListener)this.dataSourcesMap.get(dsName.trim());

    if (dataSource == null) {
      throw new IllegalArgumentException(new StringBuilder().append("The dataSource can't be null,dsName=").append(dsName).toString());
    }
    return dataSource;
  }

  private Map<String, DBSelector> buildRwDbSelectors(Map<String, ? extends Object> dataSourcePool)
  {
    Map dsSelectors = new HashMap();
    for (Map.Entry e : dataSourcePool.entrySet()) {
      String rdbIndex = new StringBuilder().append((String)e.getKey()).append("_r").toString();
      String wdbIndex = new StringBuilder().append((String)e.getKey()).append("_w").toString();
      if ((e.getValue() instanceof ZdalConfigListener)) {
        OneDBSelector rSelector = new OneDBSelector(rdbIndex, (ZdalConfigListener)e.getValue());
        rSelector.setAppDsName(this.appDsName);
        dsSelectors.put(rdbIndex, rSelector);
        OneDBSelector wSelector = new OneDBSelector(wdbIndex, (ZdalConfigListener)e.getValue());
        wSelector.setAppDsName(this.appDsName);
        dsSelectors.put(wdbIndex, wSelector);
      } else if ((e.getValue() instanceof DataSource[])) {
        dsSelectors.put(rdbIndex, buildDbSelector(rdbIndex, (DataSource[])e.getValue()));
        dsSelectors.put(wdbIndex, buildDbSelector(wdbIndex, (DataSource[])e.getValue()));
      } else if ((e.getValue() instanceof List)) {
        dsSelectors.put(rdbIndex, buildDbSelector(rdbIndex, (List)e.getValue()));

        dsSelectors.put(wdbIndex, buildDbSelector(wdbIndex, (List)e.getValue()));
      }
      else if ((e.getValue() instanceof DBSelector)) {
        dsSelectors.put(rdbIndex, (DBSelector)e.getValue());
        dsSelectors.put(wdbIndex, (DBSelector)e.getValue());
      } else if ((e.getValue() instanceof String)) {
        parse(dsSelectors, (String)e.getKey(), (String)e.getValue());
      }
      ((DBSelector)dsSelectors.get(rdbIndex)).setDbType(this.dbType);
      ((DBSelector)dsSelectors.get(wdbIndex)).setDbType(this.dbType);
    }
    CONFIG_LOGGER.warn(new StringBuilder().append("warn ## \n").append(showDbSelectors(dsSelectors, dataSourcePool)).toString());
    return dsSelectors;
  }

  private String showDbSelectors(Map<String, DBSelector> dsSelectors, Map<String, ? extends Object> dataSourcePool)
  {
    StringBuilder sb = new StringBuilder();
    for (Map.Entry e : dataSourcePool.entrySet()) {
      sb.append("[").append((String)e.getKey()).append("=").append(e.getValue()).append("]");
    }
    sb.append("\nconvert to:\n");
    for (Map.Entry e : dsSelectors.entrySet()) {
      if ((e.getValue() instanceof EquityDbManager)) {
        EquityDbManager db = (EquityDbManager)e.getValue();
        sb.append((String)e.getKey()).append("=").append(db.getWeights()).append("\n");
      } else if ((e.getValue() instanceof PriorityDbGroupSelector)) {
        PriorityDbGroupSelector selector = (PriorityDbGroupSelector)e.getValue();
        sb.append(new StringBuilder().append(selector.getId()).append(": \n").toString());
        EquityDbManager[] dbs = selector.getPriorityGroups();
        for (EquityDbManager db : dbs) {
          sb.append(db.getId()).append(db.getWeights()).append("\n");
        }
      }

    }

    return sb.toString();
  }

  private void parseDbSelector(String[] databaseSources, String dbIndex, Map<String, DBSelector> dsSelectors, WeightRWPQEnum rwPriority)
  {
    Map initDataSourceGroups = new HashMap(1);

    Map weightGroups = new HashMap(1);

    for (int i = 0; i < databaseSources.length; i++)
    {
      String dsKey = new StringBuilder().append(dbIndex).append("_").append(i).toString();
      String[] beanIdAndWeight = databaseSources[i].split(":");
      DataSource dataSource = getDataSourceObject((String)super.getZdalConfig().getLogicPhysicsDsNames().get(beanIdAndWeight[0].trim()));

      int[] weightRWPQ = parseWeightRW(beanIdAndWeight.length == 2 ? beanIdAndWeight[1] : null);

      Map initDataSources = (Map)initDataSourceGroups.get(Integer.valueOf(weightRWPQ[rwPriority.value().intValue()]));

      if (initDataSources == null) {
        initDataSources = new HashMap(databaseSources.length);
        initDataSourceGroups.put(Integer.valueOf(weightRWPQ[rwPriority.value().intValue()]), initDataSources);
      }

      Map weights = (Map)weightGroups.get(Integer.valueOf(weightRWPQ[rwPriority.value().intValue()]));
      if (weights == null) {
        weights = new HashMap(databaseSources.length);
        weightGroups.put(Integer.valueOf(weightRWPQ[rwPriority.value().intValue()]), weights);
      }

      weights.put(dsKey, Integer.valueOf(weightRWPQ[(rwPriority.value().intValue() - 2)]));
      initDataSources.put(dsKey, dataSource);
    }

    if (initDataSourceGroups.size() == 1) {
      Map rInitDataSources = ((Map[])initDataSourceGroups.values().toArray(new Map[1]))[0];

      Map rWeights = ((Map[])weightGroups.values().toArray(new Map[1]))[0];
      EquityDbManager equityDbManager = new EquityDbManager(dbIndex, rInitDataSources, rWeights);

      if (this.dbType != null) {
        equityDbManager.setDbType(this.dbType);
      }
      equityDbManager.setAppDsName(this.appDsName);
      dsSelectors.put(dbIndex, equityDbManager);
    } else {
      List rpriorityKeys = new ArrayList(initDataSourceGroups.size());
      rpriorityKeys.addAll(initDataSourceGroups.keySet());
      Collections.sort(rpriorityKeys);
      EquityDbManager[] rpriorityGroups = new EquityDbManager[rpriorityKeys.size()];

      for (int i = 0; i < rpriorityGroups.length; i++) {
        Integer key = (Integer)rpriorityKeys.get(i);
        Map rInitDataSources = (Map)initDataSourceGroups.get(key);
        Map rWeights = (Map)weightGroups.get(key);
        EquityDbManager equityDbManager = new EquityDbManager(dbIndex, rInitDataSources, rWeights);

        if (this.dbType != null)
          equityDbManager.setDbType(this.dbType);
        equityDbManager.setAppDsName(this.appDsName);
        rpriorityGroups[i] = equityDbManager;
      }

      dsSelectors.put(dbIndex, new PriorityDbGroupSelector(dbIndex, rpriorityGroups));
    }
  }

  private void parse(Map<String, DBSelector> dsSelectors, String dbIndex, String commaDbs)
  {
    String rdbIndex = new StringBuilder().append(dbIndex).append("_r").toString();
    String wdbIndex = new StringBuilder().append(dbIndex).append("_w").toString();
    String[] dbs = commaDbs.split(",");

    if (dbs.length == 1) {
      int index = dbs[0].indexOf(":");
      String dsbeanId = index == -1 ? dbs[0] : dbs[0].substring(0, index);
      DataSource ds = getDataSourceObject((String)super.getZdalConfig().getLogicPhysicsDsNames().get(dsbeanId.trim()));

      OneDBSelector selectorRead = new OneDBSelector(rdbIndex, ds);
      selectorRead.setAppDsName(this.appDsName);
      OneDBSelector selectorWrite = new OneDBSelector(wdbIndex, ds);
      selectorWrite.setAppDsName(this.appDsName);
      dsSelectors.put(rdbIndex, selectorRead);
      dsSelectors.put(wdbIndex, selectorWrite);
    }
    else {
      parseDbSelector(dbs, wdbIndex, dsSelectors, WeightRWPQEnum.writePriority);
      parseDbSelector(dbs, rdbIndex, dsSelectors, WeightRWPQEnum.readPriority);
    }
  }

  private int[] parseWeightRW(String weight) {
    if (weight == null) {
      return new int[] { 10, 10, 0, 0 };
    }

    weight = weight.trim().toLowerCase();
    int r;
    int r;
    if ((weight.indexOf(82) == -1) && (weight.indexOf(114) == -1))
      r = 0;
    else
      r = parseNumber(weightPattern_r, weight, 10);
    int w;
    int w;
    if ((weight.indexOf(87) == -1) && (weight.indexOf(119) == -1))
      w = 0;
    else
      w = parseNumber(weightPattern_w, weight, 10);
    int p;
    int p;
    if ((weight.indexOf(80) == -1) && (weight.indexOf(112) == -1))
      p = 0;
    else
      p = parseNumber(weightPattern_p, weight, 0);
    int q;
    int q;
    if ((weight.indexOf(81) == -1) && (weight.indexOf(113) == -1))
      q = 0;
    else {
      q = parseNumber(weightPattern_q, weight, 0);
    }

    return new int[] { r, w, p, q };
  }

  private int parseNumber(Pattern p, String weight, int defaultValue)
  {
    Matcher m = p.matcher(weight);
    if (!m.find()) {
      throw new IllegalArgumentException(new StringBuilder().append("权重配置不符合正则式[Rr](\\d*)[Ww](\\d*)[Pp](\\d*)[Qq](\\d*)：").append(weight).toString());
    }

    if (m.group(1).length() == 0) {
      return defaultValue;
    }
    return Integer.parseInt(m.group(1));
  }

  public void resetZdalDataSource(Map<String, String> keyWeights)
  {
    try
    {
      long startReset = System.currentTimeMillis();
      if ((this.keyWeightConfig != null) && (!this.keyWeightConfig.isEmpty())) {
        resetKeyWeightConfig(keyWeights);
        String resetKeyWeightResults = getReceivDataStr(keyWeights);

        CONFIG_LOGGER.warn(new StringBuilder().append("WARN ## resetKeyWeightConfig[").append(this.appDsName).append("]:").append(resetKeyWeightResults).toString());

        CONFIG_LOGGER.warn(new StringBuilder().append("WARN ## reset the config success,cost ").append(System.currentTimeMillis() - startReset).append(" ms,the appDsName = ").append(this.appDsName).toString());

        this.keyWeightConfig = keyWeights;
      } else if ((this.rwDataSourcePoolConfig != null) && (!this.rwDataSourcePoolConfig.isEmpty())) {
        resetDbWeight(keyWeights);
        String dbWeightConfigs = getReceivDataStr(keyWeights);

        CONFIG_LOGGER.warn(new StringBuilder().append("WARN ## resetRwDataSourceConfig[").append(this.appDsName).append("]:").append(dbWeightConfigs).toString());

        CONFIG_LOGGER.warn(new StringBuilder().append("WARN ## reset the config success,cost ").append(System.currentTimeMillis() - startReset).append(" ms,the appDsName = ").append(this.appDsName).toString());

        this.rwDataSourcePoolConfig = keyWeights;
      } else {
        throw new ZdalClientException("ERROR ## only keyWeightConfig,rwDataSourcePoolConfig can reset weight");
      }
    }
    catch (Exception e)
    {
      throw new ZdalClientException(new StringBuilder().append("ERROR ## appDsName = ").append(this.zdalConfig.getAppDsName()).append(" reset config failured ").toString(), e);
    }
  }

  private void resetDbWeight(Map<String, String> p)
  {
    Map dbSelectors = ((ZdalRuntime)this.runtimeConfigHolder.get()).dbSelectors;
    for (Map.Entry entrySet : p.entrySet()) {
      String dbIndex = ((String)entrySet.getKey()).trim();
      String commaWeights = ((String)entrySet.getValue()).trim();

      if ((this.rwDataSourcePoolConfig != null) && (this.rwDataSourcePoolConfig.get(dbIndex) != null))
      {
        resetRwDbWeight(dbIndex, dbSelectors, commaWeights);
      } else if ((this.dataSourcePoolConfig != null) && (this.dataSourcePoolConfig.get(dbIndex) != null))
      {
        String[] rdwds = commaWeights.split(",");
        int[] weights = new int[rdwds.length];
        for (int i = 0; i < rdwds.length; i++) {
          weights[i] = Integer.parseInt(rdwds[i]);
        }
        resetDbWeight(dbIndex, dbSelectors, weights);
      }
    }
  }

  private void resetRwDbWeight(String dbIndex, Map<String, DBSelector> dbSelectors, String commaWeights)
  {
    String[] rdwds = commaWeights.split(",");
    int[] rWeights = new int[rdwds.length];
    int[] wWeights = new int[rdwds.length];
    for (int i = 0; i < rdwds.length; i++) {
      int[] weightRW = parseWeightRW(rdwds[i]);
      rWeights[i] = weightRW[0];
      wWeights[i] = weightRW[1];
    }
    resetDbWeight(new StringBuilder().append(dbIndex).append("_r").toString(), dbSelectors, rWeights);
    resetDbWeight(new StringBuilder().append(dbIndex).append("_w").toString(), dbSelectors, wWeights);
  }

  private void resetDbWeight(String dbIndex, Map<String, DBSelector> dbSelectors, int[] weights) {
    DBSelector dbSelector = (DBSelector)dbSelectors.get(dbIndex);
    if (dbSelector == null) {
      throw new ZdalClientException(new StringBuilder().append("ERROR ## Couldn't find dbIndex in current datasoures. dbIndex:").append(dbIndex).toString());
    }

    Map weightMap = new HashMap(weights.length);
    for (int i = 0; i < weights.length; i++) {
      weightMap.put(new StringBuilder().append(dbIndex).append("_").append(i).toString(), Integer.valueOf(weights[i]));
    }
    dbSelector.setWeight(weightMap);
  }

  protected void resetKeyWeightConfig(Map<String, String> p)
  {
    Map keyWeightMapHolder = this.keyWeightMapConfig;
    for (Map.Entry entrySet : p.entrySet()) {
      String groupKey = (String)entrySet.getKey();
      String value = (String)entrySet.getValue();
      if ((StringUtils.isBlank(groupKey)) || (StringUtils.isBlank(value))) {
        throw new ZdalClientException(new StringBuilder().append("ERROR ## 数据源groupKey=").append(groupKey).append("分组权重配置信息不能为空,value=").append(value).toString());
      }

      String[] keyWeightStr = value.split(",");
      String[] weightKeys = new String[keyWeightStr.length];
      int[] weights = new int[keyWeightStr.length];
      for (int i = 0; i < keyWeightStr.length; i++) {
        if (StringUtils.isBlank(keyWeightStr[i])) {
          throw new ZdalClientException(new StringBuilder().append("ERROR ## 数据源keyWeightStr[").append(i).append("]分组权重配置信息不能为空.").toString());
        }

        String[] keyAndWeight = keyWeightStr[i].split(":");
        if (keyAndWeight.length != 2) {
          throw new ZdalClientException(new StringBuilder().append("ERROR ## 数据源key按组配置权重错误,keyWeightStr[").append(i).append("]=").append(keyWeightStr[i]).append(".").toString());
        }

        String key = keyAndWeight[0];
        String weightStr = keyAndWeight[1];
        if ((StringUtils.isBlank(key)) || (StringUtils.isBlank(weightStr))) {
          CONFIG_LOGGER.error(new StringBuilder().append("ERROR ## 数据源分组权重配置信息不能为空,key=").append(key).append(",weightStr=").append(weightStr).toString());

          return;
        }
        weightKeys[i] = key.trim();
        weights[i] = Integer.parseInt(weightStr.trim());
      }

      ZdalDataSourceKeyWeightRandom weightRandom = (ZdalDataSourceKeyWeightRandom)keyWeightMapHolder.get(groupKey);
      if (weightRandom == null) {
        throw new ZdalClientException(new StringBuilder().append("ERROR ## 新推送的按数据源key分组权重配置中的key不对,非法的groupKey=").append(groupKey).toString());
      }

      for (String newKey : weightKeys) {
        if ((weightRandom.getWeightConfig() == null) || (!weightRandom.getWeightConfig().containsKey(newKey)))
        {
          throw new ZdalClientException(new StringBuilder().append("新推送的数据源分组").append(groupKey).append("权重配置中包含不属于该组的数据源标识,key=").append(newKey).toString());
        }
      }

      if (weightKeys.length != weightRandom.getDataSourceNumberInGroup()) {
        throw new ZdalClientException(new StringBuilder().append("新推送的按数据源key分组权重配置中，分组groupKey=").append(groupKey).append("包含的数据源个数不对 ,size=").append(weightKeys.length).append(",the size should be ").append(weightRandom.getDataSourceNumberInGroup()).toString());
      }

      ZdalDataSourceKeyWeightRandom TDataSourceKeyWeightRandom = new ZdalDataSourceKeyWeightRandom(weightKeys, weights);

      keyWeightMapHolder.put(groupKey, TDataSourceKeyWeightRandom);
    }

    this.keyWeightMapConfig = keyWeightMapHolder;
  }

  private String getReceivDataStr(Map<String, String> p)
  {
    String str = "";
    if (p != null) {
      StringBuilder sb = new StringBuilder();
      for (Map.Entry entry : p.entrySet()) {
        String key = ((String)entry.getKey()).trim();
        String value = ((String)entry.getValue()).trim();
        sb.append(key).append("=").append(value).append(";");
      }
      str = sb.toString();
    }
    return str;
  }

  private LocalTxDataSourceDO createDataSourceDO(DataSourceParameter parameter, DBType dbType, String dsName)
    throws Exception
  {
    LocalTxDataSourceDO dsDo = new LocalTxDataSourceDO();
    dsDo.setDsName(dsName);
    dsDo.setConnectionURL(parameter.getJdbcUrl());
    dsDo.setUserName(parameter.getUserName());

    dsDo.setPassWord(parameter.getPassword());
    dsDo.setMinPoolSize(parameter.getMinConn());
    dsDo.setMaxPoolSize(parameter.getMaxConn());
    dsDo.setDriverClass(parameter.getDriverClass());
    dsDo.setBlockingTimeoutMillis(parameter.getBlockingTimeoutMillis());
    dsDo.setIdleTimeoutMinutes(parameter.getIdleTimeoutMinutes());
    dsDo.setPreparedStatementCacheSize(parameter.getPreparedStatementCacheSize());
    dsDo.setQueryTimeout(parameter.getQueryTimeout());
    dsDo.getConnectionProperties().putAll(parameter.getConnectionProperties());
    dsDo.setPrefill(parameter.getPrefill());
    if (dbType.isMysql())
      dsDo.setExceptionSorterClassName(MySQLExceptionSorter.class.getName());
    else if (dbType.isOracle())
      dsDo.setExceptionSorterClassName(OracleExceptionSorter.class.getName());
    else if (dbType.isDB2())
      dsDo.setExceptionSorterClassName(DB2ExceptionSorter.class.getName());
    else {
      throw new ZdalClientException("ERROR ## the DbType must be mysql/oracle/db2.");
    }
    dsDo.setConnectionProperties(parameter.getConnectionProperties());
    return dsDo;
  }

  public Connection getConnection()
    throws SQLException
  {
    if (!this.inited.get()) {
      throw new ZdalClientException("ERROR ## the ZdalDataSource has not init");
    }
    ZdalConnection connection = new ZdalConnection();
    buildTconnection(connection);
    return connection;
  }

  public Connection getConnection(String username, String password)
    throws SQLException
  {
    if (!this.inited.get()) {
      throw new ZdalClientException("ERROR ## the ZdalDataSource has not init");
    }
    ZdalConnection connection = new ZdalConnection(username, password);
    buildTconnection(connection);
    return connection;
  }

  private void buildTconnection(ZdalConnection connection)
  {
    ZdalRuntime rt = (ZdalRuntime)this.runtimeConfigHolder.get();
    connection.setDataSourcePool(rt == null ? null : rt.dbSelectors);
    connection.setWriteDispatcher(this.writeDispatcher);
    connection.setReadDispatcher(this.readDispatcher);
    connection.setRetryingTimes(this.retryingTimes);
    connection.setDbConfigType(this.dbConfigType);
    connection.setAppDsName(this.appDsName);
  }

  public int getLoginTimeout()
    throws SQLException
  {
    throw new UnsupportedOperationException("getLoginTimeout");
  }

  public void setLoginTimeout(int seconds)
    throws SQLException
  {
    throw new UnsupportedOperationException("setLoginTimeout");
  }

  public PrintWriter getLogWriter()
    throws SQLException
  {
    throw new UnsupportedOperationException("getLogWriter");
  }

  public void setLogWriter(PrintWriter out)
    throws SQLException
  {
    throw new UnsupportedOperationException("setLogWriter");
  }

  public boolean isWrapperFor(Class<?> iface)
    throws SQLException
  {
    throw new UnsupportedOperationException("isWrapperFor");
  }

  public <T> T unwrap(Class<T> iface)
    throws SQLException
  {
    throw new UnsupportedOperationException("unwrap");
  }

  public Map<String, ZdalDataSourceKeyWeightRandom> getKeyWeightMapConfig() {
    return this.keyWeightMapConfig;
  }

  public RuntimeConfigHolder<ZdalRuntime> getRuntimeConfigHolder() {
    return this.runtimeConfigHolder;
  }

  public SqlDispatcher getWriteDispatcher() {
    return this.writeDispatcher;
  }

  public SqlDispatcher getReadDispatcher() {
    return this.readDispatcher;
  }

  public AppRule getAppRule() {
    return this.appRule;
  }

  public Map<String, String> getKeyWeightConfig() {
    return this.keyWeightConfig;
  }

  public Map<String, ? extends Object> getRwDataSourcePoolConfig() {
    return this.rwDataSourcePoolConfig;
  }

  public Map<String, ZDataSource> getDataSourcesMap() {
    return this.dataSourcesMap;
  }

  public ZdalSignalResource getZdalSignalResource() {
    return this.zdalSignalResource;
  }

  public int getRetryingTimes() {
    return this.retryingTimes;
  }

  public void setRetryingTimes(int retryingTimes) {
    this.retryingTimes = retryingTimes;
  }

  public Map<String, ? extends Object> getDataSourcePoolConfig() {
    return this.dataSourcePoolConfig;
  }

  private static enum WeightRWPQEnum
  {
    rWeight(Integer.valueOf(0)), wWeight(Integer.valueOf(1)), readPriority(Integer.valueOf(2)), writePriority(Integer.valueOf(3));

    private Integer value;

    public Integer value() { return this.value; }

    private WeightRWPQEnum(Integer value)
    {
      this.value = value;
    }
  }
}