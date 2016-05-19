package DistributeDataBase.parser.result;

import java.util.List;
import java.util.Map;
import java.util.Set;

import DistributeDataBase.parser.GroupFunctionType;

public abstract interface SqlParserResult
{
  public abstract String getTableName();

  public abstract List<OrderByEle> getOrderByEles();

  public abstract List<OrderByEle> getGroupByEles();

  public abstract boolean isDML();

  public abstract int getSkip(List<Object> paramList);

  public abstract int isSkipBind();

  public abstract int getMax(List<Object> paramList);

  public abstract int isRowCountBind();

  public abstract GroupFunctionType getGroupFuncType();

  public abstract void getSqlReadyToRun(Set<String> paramSet, List<Object> paramList, Number paramNumber1, Number paramNumber2, Map<Integer, Object> paramMap);

  public abstract ComparativeMapChoicer getComparativeMapChoicer();
}