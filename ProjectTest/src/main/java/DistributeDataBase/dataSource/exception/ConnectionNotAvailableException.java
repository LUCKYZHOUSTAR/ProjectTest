package DistributeDataBase.dataSource.exception;

public class ConnectionNotAvailableException extends BaseException
{
  private static final long serialVersionUID = -6117676801548326638L;

  public ConnectionNotAvailableException(String msg)
  {
    super(msg);
  }

  public ConnectionNotAvailableException(String msg, Throwable nested)
  {
    super(msg, nested);
  }

  public ConnectionNotAvailableException(Throwable nested)
  {
    this(nested.getMessage(), nested);
  }
}