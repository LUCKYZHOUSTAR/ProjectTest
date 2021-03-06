package DistributeDataBase.client.controller;

import java.util.List;
import java.util.Map;

import DistributeDataBase.common.sqljep.function.Comparative;
import DistributeDataBase.rule.ruleengine.entities.retvalue.TargetDB;

public class MatcherResultImp
  implements MatcherResult
{
  List<TargetDB> calculationResult;
  Map<String, Comparative> databaseComparativeMap;
  Map<String, Comparative> tableComparativeMap;

  public void setCalculationResult(List<TargetDB> calculationResult)
  {
    this.calculationResult = calculationResult;
  }

  public void setDatabaseComparativeMap(Map<String, Comparative> databaseComparativeMap) {
    this.databaseComparativeMap = databaseComparativeMap;
  }

  public void setTableComparativeMap(Map<String, Comparative> tableComparativeMap) {
    this.tableComparativeMap = tableComparativeMap;
  }

  public List<TargetDB> getCalculationResult() {
    return this.calculationResult;
  }

  public Map<String, Comparative> getDatabaseComparativeMap() {
    return this.databaseComparativeMap;
  }

  public Map<String, Comparative> getTableComparativeMap() {
    return this.tableComparativeMap;
  }
}