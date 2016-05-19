package DistributeDataBase.dataSource.exception;

public class TimeOutException extends BaseException
{
  private static final long serialVersionUID = -6117676801548326638L;

  public TimeOutException(String msg)
  {
    super(msg);
  }

  public TimeOutException(String msg, Throwable nested)
  {
    super(msg, nested);
  }

  public TimeOutException(Throwable nested)
  {
    this(nested.getMessage(), nested);
  }
}