package DistributeDataBase.dataSource.resource.util.threadpool;

public abstract interface ThreadPoolMBean
{
  public abstract String getName();

  public abstract void setName(String paramString);

  public abstract int getPoolNumber();

  public abstract int getMinimumPoolSize();

  public abstract void setMinimumPoolSize(int paramInt);

  public abstract int getMaximumPoolSize();

  public abstract void setMaximumPoolSize(int paramInt);

  public abstract ThreadPool getInstance();

  public abstract void stop();
}