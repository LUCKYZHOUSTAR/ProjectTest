package DistributeDataBase.dataSource.resource.util.threadpool;

public abstract interface TaskWrapper extends Runnable
{
  public abstract int getTaskWaitType();

  public abstract int getTaskPriority();

  public abstract long getTaskStartTimeout();

  public abstract long getTaskCompletionTimeout();

  public abstract void waitForTask();

  public abstract void stopTask();

  public abstract void acceptTask();

  public abstract void rejectTask(RuntimeException paramRuntimeException);

  public abstract boolean isComplete();
}