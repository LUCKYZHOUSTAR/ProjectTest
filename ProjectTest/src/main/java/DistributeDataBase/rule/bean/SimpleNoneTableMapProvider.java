package DistributeDataBase.rule.bean;

import java.util.HashMap;
import java.util.Map;

import DistributeDataBase.rule.ruleengine.entities.abstractentities.SharedElement;

public class SimpleNoneTableMapProvider extends SimpleTableMapProvider
{
  private String parentID;
  private String logicTable;

  public Map<String, SharedElement> getTablesMap()
  {
    Map result = new HashMap();
    Table table = new Table();
    table.setTableName(this.logicTable);
    result.put("0", table);
    return result;
  }

  public void setParentID(String parentID)
  {
    this.parentID = parentID;
  }

  public void setLogicTable(String logicTable)
  {
    this.logicTable = logicTable;
  }

  public String getParentID()
  {
    return this.parentID;
  }
}