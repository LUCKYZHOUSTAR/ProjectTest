package DistributeDataBase.dataSource.resource.util.timeout;

import DistributeDataBase.dataSource.resource.util.ThrowableHandler;
import DistributeDataBase.dataSource.resource.util.threadpool.BasicThreadPool;
import DistributeDataBase.dataSource.resource.util.threadpool.BlockingMode;
import DistributeDataBase.dataSource.resource.util.threadpool.ThreadPool;

public class TimeoutFactory
{
  private static TimeoutFactory singleton;
  private static int timeoutFactoriesCount = 0;

  private static BasicThreadPool DEFAULT_TP = new BasicThreadPool("Timeouts");
  private boolean cancelled;
  private Thread workerThread;
  private ThreadPool threadPool;
  private TimeoutImpl freeList;
  private int size;
  private TimeoutImpl[] q;

  private static synchronized TimeoutFactory getSingleton()
  {
    if (singleton == null) {
      singleton = new TimeoutFactory(DEFAULT_TP);
    }
    return singleton;
  }

  public static Timeout createTimeout(long time, TimeoutTarget target)
  {
    return getSingleton().schedule(time, target);
  }

  public TimeoutFactory(ThreadPool threadPool)
  {
    this.threadPool = threadPool;
    this.q = new TimeoutImpl[16];
    this.freeList = null;
    this.size = 0;

    this.workerThread = new Thread("TimeoutFactory-" + timeoutFactoriesCount++)
    {
      public void run() {
        TimeoutFactory.this.doWork();
      }
    };
    this.workerThread.setDaemon(true);
    this.workerThread.start();
  }

  public TimeoutFactory()
  {
    this(DEFAULT_TP);
  }

  public Timeout schedule(long time, TimeoutTarget target)
  {
    if (this.cancelled == true)
      throw new IllegalStateException("TimeoutFactory has been cancelled");
    if (time < 0L)
      throw new IllegalArgumentException("Negative time");
    if (target == null) {
      throw new IllegalArgumentException("Null timeout target");
    }
    return newTimeout(time, target);
  }

  public Timeout schedule(long time, Runnable run)
  {
    return schedule(time, new TimeoutTargetImpl(run));
  }

  public void cancel()
  {
    this.cancelled = true;

    synchronized (this) {
      notify();
    }
  }

  public boolean isCancelled()
  {
    return this.cancelled;
  }

  private void swap(int a, int b)
  {
    TimeoutImpl temp = this.q[a];
    this.q[a] = this.q[b];
    this.q[a].index = a;
    this.q[b] = temp;
    this.q[b].index = b;
  }

  private boolean normalizeUp(int index)
  {
    if (index == 1)
      return false;
    boolean ret = false;
    long t = this.q[index].time;
    int p = index >> 1;
    while (this.q[p].time > t)
    {
      swap(p, index);
      ret = true;
      if (p == 1)
        break;
      index = p;
      p >>= 1;
    }
    return ret;
  }

  private TimeoutImpl removeNode(int index)
  {
    TimeoutImpl res = this.q[index];

    if (index == this.size) {
      this.size -= 1;
      this.q[index] = null;
      return res;
    }
    swap(index, this.size);
    this.size -= 1;

    this.q[res.index] = null;
    if (normalizeUp(index))
      return res;
    long t = this.q[index].time;
    int c = index << 1;
    while (c <= this.size)
    {
      TimeoutImpl l = this.q[c];

      if (c + 1 <= this.size)
      {
        TimeoutImpl r = this.q[(c + 1)];

        if (l.time <= r.time) {
          if (t <= l.time)
            break;
          swap(index, c);
          index = c;
        } else {
          if (t <= r.time)
            break;
          swap(index, c + 1);
          index = c + 1;
        }
      } else {
        if (t <= l.time)
          break;
        swap(index, c);
        index = c;
      }
      c = index << 1;
    }
    return res;
  }

