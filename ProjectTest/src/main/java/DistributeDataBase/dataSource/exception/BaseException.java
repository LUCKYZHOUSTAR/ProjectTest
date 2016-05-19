package DistributeDataBase.dataSource.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.SQLException;

import DistributeDataBase.dataSource.resource.NestedThrowable;


public class BaseException extends SQLException
  implements NestedThrowable
{
  private static final long serialVersionUID = -2725065717652326046L;
  protected final Throwable nested;

  public BaseException(String msg)
  {
    super(msg);
    this.nested = null;
  }

  public BaseException(String msg, Throwable nested)
  {
    super(msg);
    this.nested = nested;
    NestedThrowable.Util.checkNested(this, nested);
  }

  public BaseException(Throwable nested)
  {
    this(nested.getMessage(), nested);
  }

  public BaseException(String msg, String state)
  {
    super(msg, state);
    this.nested = null;
  }

  public BaseException(String msg, String state, int code)
  {
    super(msg, state, code);
    this.nested = null;
  }

  public Throwable getNested()
  {
    return this.nested;
  }

  public Throwable getCause()
  {
    return this.nested;
  }

  public String getMessage()
  {
    return NestedThrowable.Util.getMessage(super.getMessage(), this.nested);
  }

  public void printStackTrace(PrintStream stream)
  {
    if ((this.nested == null) || (NestedThrowable.PARENT_TRACE_ENABLED)) {
      super.printStackTrace(stream);
    }
    NestedThrowable.Util.print(this.nested, stream);
  }

  public void printStackTrace(PrintWriter writer)
  {
    if ((this.nested == null) || (NestedThrowable.PARENT_TRACE_ENABLED)) {
      super.printStackTrace(writer);
    }
    NestedThrowable.Util.print(this.nested, writer);
  }

  public void printStackTrace()
  {
    printStackTrace(System.err);
  }
}