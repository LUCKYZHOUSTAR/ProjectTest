package DistributeDataBase.dataSource.resource.util.threadpool;

public class StartTimeoutException extends RuntimeException
{
  public StartTimeoutException()
  {
  }

  public StartTimeoutException(String message)
  {
    super(message);
  }
}