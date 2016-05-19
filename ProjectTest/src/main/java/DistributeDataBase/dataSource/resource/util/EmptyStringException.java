package DistributeDataBase.dataSource.resource.util;

public class EmptyStringException extends IllegalArgumentException
{
  public EmptyStringException(String msg)
  {
    super(msg);
  }

  public EmptyStringException()
  {
  }
}