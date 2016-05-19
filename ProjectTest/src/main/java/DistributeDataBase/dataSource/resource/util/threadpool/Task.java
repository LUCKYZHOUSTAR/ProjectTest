package DistributeDataBase.dataSource.resource.util.threadpool;

public abstract interface Task
{
  public static final int WAIT_NONE = 0;
  public static final int WAIT_FOR_START = 1;
  public static final int WAIT_FOR_COMPLETE = 2;

  public abstract int getWaitType();

  public abstract int getPriority();

  public abstract long getStartTimeout();

  public abstract long getCompletionTimeout();

  public abstract void execute();

  public abstract void stop();

  public abstract void accepted(long paramLong);

  public abstract void rejected(long paramLong, Throwable paramThrowable);

  public abstract void started(long paramLong);

  public abstract void completed(long paramLong, Throwable paramThrowable);
}