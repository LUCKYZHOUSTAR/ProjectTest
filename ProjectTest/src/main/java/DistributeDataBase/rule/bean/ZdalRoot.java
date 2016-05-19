package DistributeDataBase.rule.bean;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import DistributeDataBase.common.DBType;
import DistributeDataBase.rule.LogicTableRule;

public class ZdalRoot
{
  private static final Logger log = Logger.getLogger("zdal-client-config");

  private DBType dbType = DBType.MYSQL;
  private Map<String, LogicTable> logicTableMap;
  private String defaultDBSelectorID;

  public Map<String, LogicTable> getLogicTableMap()
  {
    return Collections.unmodifiableMap(this.logicTableMap);
  }

  public LogicTableRule getLogicTableMap(String logicTableName) {
    LogicTableRule logicTableRule = getLogicTable(logicTableName);
    if (logicTableRule == null)
    {
      if ((this.defaultDBSelectorID != null) && (this.defaultDBSelectorID.length() != 0))
      {
        DefaultLogicTableRule defaultLogicTableRule = new DefaultLogicTableRule(this.defaultDBSelectorID, logicTableName);

        logicTableRule = defaultLogicTableRule;
      } else {
        throw new IllegalArgumentException("未能找到对应规则,逻辑表:" + logicTableName);
      }
    }
    return logicTableRule;
  }

  public LogicTable getLogicTable(String logicTableName) {
    if (logicTableName == null) {
      throw new IllegalArgumentException("logic table name is null");
    }
    LogicTable logicTable = (LogicTable)this.logicTableMap.get(logicTableName);
    return logicTable;
  }

  public void setLogicTableMap(Map<String, LogicTable> logicTableMap)
  {
    this.logicTableMap = new HashMap(logicTableMap.size());
    for (Map.Entry entry : logicTableMap.entrySet()) {
      String key = (String)entry.getKey();
      if (key != null) {
        key = key.toLowerCase();
      }
      this.logicTableMap.put(key, entry.getValue());
    }
  }

  public void init(String appDsName)
  {
    for (Map.Entry logicTableEntry : this.logicTableMap.entrySet()) {
      log.warn("WARN ## logic Table is starting :" + appDsName + "." + (String)logicTableEntry.getKey());

      LogicTable logicTable = (LogicTable)logicTableEntry.getValue();
      String logicTableName = logicTable.getLogicTableName();
      if ((logicTableName == null) || (logicTableName.length() == 0))
      {
        logicTable.setLogicTableName((String)logicTableEntry.getKey());
      }
      logicTable.setDBType(this.dbType);
      logicTable.init();
      log.warn("WARN ## logic Table inited :" + logicTable.toString());
    }
  }

  public DBType getDBType() {
    return this.dbType;
  }

  public void setDBType(DBType dbType) {
    this.dbType = dbType;
  }

  public String getDefaultDBSelectorID() {
    return this.defaultDBSelectorID;
  }

  public void setDefaultDBSelectorID(String defaultDBSelectorID) {
    this.defaultDBSelectorID = defaultDBSelectorID;
  }
}