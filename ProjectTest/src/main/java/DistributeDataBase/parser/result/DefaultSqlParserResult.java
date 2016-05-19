package DistributeDataBase.parser.result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.aspectj.weaver.patterns.ParserException;

import DistributeDataBase.common.sqljep.function.Comparative;
import DistributeDataBase.common.sqljep.function.ComparativeAND;
import DistributeDataBase.common.sqljep.function.ComparativeOR;
import DistributeDataBase.parser.GroupFunctionType;

public abstract class DefaultSqlParserResult
  implements SqlParserResult, ComparativeMapChoicer
{
  protected ZdalSchemaStatVisitor visitor;
  public static final int DEFAULT_SKIP_MAX = -1000;
  protected String tableName = null;

  public DefaultSqlParserResult(ZdalSchemaStatVisitor visitor) {
    if (visitor == null) {
      throw new SqlParserException("ERROR the visitor is null ");
    }
    this.visitor = visitor;
  }

  public List<OrderByEle> getGroupByEles() {
    Set columns = this.visitor.getGroupByColumns();
    List results = Collections.emptyList();
    for (TableStat.Column column : columns) {
      OrderByEle orderByEle = new OrderByEle(column.getTable(), column.getName());
      orderByEle.setAttributes(column.getAttributes());
      results.add(orderByEle);
    }
    return results;
  }

  public GroupFunctionType getGroupFuncType() {
    if (TableStat.SELECTMODE.COUNT == this.visitor.getSelectMode())
      return GroupFunctionType.COUNT;
    if (TableStat.SELECTMODE.MAX == this.visitor.getSelectMode())
      return GroupFunctionType.MAX;
    if (TableStat.SELECTMODE.MIN == this.visitor.getSelectMode())
      return GroupFunctionType.MIN;
    if (TableStat.SELECTMODE.SUM == this.visitor.getSelectMode()) {
      return GroupFunctionType.SUM;
    }
    return GroupFunctionType.NORMAL;
  }

  public boolean isDML() {
    return (this.visitor.getSqlMode() == TableStat.Mode.Delete) || (this.visitor.getSqlMode() == TableStat.Mode.Insert) || (this.visitor.getSqlMode() == TableStat.Mode.Select) || (this.visitor.getSqlMode() == TableStat.Mode.Update);
  }

  public List<OrderByEle> getOrderByEles()
  {
    List columns = this.visitor.getOrderByColumns();
    List results = new ArrayList();
    for (TableStat.Column column : columns) {
      OrderByEle orderByEle = new OrderByEle(column.getTable(), column.getName());
      orderByEle.setAttributes(column.getAttributes());
      results.add(orderByEle);
    }
    return results;
  }

  public String getTableName()
  {
    if ((this.visitor.getTables() == null) || (this.visitor.getTables().isEmpty())) {
      throw new SqlParserException("ERROR ## the tableName is null");
    }
    if (StringUtils.isBlank(this.tableName)) {
      for (Map.Entry entry : this.visitor.getTables().entrySet()) {
        String temp = ((TableStat.Name)entry.getKey()).getName();
        if (this.tableName == null) {
          if (temp != null) {
            this.tableName = temp.toLowerCase();
          }
        }
        else if ((temp != null) && (!this.tableName.equals(((TableStat.Name)entry.getKey()).getName().toLowerCase()))) {
          throw new IllegalArgumentException("sql语句中的表名不同，请保证所有sql语句的表名以及他们的schemaName相同，包括内嵌sql");
        }
      }

    }

    return this.tableName;
  }

  public Map<String, Comparative> getColumnsMap(List<Object> arguments, Set<String> partnationSet)
  {
    Map copiedMap = new LinkedHashMap();
    for (String partnation : partnationSet) {
      Comparative comparative = getComparativeOf(partnation, arguments);
      if (comparative != null) {
        copiedMap.put(partnation, comparative);
      }
    }
    return copiedMap;
  }

  private Comparative getComparativeOf(String partinationKey, List<Object> arguments)
  {
    List bindColumns = this.visitor.getBindVarConditions();

    List conditions = new ArrayList();
    for (BindVarCondition tmp : bindColumns) {
      if (tmp.getColumnName().equalsIgnoreCase(partinationKey)) {
        conditions.add(tmp);
      }
    }
    if (!conditions.isEmpty()) {
      Comparative comparative = null;
      int index = 1;
      for (BindVarCondition bindVarCondition : conditions) {
        String op = bindVarCondition.getOperator();
        int function = Comparative.getComparisonByIdent(op);
        if ((function == -1) || (op.trim().equalsIgnoreCase("in"))) {
          Object arg = arguments.get(bindVarCondition.getIndex());
          Comparable value = null;
          if ((arg instanceof Comparable))
            value = (Comparable)arg;
          else {
            throw new ParserException("ERROR ## can not use this type of partination");
          }

          if (comparative == null) {
            comparative = new Comparative(3, value);
            if (index == conditions.size())
              return comparative;
          }
          else {
            Comparative next = new Comparative(3, value);
            ComparativeOR comparativeOR = new ComparativeOR();
            comparativeOR.addComparative(comparative);
            comparativeOR.addComparative(next);
            comparative = comparativeOR;
            if (index == conditions.size()) {
              return comparativeOR;
            }
          }
          index++;
        }
      }

      index = -1;
      for (BindVarCondition condition : conditions) {
        Object arg = arguments.get(condition.getIndex());
        Comparable value = null;
        if ((arg instanceof Comparable))
          value = (Comparable)arg;
        else {
          throw new ParserException("ERROR ## can not use this type of partination");
        }
        int function = Comparative.getComparisonByIdent(condition.getOperator());

        if (comparative == null) {
          comparative = new Comparative(function, value);
          index = condition.getIndex();
        } else {
          Comparative next = new Comparative(function, value);
          if (index == condition.getIndex()) {
            return comparative;
          }
          if (condition.getOp() == 1) {
            ComparativeAND comparativeAND = new ComparativeAND();
            comparativeAND.addComparative(comparative);
            comparativeAND.addComparative(next);
            return comparativeAND;
          }if (condition.getOp() == -1) {
            ComparativeOR comparativeOR = new ComparativeOR();
            comparativeOR.addComparative(comparative);
            comparativeOR.addComparative(next);
            return comparativeOR;
          }
        }
      }
      return comparative;
    }
    List noBindConditions = this.visitor.getNoBindVarConditions();

    if (noBindConditions.isEmpty()) {
      return null;
    }
    List noBinditions = new ArrayList();
    for (BindVarCondition tmp : noBindConditions) {
      if (tmp.getColumnName().equalsIgnoreCase(partinationKey)) {
        int function = Comparative.getComparisonByIdent(tmp.getOperator());
        if (function == -1) {
          if (tmp.getOperator().trim().equalsIgnoreCase("in")) {
            noBinditions.add(tmp);
          }
        }
        else
        {
          noBinditions.add(tmp);
        }
      }
    }
    Comparative comparative = null;
    for (BindVarCondition condition : noBinditions) {
      Comparable value = condition.getValue();
      if (value == null) {
        throw new SqlParserException(new StringBuilder().append("ERROR ## parse from no-bind-column of this partination is error,the partination name = ").append(partinationKey).toString());
      }

      if (!(value instanceof Comparable)) {
        throw new ParserException("ERROR ## can not use this type of partination");
      }
      if (condition.getOperator().trim().equalsIgnoreCase("in")) {
        if (comparative == null) {
          comparative = new Comparative(3, value);
        } else {
          Comparative next = new Comparative(3, value);
          ComparativeOR comparativeOR = new ComparativeOR();
          comparativeOR.addComparative(comparative);
          comparativeOR.addComparative(next);
          comparative = comparativeOR;
        }
      } else {
        int function = Comparative.getComparisonByIdent(condition.getOperator());

        if (comparative == null) {
          comparative = new Comparative(function, value);
        } else {
          Comparative next = new Comparative(function, value);
          if (condition.getOp() == 1) {
            ComparativeAND comparativeAND = new ComparativeAND();
            comparativeAND.addComparative(comparative);
            comparativeAND.addComparative(next);
            return comparativeAND;
          }if (condition.getOp() == -1) {
            ComparativeOR comparativeOR = new ComparativeOR();
            comparativeOR.addComparative(comparative);
            comparativeOR.addComparative(next);
            return comparativeOR;
          }
        }
      }
    }
    return comparative;
  }

  public void getSqlReadyToRun(Set<String> tables, List<Object> args, Number skip, Number max, Map<Integer, Object> modifiedMap)
  {
    if (tables == null) {
      throw new IllegalArgumentException("待替换表名为空");
    }

    if ((isSkipBind() < 0) && (isRowCountBind() < 0)) {
      throw new IllegalArgumentException("The limit skip or rowCount set error!");
    }
    modifyParam(skip, isSkipBind(), modifiedMap);
    modifyParam(max, isRowCountBind(), modifiedMap);
  }

  protected void modifyParam(Number num, int index, Map<Integer, Object> changeParam)
  {
    Object obj = null;
    if ((num instanceof Long))
      obj = (Long)num;
    else if ((num instanceof Integer))
      obj = (Integer)num;
    else {
      throw new IllegalArgumentException("只支持int long的情况");
    }
    changeParam.put(Integer.valueOf(index), obj);
  }

  protected String toColumns(Set<TableStat.Column> columns) {
    StringBuilder result = new StringBuilder();
    int i = 0;
    for (TableStat.Column column : columns) {
      result.append(column);
      if (i != columns.size() - 1) {
        result.append(",");
      }
    }
    return result.toString();
  }

  public ComparativeMapChoicer getComparativeMapChoicer() {
    return this;
  }
}