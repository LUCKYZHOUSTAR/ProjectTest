package DistributeDataBase.dataSource.resource.util.threadpool;

public class ThreadPoolFullException extends RuntimeException
{
  public ThreadPoolFullException()
  {
  }

  public ThreadPoolFullException(String message)
  {
    super(message);
  }
}