package DistributeDataBase.dataSource.resource.util.threadpool;

public class ThreadPoolStoppedException extends RuntimeException
{
  public ThreadPoolStoppedException()
  {
  }

  public ThreadPoolStoppedException(String message)
  {
    super(message);
  }
}