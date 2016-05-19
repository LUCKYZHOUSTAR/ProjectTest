package DistributeDataBase.dataSource.resource.util.threadpool;

public abstract interface BasicThreadPoolMBean extends ThreadPoolMBean
{
  public abstract int getQueueSize();

  public abstract int getMaximumQueueSize();

  public abstract void setMaximumQueueSize(int paramInt);

  public abstract BlockingMode getBlockingMode();

  public abstract void setBlockingMode(BlockingMode paramBlockingMode);

  public abstract String getThreadGroupName();

  public abstract void setThreadGroupName(String paramString);

  public abstract long getKeepAliveTime();

  public abstract void setKeepAliveTime(long paramLong);
}