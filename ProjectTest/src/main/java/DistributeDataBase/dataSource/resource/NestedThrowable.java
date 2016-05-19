package DistributeDataBase.dataSource.resource;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;

import org.apache.commons.lang.NullArgumentException;
import org.apache.log4j.Logger;
import org.apache.tools.ant.taskdefs.Java;

public abstract interface NestedThrowable extends Serializable
{
  public static final boolean PARENT_TRACE_ENABLED = Util.getBoolean("parentTraceEnabled", true);

  public static final boolean NESTED_TRACE_ENABLED = Util.getBoolean("nestedTraceEnabled", ((Java.isCompatible(5)) && (!PARENT_TRACE_ENABLED)) || (!Java.isCompatible(5)));

  public static final boolean DETECT_DUPLICATE_NESTING = Util.getBoolean("detectDuplicateNesting", true);

  public abstract Throwable getNested();

  public abstract Throwable getCause();

  public static final class Util
  {
    private static Logger log = Logger.getLogger(NestedThrowable.class);

    private static Logger getLogger()
    {
      return log;
    }

    protected static boolean getBoolean(String name, boolean defaultValue)
    {
      name = NestedThrowable.class.getName() + "." + name;
      String value = System.getProperty(name, String.valueOf(defaultValue));

      log = getLogger();

      log.debug(name + "=" + value);

      return new Boolean(value).booleanValue();
    }

    public static void checkNested(NestedThrowable parent, Throwable child)
    {
      if ((!NestedThrowable.DETECT_DUPLICATE_NESTING) || (parent == null) || (child == null)) {
        return;
      }
      Class parentType = parent.getClass();
      Class childType = child.getClass();

      if (parentType.isAssignableFrom(childType))
      {
        log = getLogger();

        log.warn("Duplicate throwable nesting of same base type: " + parentType + " is assignable from: " + childType);
      }
    }

    public static String getMessage(String msg, Throwable nested)
    {
      StringBuffer buff = new StringBuffer(msg == null ? "" : msg);

      if (nested != null) {
        buff.append(msg == null ? "- " : "; - ").append("nested throwable: (").append(nested).append(")");
      }

      return buff.toString();
    }

    public static void print(Throwable nested, PrintStream stream)
    {
      if (stream == null) {
        throw new NullArgumentException("stream");
      }
      if ((NestedThrowable.NESTED_TRACE_ENABLED) && (nested != null))
        synchronized (stream) {
          if (NestedThrowable.PARENT_TRACE_ENABLED)
            stream.print(" + nested throwable: ");
          else {
            stream.print("[ parent trace omitted ]: ");
          }

          nested.printStackTrace(stream);
        }
    }

    public static void print(Throwable nested, PrintWriter writer)
    {
      if (writer == null) {
        throw new NullArgumentException("writer");
      }
      if ((NestedThrowable.NESTED_TRACE_ENABLED) && (nested != null))
        synchronized (writer) {
          if (NestedThrowable.PARENT_TRACE_ENABLED)
            writer.print(" + nested throwable: ");
          else {
            writer.print("[ parent trace omitted ]: ");
          }

          nested.printStackTrace(writer);
        }
    }
  }
}