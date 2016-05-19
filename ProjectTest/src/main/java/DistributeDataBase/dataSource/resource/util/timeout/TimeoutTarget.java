package DistributeDataBase.dataSource.resource.util.timeout;

public abstract interface TimeoutTarget
{
  public abstract void timedOut(Timeout paramTimeout);
}