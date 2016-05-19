package DistributeDataBase.dataSource.exception;

public class DBNotAvailableException extends BaseException
{
  private static final long serialVersionUID = -6117676801548326638L;

  public DBNotAvailableException(String msg)
  {
    super(msg);
  }

  public DBNotAvailableException(String msg, Throwable nested)
  {
    super(msg, nested);
  }

  public DBNotAvailableException(Throwable nested)
  {
    this(nested.getMessage(), nested);
  }
}