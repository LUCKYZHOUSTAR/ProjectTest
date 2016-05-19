package DistributeDataBase.dataSource.resource.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;

public final class ThrowableHandler
{
  private static Logger log = Logger.getLogger(ThrowableHandler.class);

  protected static List listeners = Collections.synchronizedList(new ArrayList());

  public static void addThrowableListener(ThrowableListener listener)
  {
    if (!listeners.contains(listener))
      listeners.add(listener);
  }

  public static void removeThrowableListener(ThrowableListener listener)
  {
    listeners.remove(listener);
  }

  protected static void fireOnThrowable(int type, Throwable t)
  {
    Object[] list = listeners.toArray();

    for (int i = 0; i < list.length; i++)
      ((ThrowableListener)list[i]).onThrowable(type, t);
  }

  public static void add(int type, Throwable t)
  {
    if (t == null)
      return;
    try
    {
      fireOnThrowable(type, t);
    }
    catch (Throwable bad) {
      log.error(bad);
    }
  }

  public static void add(Throwable t)
  {
    add(0, t);
  }

  public static void addError(Throwable t)
  {
    add(1, t);
  }

  public static void addWarning(Throwable t)
  {
    add(1, t);
  }

  public static abstract interface Type
  {
    public static final int UNKNOWN = 0;
    public static final int ERROR = 1;
    public static final int WARNING = 2;
  }
}