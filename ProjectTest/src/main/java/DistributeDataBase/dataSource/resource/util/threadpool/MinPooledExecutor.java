package DistributeDataBase.dataSource.resource.util.threadpool;

import com.alipay.zdal.datasource.resource.util.concurrent.Channel;
import com.alipay.zdal.datasource.resource.util.concurrent.PooledExecutor;

public class MinPooledExecutor extends PooledExecutor
{
  protected int keepAliveSize;

  public MinPooledExecutor(int poolSize)
  {
    super(poolSize);
  }

  public MinPooledExecutor(Channel channel, int poolSize)
  {
    super(channel, poolSize);
  }

  public int getKeepAliveSize()
  {
    return this.keepAliveSize;
  }

  public void setKeepAliveSize(int keepAliveSize)
  {
    this.keepAliveSize = keepAliveSize;
  }

  protected Runnable getTask()
    throws InterruptedException
  {
    Runnable task = super.getTask();
    while ((task == null) && (keepAlive())) {
      task = super.getTask();
    }
    return task;
  }

  protected synchronized boolean keepAlive()
  {
    if (this.shutdown_) {
      return false;
    }
    return this.poolSize_ <= this.keepAliveSize;
  }
}