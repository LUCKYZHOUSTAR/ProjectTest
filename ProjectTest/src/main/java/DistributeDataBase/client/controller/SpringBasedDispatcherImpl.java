package DistributeDataBase.client.controller;

import com.alipay.zdal.client.RouteCondition;
import com.alipay.zdal.client.dispatcher.DispatcherResult;
import com.alipay.zdal.client.dispatcher.EXECUTE_PLAN;
import com.alipay.zdal.client.dispatcher.Matcher;
import com.alipay.zdal.client.dispatcher.Result;
import com.alipay.zdal.client.dispatcher.SqlDispatcher;
import com.alipay.zdal.client.dispatcher.impl.DatabaseAndTablesDispatcherResultImp;
import com.alipay.zdal.client.util.condition.DBSelectorRouteCondition;
import com.alipay.zdal.client.util.condition.DummySqlParcerResult;
import com.alipay.zdal.client.util.condition.JoinCondition;
import com.alipay.zdal.client.util.condition.RuleRouteCondition;
import com.alipay.zdal.common.DBType;
import com.alipay.zdal.common.exception.checked.ZdalCheckedExcption;
import com.alipay.zdal.common.sqljep.function.Comparative;
import com.alipay.zdal.parser.SQLParser;
import com.alipay.zdal.parser.output.OutputHandlerConsist;
import com.alipay.zdal.parser.result.SqlParserResult;
import com.alipay.zdal.parser.sqlobjecttree.ComparativeMapChoicer;
import com.alipay.zdal.rule.LogicTableRule;
import com.alipay.zdal.rule.bean.LogicTable;
import com.alipay.zdal.rule.bean.ZdalRoot;
import com.alipay.zdal.rule.ruleengine.entities.retvalue.TargetDB;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SpringBasedDispatcherImpl
  implements SqlDispatcher
{
  private SQLParser parser = null;
  ZdalRoot root;
  private final Matcher matcher = new SpringBasedRuleMatcherImpl();

  public static final OutputHandlerConsist consist = new OutputHandlerConsist();

  public DispatcherResult getDBAndTables(String sql, List<Object> args)
    throws ZdalCheckedExcption
  {
    DBType dbType = getDBType();
    SqlParserResult sqlParserResult = this.parser.parse(sql, dbType);

    String logicTableName = sqlParserResult.getTableName();
    int index = logicTableName.indexOf(".");
    if (index >= 0) {
      logicTableName = logicTableName.substring(index + 1);
    }

    LogicTableRule rule = this.root.getLogicTableMap(logicTableName);
    if (rule == null) {
      throw new IllegalArgumentException("未能找到对应规则,逻辑表:" + logicTableName);
    }

    boolean isAllowReverseOutput = rule.isAllowReverseOutput();
    MatcherResult matcherResult = this.matcher.match(sqlParserResult.getComparativeMapChoicer(), args, rule);

    return getDispatcherResult(matcherResult, sqlParserResult, args, dbType, rule.getUniqueColumns(), isAllowReverseOutput, true);
  }

  protected DispatcherResult getDispatcherResult(MatcherResult matcherResult, SqlParserResult sqlParserResult, List<Object> args, DBType dbType, List<String> uniqueColumnSet, boolean isAllowReverseOutput, boolean isSqlParser)
  {
    DispatcherResultImp dispatcherResult = getTargetDatabaseMetaDataBydatabaseGroups(matcherResult.getCalculationResult(), sqlParserResult, args, isAllowReverseOutput);

    ControllerUtils.buildExecutePlan(dispatcherResult, matcherResult.getCalculationResult());

    validGroupByFunction(sqlParserResult, dispatcherResult);

    if (isSqlParser)
    {
      ControllerUtils.buildReverseOutput(args, sqlParserResult, dispatcherResult.getMax(), dispatcherResult.getSkip(), dispatcherResult, DBType.MYSQL.equals(dbType));
    }

    return dispatcherResult;
  }

  protected void validGroupByFunction(SqlParserResult sqlParserResult, DispatcherResult dispatcherResult)
  {
    List groupByElement = sqlParserResult.getGroupByEles();
    if (groupByElement.size() != 0) {
      if (dispatcherResult.getDatabaseExecutePlan() == EXECUTE_PLAN.MULTIPLE) {
        throw new IllegalArgumentException("多库的情况下，不允许使用group by 函数");
      }
      if (dispatcherResult.getTableExecutePlan() == EXECUTE_PLAN.MULTIPLE)
        throw new IllegalArgumentException("多表的情况下，不允许使用group by函数");
    }
  }

  protected DispatcherResultImp getTargetDatabaseMetaDataBydatabaseGroups(List<TargetDB> targetDatabases, SqlParserResult sqlParserResult, List<Object> arguments, boolean isAllowReverseOutput)
  {
    DispatcherResultImp dispatcherResultImp = new DispatcherResultImp(sqlParserResult.getTableName(), targetDatabases, isAllowReverseOutput, sqlParserResult.getSkip(arguments), sqlParserResult.getMax(arguments), new OrderByMessagesImp(sqlParserResult.getOrderByEles()), sqlParserResult.getGroupFuncType());

    return dispatcherResultImp;
  }

  public DispatcherResult getDBAndTables(RouteCondition rc)
  {
    String logicTableName = rc.getVirtualTableName();
    List uniqueColumns = Collections.emptyList();
    SqlParserResult sqlParserResult = null;
    if ((rc instanceof RuleRouteCondition))
    {
      sqlParserResult = ((RuleRouteCondition)rc).getSqlParserResult();
      try {
        LogicTableRule rule = this.root.getLogicTableMap(logicTableName);
        uniqueColumns = rule.getUniqueColumns();
        MatcherResult matcherResult = this.matcher.match(sqlParserResult.getComparativeMapChoicer(), null, rule);

        DispatcherResult result = getDispatcherResult(matcherResult, sqlParserResult, Collections.emptyList(), null, rule.getUniqueColumns(), false, false);

        if ((rc instanceof JoinCondition)) {
          result.setVirtualJoinTableNames(((JoinCondition)rc).getVirtualJoinTableNames());
        }

        return result;
      } catch (ZdalCheckedExcption e) {
        throw new RuntimeException(e);
      }
    }
    if ((rc instanceof DBSelectorRouteCondition)) {
      DBSelectorRouteCondition dBSelectorRouteCondition = (DBSelectorRouteCondition)rc;
      List targetDBs = new ArrayList(1);
      TargetDB targetDB = new TargetDB();
      targetDB.setDbIndex(dBSelectorRouteCondition.getDBSelectorID());
      Set targetDBSet = new HashSet();
      targetDBSet.addAll(dBSelectorRouteCondition.getTableList());
      targetDB.setTableNames(targetDBSet);
      targetDBs.add(targetDB);
      ComparativeMapChoicer choicer = new ComparativeMapChoicer()
      {
        public Map<String, Comparative> getColumnsMap(List<Object> arguments, Set<String> partnationSet)
        {
          return Collections.emptyMap();
        }
      };
      sqlParserResult = new DummySqlParcerResult(choicer, logicTableName);
      MatcherResult matcherResult = this.matcher.buildMatcherResult(Collections.EMPTY_MAP, Collections.EMPTY_MAP, targetDBs);

      return getDispatcherResult(matcherResult, sqlParserResult, Collections.emptyList(), null, uniqueColumns, false, false);
    }

    throw new IllegalArgumentException("wrong RouteCondition type:" + rc.getClass().getName());
  }

  public Result getAllDatabasesAndTables(String logicTableName)
  {
    LogicTable logicTable = this.root.getLogicTable(logicTableName.toLowerCase());
    if (logicTable == null) {
      throw new IllegalArgumentException("逻辑表名未找到");
    }
    return new DatabaseAndTablesDispatcherResultImp(logicTable.getAllTargetDBList(), logicTableName);
  }

  public SQLParser getParser()
  {
    return this.parser;
  }

  public void setParser(SQLParser parser) {
    this.parser = parser;
  }

  public ZdalRoot getRoot() {
    return this.root;
  }

  public void setRoot(ZdalRoot root) {
    this.root = root;
  }

  public DBType getDBType() {
    return this.root.getDBType();
  }
}