  private synchronized Timeout newTimeout(long time, TimeoutTarget target)
  {
    if (++this.size == this.q.length) {
      TimeoutImpl[] newQ = new TimeoutImpl[2 * this.q.length];
      System.arraycopy(this.q, 0, newQ, 0, this.q.length);
      this.q = newQ;
    }
    TimeoutImpl timeout;
    if (this.freeList != null) {
      TimeoutImpl timeout = this.q[this.size] =  = this.freeList;
      this.freeList = timeout.nextFree;
      timeout.nextFree = null;
    }
    else
    {
      timeout = this.q[this.size] =  = new TimeoutImpl(null);
    }timeout.index = this.size;
    timeout.time = time;
    timeout.target = target;
    normalizeUp(this.size);
    if (timeout.index == 1) {
      notify();
    }
    return timeout;
  }

  private boolean dropTimeout(TimeoutImpl timeout)
  {
    synchronized (this) {
      if (timeout.index > 0)
      {
        removeNode(timeout.index);

        timeout.index = -1;
        timeout.nextFree = this.freeList;
        this.freeList = timeout;

        return true;
      }

      return false;
    }
  }

  private void doWork()
  {
    while (!this.cancelled) {
      TimeoutImpl work = null;

      synchronized (this) {
        if ((this.size == 0) && (!this.cancelled)) {
          try {
            wait();
          } catch (InterruptedException ex) {
          }
        } else {
          long now = System.currentTimeMillis();
          if ((this.q[1].time > now) && (!this.cancelled))
            try {
              wait(this.q[1].time - now);
            }
            catch (InterruptedException ex) {
            }
          if ((this.size > 0) && (this.q[1].time <= System.currentTimeMillis()) && (!this.cancelled)) {
            work = removeNode(1);
            work.index = -2;
          }
        }
      }

      if (work != null)
      {
        TimeoutWorker worker = new TimeoutWorker(work);
        try {
          this.threadPool.run(worker);
        }
        catch (Throwable t) {
          ThrowableHandler.add(1, t);
        }
        synchronized (work)
        {
          work.index = -1;
        }
      }

    }

    cleanup();
  }

  private void cleanup()
  {
    this.freeList = cleanupTimeoutImpl(this.freeList);

    for (int i = 1; i <= this.size; i++) {
      this.q[i] = cleanupTimeoutImpl(this.q[i]);
    }
    this.q = null;

    this.threadPool = null;
    this.workerThread = null;
  }

  private TimeoutImpl cleanupTimeoutImpl(TimeoutImpl timeout)
  {
    if (timeout != null) {
      timeout.target = null;
      timeout.nextFree = cleanupTimeoutImpl(timeout.nextFree);
    }
    return null;
  }

  static
  {
    DEFAULT_TP.setBlockingMode(BlockingMode.RUN);
  }

  private static class TimeoutTargetImpl
    implements TimeoutTarget
  {
    Runnable runnable;

    TimeoutTargetImpl(Runnable runnable)
    {
      this.runnable = runnable;
    }

    public void timedOut(Timeout ignored) {
      this.runnable.run();
    }
  }

  private static class TimeoutWorker
    implements Runnable
  {
    private final TimeoutFactory.TimeoutImpl work;

    TimeoutWorker(TimeoutFactory.TimeoutImpl work)
    {
      this.work = work;
    }

    public void run()
    {
      try
      {
        this.work.target.timedOut(this.work);
      }
      catch (Throwable t) {
        ThrowableHandler.add(1, t);
      }
      synchronized (this.work)
      {
        this.work.index = -1;
      }
    }
  }

  private class TimeoutImpl
    implements Timeout
  {
    static final int DONE = -1;
    static final int TIMEOUT = -2;
    int index;
    long time;
    TimeoutTarget target;
    TimeoutImpl nextFree;

    private TimeoutImpl()
    {
    }

    public boolean cancel()
    {
      return TimeoutFactory.this.dropTimeout(this);
    }
  }
}