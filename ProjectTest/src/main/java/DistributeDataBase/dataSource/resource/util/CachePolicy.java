package DistributeDataBase.dataSource.resource.util;

public abstract interface CachePolicy
{
  public abstract Object get(Object paramObject);

  public abstract Object peek(Object paramObject);

  public abstract void insert(Object paramObject1, Object paramObject2);

  public abstract void remove(Object paramObject);

  public abstract void flush();

  public abstract int size();

  public abstract void create()
    throws Exception;

  public abstract void start()
    throws Exception;

  public abstract void stop();

  public abstract void destroy();
}