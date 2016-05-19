package DistributeDataBase.client.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import DistributeDataBase.rule.ruleengine.entities.retvalue.TargetDB;

public class ControllerUtils
{
  public static String toLowerCaseIgnoreNull(String tobeDone)
  {
    if (tobeDone != null) {
      return tobeDone.toLowerCase();
    }
    return null;
  }

  public static void buildExecutePlan(DispatcherResult dispatcherResult, List<TargetDB> targetDBList)
  {
    if (targetDBList == null) {
      throw new IllegalArgumentException("targetDBList is null");
    }
    int size = targetDBList.size();
    switch (size) {
    case 0:
      dispatcherResult.setDatabaseExecutePlan(EXECUTE_PLAN.NONE);
      dispatcherResult.setTableExecutePlan(EXECUTE_PLAN.NONE);
      break;
    case 1:
      TargetDB targetDB = (TargetDB)targetDBList.get(0);
      Set set = targetDB.getTableNames();
      dispatcherResult.setTableExecutePlan(buildTableExecutePlan(set, null));

      if (dispatcherResult.getTableExecutePlan() != EXECUTE_PLAN.NONE)
        dispatcherResult.setDatabaseExecutePlan(EXECUTE_PLAN.SINGLE);
      else {
        dispatcherResult.setDatabaseExecutePlan(EXECUTE_PLAN.NONE);
      }
      break;
    default:
      EXECUTE_PLAN currentExeutePlan = EXECUTE_PLAN.NONE;
      for (TargetDB oneDB : targetDBList) {
        currentExeutePlan = buildTableExecutePlan(oneDB.getTableNames(), currentExeutePlan);
      }

      dispatcherResult.setTableExecutePlan(currentExeutePlan);
      if (dispatcherResult.getTableExecutePlan() != EXECUTE_PLAN.NONE)
        dispatcherResult.setDatabaseExecutePlan(EXECUTE_PLAN.MULTIPLE);
      else
        dispatcherResult.setDatabaseExecutePlan(EXECUTE_PLAN.NONE);
      break;
    }
  }

  private static EXECUTE_PLAN buildTableExecutePlan(Set<String> tableSet, EXECUTE_PLAN currentExecutePlan)
  {
    if (currentExecutePlan == null) {
      currentExecutePlan = EXECUTE_PLAN.NONE;
    }
    EXECUTE_PLAN tempExecutePlan = null;
    if (tableSet == null) {
      throw new IllegalStateException("targetTab is null");
    }
    int tableSize = tableSet.size();

    switch (tableSize) {
    case 0:
      tempExecutePlan = EXECUTE_PLAN.NONE;
      break;
    case 1:
      tempExecutePlan = EXECUTE_PLAN.SINGLE;
      break;
    default:
      tempExecutePlan = EXECUTE_PLAN.MULTIPLE;
    }
    return tempExecutePlan.value() > currentExecutePlan.value() ? tempExecutePlan : currentExecutePlan;
  }

  public static void buildReverseOutput(List<Object> args, SqlParserResult dmlc, int max, int skip, DispatcherResult retMeta, boolean isMySQL)
  {
    boolean allowReverseOutput = retMeta.allowReverseOutput();
    List targetdbs = retMeta.getTarget();
    for (TargetDB targetDB : targetdbs) {
      Set tabs = targetDB.getTableNames();
      Map modifiedMap = new HashMap();

      if (targetdbs.size() == 1) {
        Set temp_tabs = ((TargetDB)targetdbs.get(0)).getTableNames();
        if (temp_tabs.size() == 1) {
          if (allowReverseOutput)
          {
            dmlc.getSqlReadyToRun(temp_tabs, args, Integer.valueOf(skip), Integer.valueOf(max), modifiedMap);
          }
        }
        else mutiTableReverseOutput(args, dmlc, max, skip, retMeta, allowReverseOutput, temp_tabs, modifiedMap);
      }
      else
      {
        mutiTableReverseOutput(args, dmlc, max, skip, retMeta, allowReverseOutput, tabs, modifiedMap);
      }

      if (retMeta.allowReverseOutput())
        targetDB.setChangedParams(modifiedMap);
    }
  }

  private static void mutiTableReverseOutput(List<Object> args, SqlParserResult dmlc, int max, int skip, DispatcherResult retMeta, boolean allowReverseOutput, Set<String> tabs, Map<Integer, Object> modifiedMap)
  {
    if (allowReverseOutput)
    {
      dmlc.getSqlReadyToRun(tabs, args, Integer.valueOf(0), Integer.valueOf(max), modifiedMap);
    }
    else if ((skip != -1000) && (max != -1000))
    {
      dmlc.getSqlReadyToRun(tabs, args, Integer.valueOf(0), Integer.valueOf(max), modifiedMap);
      retMeta.needAllowReverseOutput(true);
    }
  }
}