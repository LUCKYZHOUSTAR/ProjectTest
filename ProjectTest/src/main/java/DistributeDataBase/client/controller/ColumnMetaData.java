package DistributeDataBase.client.controller;

import DistributeDataBase.common.sqljep.function.Comparative;

public class ColumnMetaData
{
  public final String key;
  public final Comparative value;

  public ColumnMetaData(String key, Comparative value)
  {
    this.key = key;
    this.value = value;
  }
}