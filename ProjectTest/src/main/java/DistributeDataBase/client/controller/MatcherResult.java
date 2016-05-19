package DistributeDataBase.client.controller;

import java.util.List;
import java.util.Map;

import DistributeDataBase.common.sqljep.function.Comparative;
import DistributeDataBase.rule.ruleengine.entities.retvalue.TargetDB;

public abstract interface MatcherResult
{
  public abstract List<TargetDB> getCalculationResult();

  public abstract Map<String, Comparative> getDatabaseComparativeMap();

  public abstract Map<String, Comparative> getTableComparativeMap();
}