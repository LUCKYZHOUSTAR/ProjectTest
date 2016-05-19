package com.alipay.zdal.client.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hamcrest.Matcher;

import DistributeDataBase.common.exception.checked.ZdalCheckedExcption;
import DistributeDataBase.common.sqljep.function.Comparative;
import DistributeDataBase.rule.LogicTableRule;
import DistributeDataBase.rule.ruleengine.entities.abstractentities.RuleChain;
import DistributeDataBase.rule.ruleengine.entities.inputvalue.CalculationContextInternal;
import DistributeDataBase.rule.ruleengine.entities.retvalue.TargetDB;

public class SpringBasedRuleMatcherImpl
  implements Matcher
{
  public MatcherResult match(ComparativeMapChoicer comparativeMapChoicer, List<Object> args, LogicTableRule rule)
    throws ZdalCheckedExcption
  {
    Set ruleChainSet = rule.getRuleChainSet();

    Map comparativeMapDatabase = new HashMap(2);

    Map comparativeTable = new HashMap(2);

    Map resultMap = new HashMap(ruleChainSet.size());

    for (Iterator i$ = ruleChainSet.iterator(); i$.hasNext(); ) { ruleChain = (RuleChain)i$.next();

      List requiredArgumentSortByLevel = ruleChain.getRequiredArgumentSortByLevel();

      index = 0;

      for (Set oneLevelArgument : requiredArgumentSortByLevel)
      {
        Map sqlArgs = comparativeMapChoicer.getColumnsMap(args, oneLevelArgument);

        if (sqlArgs.size() == oneLevelArgument.size())
        {
          resultMap.put(ruleChain, new CalculationContextInternal(ruleChain, index, sqlArgs));

          if (ruleChain.isDatabaseRuleChain()) {
            comparativeMapDatabase.putAll(sqlArgs); break;
          }

          comparativeTable.putAll(sqlArgs);

          break;
        }
        index++;
      }
    }
    RuleChain ruleChain;
    int index;
    List calc = rule.calculate(resultMap);
    return buildMatcherResult(comparativeMapDatabase, comparativeTable, calc);
  }

  public MatcherResult buildMatcherResult(Map<String, Comparative> comparativeMapDatabase, Map<String, Comparative> comparativeTable, List<TargetDB> targetDB)
  {
    MatcherResultImp result = new MatcherResultImp();
    result.setCalculationResult(targetDB);
    result.setDatabaseComparativeMap(comparativeMapDatabase);
    result.setTableComparativeMap(comparativeTable);
    return result;
  }
}