package DistributeDataBase.dataSource.resource.util.threadpool;

public abstract interface ThreadPool
{
  public abstract void stop(boolean paramBoolean);

  public abstract void waitForTasks()
    throws InterruptedException;

  public abstract void waitForTasks(long paramLong)
    throws InterruptedException;

  public abstract void runTaskWrapper(TaskWrapper paramTaskWrapper);

  public abstract void runTask(Task paramTask);

  public abstract void run(Runnable paramRunnable);

  public abstract void run(Runnable paramRunnable, long paramLong1, long paramLong2);
}