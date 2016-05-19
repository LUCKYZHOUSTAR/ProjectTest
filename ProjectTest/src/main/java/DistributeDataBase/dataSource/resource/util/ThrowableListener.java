package DistributeDataBase.dataSource.resource.util;

import java.util.EventListener;

public abstract interface ThrowableListener extends EventListener
{
  public abstract void onThrowable(int paramInt, Throwable paramThrowable);
}