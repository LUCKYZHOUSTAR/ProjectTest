package DistributeDataBase.client.controller;

import java.util.ArrayList;
import java.util.List;

import DistributeDataBase.rule.ruleengine.entities.retvalue.TargetDB;
import DistributeDataBase.rule.ruleengine.entities.retvalue.TargetDBMetaData;

public class TargetDBMeta
  implements DispatcherResult
{
  private final TargetDBMetaData dbMeta;
  private final int max;
  private final int skip;
  private final OrderByMessages orderByMessages;
  private final GroupFunctionType groupFunctionType;
  private ColumnMetaData primaryKey;
  private final List<ColumnMetaData> splitDB = new ArrayList();

  private final List<ColumnMetaData> splitTab = new ArrayList();

  private final List<String> virtualJoinTableNames = new ArrayList(0);

  public TargetDBMeta(TargetDBMetaData dbMeta, int skip, int max, OrderByMessages orderByMessages, GroupFunctionType groupFunctionType)
  {
    this.dbMeta = dbMeta;
    this.skip = skip;
    this.max = max;
    this.orderByMessages = orderByMessages;
    this.groupFunctionType = groupFunctionType;
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
    return this.dbMeta.getVirtualTableName();
  }

  public ColumnMetaData getPrimaryKey() {
    return this.primaryKey;
  }

  public void setPrimaryKey(ColumnMetaData primaryKey) {
    this.primaryKey = primaryKey;
  }

  public List<ColumnMetaData> getSplitDB() {
    return this.splitDB;
  }

  public void addSplitDB(ColumnMetaData splitDB) {
    this.splitDB.add(splitDB);
  }

  public List<ColumnMetaData> getSplitTab() {
    return this.splitTab;
  }

  public void addSplitTab(ColumnMetaData splitTab) {
    this.splitTab.add(splitTab);
  }

  public boolean allowReverseOutput() {
    return this.dbMeta.allowReverseOutput();
  }

  public void needAllowReverseOutput(boolean reverse) {
    this.dbMeta.needAllowReverseOutput(reverse);
  }

  public GroupFunctionType getGroupFunctionType() {
    return this.groupFunctionType;
  }

  public List<TargetDB> getTarget() {
    return this.dbMeta.getTarget();
  }

  public EXECUTE_PLAN getDatabaseExecutePlan() {
    throw new IllegalStateException("not support yet");
  }

  public EXECUTE_PLAN getTableExecutePlan() {
    throw new IllegalStateException("not support yet");
  }

  public void setDatabaseExecutePlan(EXECUTE_PLAN executePlan) {
    throw new IllegalStateException("not support yet");
  }

  public void setTableExecutePlan(EXECUTE_PLAN executePlan) {
    throw new IllegalStateException("not support yet");
  }

  public boolean mappingRuleReturnNullValue() {
    return false;
  }

  public List<String> getVirtualJoinTableNames() {
    return this.virtualJoinTableNames;
  }

  public void setVirtualJoinTableNames(List<String> virtualJoinTableNames) {
    this.virtualJoinTableNames.addAll(virtualJoinTableNames);
  }
}