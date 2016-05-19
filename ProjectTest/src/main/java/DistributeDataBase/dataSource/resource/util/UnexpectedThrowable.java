package DistributeDataBase.dataSource.resource.util;

public class UnexpectedThrowable extends NestedError
{
  public UnexpectedThrowable(String msg)
  {
    super(msg);
  }

  public UnexpectedThrowable(String msg, Throwable nested)
  {
    super(msg, nested);
  }

  public UnexpectedThrowable(Throwable nested)
  {
    super(nested);
  }

  public UnexpectedThrowable()
  {
  }
}