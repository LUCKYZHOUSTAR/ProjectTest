package DistributeDataBase.parser;

import DistributeDataBase.common.DBType;

public abstract interface SQLParser
{
  public abstract SqlParserResult parse(String paramString, DBType paramDBType);
}