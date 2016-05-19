package DistributeDataBase.dataSource.resource.util;

public class UnreachableStatementException extends RuntimeException
{
  public UnreachableStatementException(String msg)
  {
    super(msg);
  }

  public UnreachableStatementException()
  {
  }
}