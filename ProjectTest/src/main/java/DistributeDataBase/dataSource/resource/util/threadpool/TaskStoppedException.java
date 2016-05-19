package DistributeDataBase.dataSource.resource.util.threadpool;

public class TaskStoppedException extends RuntimeException
{
  public TaskStoppedException()
  {
  }

  public TaskStoppedException(String message)
  {
    super(message);
  }
}