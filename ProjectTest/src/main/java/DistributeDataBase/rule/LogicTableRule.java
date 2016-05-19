package DistributeDataBase.rule;

import java.util.List;
import java.util.Map;
import java.util.Set;

import DistributeDataBase.rule.ruleengine.entities.abstractentities.RuleChain;
import DistributeDataBase.rule.ruleengine.entities.inputvalue.CalculationContextInternal;
import DistributeDataBase.rule.ruleengine.entities.retvalue.TargetDB;

public abstract interface LogicTableRule
{
  public abstract Set<RuleChain> getRuleChainSet();

  public abstract boolean isAllowReverseOutput();

  public abstract List<TargetDB> calculate(Map<RuleChain, CalculationContextInternal> paramMap);

  public abstract List<String> getUniqueColumns();
}