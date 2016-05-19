package DistributeDataBase.client.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import DistributeDataBase.common.DBType;
import DistributeDataBase.common.exception.checked.ZdalCheckedExcption;
import DistributeDataBase.common.sqljep.function.Comparative;

public abstract interface RuleController
{
  public abstract TargetDBMeta getDBAndTables(String paramString, List<Object> paramList)
    throws ZdalCheckedExcption;

  public abstract TargetDBMeta getDBAndTables(String paramString, List<Object> paramList, boolean paramBoolean)
    throws ZdalCheckedExcption;

  public abstract DBType getDBType();

  public abstract TargetDBMeta getTargetDB(String paramString, Map<String, Comparative> paramMap, SqlType paramSqlType)
    throws ZdalCheckedExcption;

  public abstract TargetDBMeta getTargetDB(String paramString1, String paramString2, Set<String> paramSet, SqlType paramSqlType)
    throws ZdalCheckedExcption;

  public abstract String getGeneratorUrl();

  public abstract void setGeneratorUrl(String paramString);

  public abstract String getRuleUrl();

  public abstract void setRuleUrl(String paramString);
}