package DistributeDataBase.client.controller;

import java.util.ArrayList;
import java.util.List;

import DistributeDataBase.rule.ruleengine.entities.retvalue.TargetDB;

public class DispatcherResultImp
  implements DispatcherResult
{
  private final int max;
  private final int skip;
  private final OrderByMessages orderByMessages;
  private final GroupFunctionType groupFunctionType;
  private ColumnMetaData uniqueKey;
  private final List<ColumnMetaData> splitDB = new ArrayList();

  private final List<ColumnMetaData> splitTab = new ArrayList();
  private EXECUTE_PLAN databaseExecutePlan;
  private EXECUTE_PLAN tableExecutePlan;
  private boolean allowReverseOutput;
  private final List<TargetDB> target;
  private final String virtualTableName;
  List<String> virtualJoinTableNames = new ArrayList();

  public DispatcherResultImp(String virtualTableName, List<TargetDB> targetdbs, boolean allowReverseOutput, int skip, int max, OrderByMessages orderByMessages, GroupFunctionType groupFunctionType)
  {
    this.skip = skip;
    this.max = max;

    this.orderByMessages = orderByMessages;
    this.groupFunctionType = groupFunctionType;

    this.virtualTableName = virtualTableName;
    this.target = targetdbs;
    this.allowReverseOutput = allowReverseOutput;
  }

  public List<TargetDB> getTarget()
  {
    return this.target;
  }

  public int getMax() {
    return this.max;
  }

  public int getSkip() {
    return this.skip;
  }

  public OrderByMessages getOrderByMessages() {
    return this.orderByMessages;
  }

  public String getVirtualTableName() {
    return this.virtualTableName;
  }

  public ColumnMetaData getPrimaryKey() {
    return this.uniqueKey;
  }

  public void setUniqueKey(ColumnMetaData uniqueKey) {
    this.uniqueKey = uniqueKey;
  }

  public List<ColumnMetaData> getSplitDB() {
    return this.splitDB;
  }

  public void addSplitDB(ColumnMetaData splitDB) {
    this.splitDB.add(splitDB);
  }

  public void addSplitTab(ColumnMetaData splitTab) {
    this.splitTab.add(splitTab);
  }

  public boolean allowReverseOutput() {
    return this.allowReverseOutput;
  }

  public void needAllowReverseOutput(boolean reverse) {
    this.allowReverseOutput = reverse;
  }

  public GroupFunctionType getGroupFunctionType() {
    return this.groupFunctionType;
  }

  public List<ColumnMetaData> getSplitTab() {
    return this.splitTab;
  }

  public EXECUTE_PLAN getDatabaseExecutePlan() {
    return this.databaseExecutePlan;
  }

  public void setDatabaseExecutePlan(EXECUTE_PLAN databaseExecutePlan) {
    this.databaseExecutePlan = databaseExecutePlan;
  }

  public EXECUTE_PLAN getTableExecutePlan() {
    return this.tableExecutePlan;
  }

  public void setTableExecutePlan(EXECUTE_PLAN executePlan) {
    this.tableExecutePlan = executePlan;
  }

  public List<String> getVirtualJoinTableNames()
  {
    return this.virtualJoinTableNames;
  }

  public void setVirtualJoinTableNames(List<String> virtualJoinTableNames) {
    this.virtualJoinTableNames.addAll(virtualJoinTableNames);
  }
}