package DistributeDataBase.dataSource.exception;

import java.sql.SQLException;

public class NestedSQLException extends SQLException
{
  private static final long serialVersionUID = -441747636494736964L;

  public NestedSQLException(String msg)
  {
    super(msg);
  }

  public NestedSQLException(String msg, Throwable nested)
  {
    super(msg, nested);
  }

  public NestedSQLException(Throwable nested)
  {
    this(nested.getMessage(), nested);
  }

  public NestedSQLException(String msg, String state)
  {
    super(msg, state);
  }

  public NestedSQLException(String msg, String state, int code)
  {
    super(msg, state, code);
  }
}