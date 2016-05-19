package com.alipay.zdal.rule.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import DistributeDataBase.rule.ruleengine.entities.abstractentities.RuleChain;
import DistributeDataBase.rule.ruleengine.entities.inputvalue.CalculationContextInternal;
import DistributeDataBase.rule.ruleengine.entities.retvalue.TargetDB;

public class DefaultLogicTableRule
  implements LogicTableRule, Cloneable
{
  private final String defaultTable;
  private final String databases;

  public DefaultLogicTableRule(String databases, String defaultTable)
  {
    this.databases = databases;
    this.defaultTable = defaultTable;
  }

  public String getDatabases()
  {
    return this.databases;
  }

  public List<TargetDB> calculate(Map<RuleChain, CalculationContextInternal> map)
  {
    List targetDBs = new ArrayList(0);

    TargetDB targetDB = new TargetDB();
    targetDB.setDbIndex(this.databases);
    Set tableNames = new HashSet(1);
    tableNames.add(this.defaultTable);
    targetDB.setTableNames(tableNames);
    targetDBs.add(targetDB);
    return targetDBs;
  }

  public Set<RuleChain> getRuleChainSet() {
    return Collections.emptySet();
  }

  public List<String> getUniqueColumns() {
    return Collections.emptyList();
  }

  public boolean isAllowReverseOutput() {
    return false;
  }

  public boolean isNeedRowCopy() {
    return false;
  }

  public Object clone() throws CloneNotSupportedException
  {
    if (this.databases == null) {
      throw new IllegalArgumentException("databases == null || defaultTable == null");
    }
    return super.clone();
  }
}