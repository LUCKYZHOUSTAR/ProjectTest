package DistributeDataBase.dataSource.resource.util.threadpool;

import org.apache.log4j.Logger;

public class RunnableTaskWrapper
  implements TaskWrapper
{
  private static final Logger log = Logger.getLogger(RunnableTaskWrapper.class);
  private final Runnable runnable;
  private boolean started;
  private Thread runThread;
  private final long startTimeout;
  private final long completionTimeout;

  public RunnableTaskWrapper(Runnable runnable)
  {
    this(runnable, 0L, 0L);
  }

  public RunnableTaskWrapper(Runnable runnable, long startTimeout, long completeTimeout) {
    if (runnable == null)
      throw new IllegalArgumentException("Null runnable");
    this.runnable = runnable;
    this.startTimeout = startTimeout;
    this.completionTimeout = completeTimeout;
  }

  public int getTaskWaitType()
  {
    return 0;
  }

  public int getTaskPriority() {
    return 5;
  }

  public long getTaskStartTimeout() {
    return this.startTimeout;
  }

  public long getTaskCompletionTimeout() {
    return this.completionTimeout;
  }

  public void acceptTask()
  {
  }

  public void rejectTask(RuntimeException t) {
    throw t;
  }

  public void stopTask()
  {
    if ((this.runThread != null) && (!this.runThread.isInterrupted())) {
      this.runThread.interrupt();
      if (log.isDebugEnabled()) {
        log.debug("stopTask, interrupted thread=" + this.runThread);
      }
    }
    else if (this.runThread != null)
    {
      this.runThread.stop();
      if (log.isDebugEnabled())
        log.debug("stopTask, stopped thread=" + this.runThread);
    }
  }

  public void waitForTask()
  {
  }

  public boolean isComplete()
  {
    return (this.started == true) && (this.runThread == null);
  }

  public void run()
  {
    try
    {
      if (log.isDebugEnabled()) {
        log.debug("Begin run, wrapper=" + this);
      }

      this.runThread = Thread.currentThread();
      this.started = true;
      this.runnable.run();
      this.runThread = null;
      if (log.isDebugEnabled())
        log.debug("End run, wrapper=" + this);
    }
    catch (Throwable t)
    {
      log.warn("Unhandled throwable for runnable: " + this.runnable, t);
    }
  }
}