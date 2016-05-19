package DistributeDataBase.dataSource.resource.util.threadpool;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ThreadFactory;

import org.apache.log4j.Logger;

import DistributeDataBase.dataSource.resource.util.collection.WeakValueHashMap;

public class BasicThreadPool
  implements ThreadPool, BasicThreadPoolMBean
{
  private static final ThreadGroup JBOSS_THREAD_GROUP = new ThreadGroup("JBoss Pooled Threads");

  private static final Map threadGroups = Collections.synchronizedMap(new WeakValueHashMap());

  private static final SynchronizedInt lastPoolNumber = new SynchronizedInt(0);

  private static Logger log = Logger.getLogger(BasicThreadPool.class);
  private String name;
  private final int poolNumber;
  private BlockingMode blockingMode = BlockingMode.ABORT;
  private final MinPooledExecutor executor;
  private final BoundedLinkedQueue queue;
  private ThreadGroup threadGroup;
  private final SynchronizedInt lastThreadNumber = new SynchronizedInt(0);

  private final SynchronizedBoolean stopped = new SynchronizedBoolean(false);

  private final Heap tasksWithTimeouts = new Heap(13);
  private TimeoutMonitor timeoutTask;

  public BasicThreadPool()
  {
    this("ThreadPool");
  }

  public BasicThreadPool(String name)
  {
    this(name, JBOSS_THREAD_GROUP);
  }

  public BasicThreadPool(String name, ThreadGroup threadGroup)
  {
    ThreadFactory factory = new ThreadPoolThreadFactory(null);

    this.queue = new BoundedLinkedQueue(1024);

    this.executor = new MinPooledExecutor(this.queue, 100);
    this.executor.setMinimumPoolSize(100);
    this.executor.setKeepAliveTime(60000L);
    this.executor.setThreadFactory(factory);
    this.executor.abortWhenBlocked();

    this.poolNumber = lastPoolNumber.increment();
    setName(name);
    this.threadGroup = threadGroup;
  }

  public void stop(boolean immediate)
  {
    if (log.isDebugEnabled()) {
      log.debug("stop, immediate=" + immediate);
    }
    this.stopped.set(true);
    if (immediate)
      this.executor.shutdownNow();
    else
      this.executor.shutdownAfterProcessingCurrentlyQueuedTasks();
  }

  public void waitForTasks() throws InterruptedException
  {
    this.executor.awaitTerminationAfterShutdown();
  }

  public void waitForTasks(long maxWaitTime) throws InterruptedException {
    this.executor.awaitTerminationAfterShutdown(maxWaitTime);
  }

  public void runTaskWrapper(TaskWrapper wrapper) {
    if (log.isDebugEnabled()) {
      log.debug("runTaskWrapper, wrapper=" + wrapper);
    }

    if (this.stopped.get()) {
      wrapper.rejectTask(new ThreadPoolStoppedException("Thread pool has been stopped"));
      return;
    }

    wrapper.acceptTask();

    long completionTimeout = wrapper.getTaskCompletionTimeout();
    TimeoutInfo info = null;
    if (completionTimeout > 0L) {
      checkTimeoutMonitor();

      info = new TimeoutInfo(wrapper, completionTimeout);
      this.tasksWithTimeouts.insert(info);
    }
    int waitType = wrapper.getTaskWaitType();
    switch (waitType) {
    case 2:
      executeOnThread(wrapper);
      break;
    default:
      execute(wrapper);
    }

    waitForTask(wrapper);
  }

  public void runTask(Task task) {
    BasicTaskWrapper wrapper = new BasicTaskWrapper(task);
    runTaskWrapper(wrapper);
  }

  public void run(Runnable runnable) {
    run(runnable, 0L, 0L);
  }

  public void run(Runnable runnable, long startTimeout, long completeTimeout) {
    RunnableTaskWrapper wrapper = new RunnableTaskWrapper(runnable, startTimeout, completeTimeout);

    runTaskWrapper(wrapper);
  }

  public ThreadGroup getThreadGroup() {
    return this.threadGroup;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPoolNumber() {
    return this.poolNumber;
  }

  public String getThreadGroupName() {
    return this.threadGroup.getName();
  }

  public void setThreadGroupName(String threadGroupName)
  {
    ThreadGroup group;
    synchronized (threadGroups) {
      group = (ThreadGroup)threadGroups.get(threadGroupName);
      if (group == null) {
        group = new ThreadGroup(JBOSS_THREAD_GROUP, threadGroupName);
        threadGroups.put(threadGroupName, group);
      }
    }
    this.threadGroup = group;
  }

  public int getQueueSize() {
    return this.queue.size();
  }

  public int getMaximumQueueSize() {
    return this.queue.capacity();
  }

  public void setMaximumQueueSize(int size) {
    this.queue.setCapacity(size);
  }

  public int getPoolSize() {
    return this.executor.getPoolSize();
  }

  public int getMinimumPoolSize() {
    return this.executor.getMinimumPoolSize();
  }

  public void setMinimumPoolSize(int size) {
    synchronized (this.executor) {
      this.executor.setKeepAliveSize(size);

      if (this.executor.getMaximumPoolSize() < size) {
        this.executor.setMinimumPoolSize(size);
        this.executor.setMaximumPoolSize(size);
      }
    }
  }

  public int getMaximumPoolSize() {
    return this.executor.getMaximumPoolSize();
  }

  public void setMaximumPoolSize(int size) {
    synchronized (this.executor) {
      this.executor.setMinimumPoolSize(size);
      this.executor.setMaximumPoolSize(size);

      if (this.executor.getKeepAliveSize() > size)
        this.executor.setKeepAliveSize(size);
    }
  }

  public long getKeepAliveTime() {
    return this.executor.getKeepAliveTime();
  }

  public void setKeepAliveTime(long time) {
    this.executor.setKeepAliveTime(time);
  }

  public BlockingMode getBlockingMode() {
    return this.blockingMode;
  }

  public void setBlockingMode(BlockingMode mode) {
    this.blockingMode = mode;

    if (this.blockingMode == BlockingMode.RUN)
      this.executor.runWhenBlocked();
    else if (this.blockingMode == BlockingMode.WAIT)
      this.executor.waitWhenBlocked();
    else if (this.blockingMode == BlockingMode.DISCARD)
      this.executor.discardWhenBlocked();
    else if (this.blockingMode == BlockingMode.DISCARD_OLDEST)
      this.executor.discardOldestWhenBlocked();
    else if (this.blockingMode == BlockingMode.ABORT)
      this.executor.abortWhenBlocked();
    else
      throw new IllegalArgumentException("Failed to recognize mode: " + mode);
  }

  public void setBlockingMode(String name)
  {
    this.blockingMode = BlockingMode.toBlockingMode(name);
    if (this.blockingMode == null)
      this.blockingMode = BlockingMode.ABORT;
  }

  public ThreadPool getInstance() {
    return this;
  }

  public void stop() {
    stop(false);
  }

  public String toString()
  {
    return this.name + '(' + this.poolNumber + ')';
  }

  protected void executeOnThread(TaskWrapper wrapper)
  {
    if (log.isDebugEnabled()) {
      log.debug("executeOnThread, wrapper=" + wrapper);
    }

    wrapper.run();
  }

  protected void execute(TaskWrapper wrapper)
  {
    if (log.isDebugEnabled()) {
      log.debug("execute, wrapper=" + wrapper);
    }
    try
    {
      this.executor.execute(wrapper);
    } catch (Throwable t) {
      wrapper.rejectTask(new ThreadPoolFullException(t.toString()));
    }
  }

  protected void waitForTask(TaskWrapper wrapper)
  {
    wrapper.waitForTask();
  }

  protected synchronized void checkTimeoutMonitor()
  {
    if (this.timeoutTask == null)
      this.timeoutTask = new TimeoutMonitor(this.name, log);
  }

  protected TimeoutInfo getNextTimeout()
  {
    TimeoutInfo info = (TimeoutInfo)this.tasksWithTimeouts.extract();
    return info;
  }

  private class TimeoutMonitor
    implements Runnable
  {
    final Logger log;

    TimeoutMonitor(String name, Logger log)
    {
      this.log = log;
      Thread t = new Thread(this, name + " TimeoutMonitor");
      t.setDaemon(true);
      t.start();
    }

    public void run()
    {
      boolean isStopped = BasicThreadPool.this.stopped.get();
      while (!isStopped) {
        try {
          BasicThreadPool.TimeoutInfo info = BasicThreadPool.this.getNextTimeout();
          if (info != null) {
            long now = System.currentTimeMillis();
            long timeToTimeout = info.getTaskCompletionTimeout(now);
            if (timeToTimeout > 0L) {
              if (this.log.isDebugEnabled()) {
                this.log.debug("Will check wrapper=" + info.getTaskWrapper() + " after " + timeToTimeout);
              }

              Thread.sleep(timeToTimeout);
            }

            TaskWrapper wrapper = info.getTaskWrapper();
            if (!wrapper.isComplete()) {
              if (this.log.isDebugEnabled()) {
                this.log.debug("Failed completion check for wrapper=" + wrapper);
              }

              if (info.stopTask() == true)
              {
                info.setTimeout(1000L);
                BasicThreadPool.this.tasksWithTimeouts.insert(info);
                if (this.log.isDebugEnabled()) {
                  this.log.debug("Rescheduled completion check for wrapper=" + wrapper);
                }
              }
            }
          }
          else
          {
            Thread.sleep(1000L);
          }
        } catch (InterruptedException e) {
          if (this.log.isDebugEnabled())
            this.log.debug("Timeout monitor has been interrupted", e);
        }
        catch (Throwable e) {
          if (this.log.isDebugEnabled()) {
            this.log.debug("Timeout monitor saw unexpected error", e);
          }
        }
        isStopped = BasicThreadPool.this.stopped.get();
      }
    }
  }

  private static class TimeoutInfo
    implements Comparable
  {
    long start;
    long timeoutMS;
    TaskWrapper wrapper;
    boolean firstStop;

    TimeoutInfo(TaskWrapper wrapper, long timeout)
    {
      this.start = System.currentTimeMillis();
      this.timeoutMS = (this.start + timeout);
      this.wrapper = wrapper;
    }

    public void setTimeout(long timeout) {
      this.start = System.currentTimeMillis();
      this.timeoutMS = (this.start + timeout);
    }

    public int compareTo(Object o)
    {
      TimeoutInfo ti = (TimeoutInfo)o;
      long to0 = this.timeoutMS;
      long to1 = ti.timeoutMS;
      int diff = (int)(to0 - to1);
      return diff;
    }

    TaskWrapper getTaskWrapper() {
      return this.wrapper;
    }

    public long getTaskCompletionTimeout() {
      return this.wrapper.getTaskCompletionTimeout();
    }

    public long getTaskCompletionTimeout(long now)
    {
      return this.timeoutMS - now;
    }

    public boolean stopTask()
    {
      this.wrapper.stopTask();
      boolean wasFirstStop = !this.firstStop;
      this.firstStop = true;
      return wasFirstStop;
    }
  }

  private class ThreadPoolThreadFactory
    implements ThreadFactory
  {
    private ThreadPoolThreadFactory()
    {
    }

    public Thread newThread(Runnable runnable)
    {
      String threadName = BasicThreadPool.this.toString() + "-" + BasicThreadPool.this.lastThreadNumber.increment();

      Thread thread = new Thread(BasicThreadPool.this.threadGroup, runnable, threadName);
      thread.setDaemon(true);
      return thread;
    }
  }
}