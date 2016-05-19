package DistributeDataBase.parser;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

public class ParserCache
{
  private static final ParserCache INSTANCE = new ParserCache();

  private final ReentrantLock lock = new ReentrantLock();

  private final Map<String, ItemValue> map = new LinkedHashMap();

  public static final ParserCache instance()
  {
    return INSTANCE;
  }

  public int size()
  {
    return this.map.size();
  }

  protected ItemValue get(String sql)
  {
    return (ItemValue)this.map.get(sql);
  }

  public SqlType getSqlType(String sql) {
    ItemValue itemValue = get(sql);
    if (itemValue != null) {
      return itemValue.getSqlType();
    }
    return null;
  }

  public SqlType setSqlTypeIfAbsent(String sql, SqlType sqlType)
  {
    ItemValue itemValue = get(sql);
    SqlType returnSqlType = null;
    if (itemValue == null)
    {
      this.lock.lock();
      try
      {
        itemValue = get(sql);
        if (itemValue == null)
        {
          itemValue = new ItemValue();

          put(sql, itemValue);
        }
      }
      finally {
        this.lock.unlock();
      }

      returnSqlType = itemValue.setSqlTypeIfAbsent(sqlType);
    }
    else if (itemValue.getFutureVisitor() == null)
    {
      returnSqlType = itemValue.setSqlTypeIfAbsent(sqlType);
    }
    else {
      returnSqlType = itemValue.getSqlType();
    }

    return returnSqlType;
  }

  public FutureTask<ZdalSchemaStatVisitor> getFutureTask(String sql) {
    ItemValue itemValue = get(sql);
    if (itemValue != null) {
      return itemValue.getFutureVisitor();
    }
    return null;
  }

  public List<String> getTableNameReplacement(String sql)
  {
    ItemValue itemValue = get(sql);
    if (itemValue != null) {
      return itemValue.getTableNameReplacement();
    }
    return null;
  }

  public List<String> setTableNameReplacementIfAbsent(String sql, List<String> tablenameReplacement)
  {
    ItemValue itemValue = get(sql);
    List returnList = null;
    if (itemValue == null)
    {
      this.lock.lock();
      try
      {
        itemValue = get(sql);
        if (itemValue == null)
        {
          itemValue = new ItemValue();

          put(sql, itemValue);
        }
      }
      finally {
        this.lock.unlock();
      }

      returnList = itemValue.setTableNameReplacementIfAbsent(tablenameReplacement);
    }
    else if (itemValue.getTableNameReplacement() == null)
    {
      returnList = itemValue.setTableNameReplacementIfAbsent(tablenameReplacement);
    }
    else {
      returnList = itemValue.getTableNameReplacement();
    }

    return returnList;
  }

  public FutureTask<ZdalSchemaStatVisitor> setFutureTaskIfAbsent(String sql, FutureTask<ZdalSchemaStatVisitor> future)
  {
    ItemValue itemValue = get(sql);
    FutureTask returnFutureTask = null;
    if (itemValue == null)
    {
      this.lock.lock();
      try
      {
        itemValue = get(sql);
        if (itemValue == null)
        {
          itemValue = new ItemValue();

          put(sql, itemValue);
        }
      }
      finally {
        this.lock.unlock();
      }

      returnFutureTask = itemValue.setFutureVisitorIfAbsent(future);
    }
    else if (itemValue.getFutureVisitor() == null)
    {
      returnFutureTask = itemValue.setFutureVisitorIfAbsent(future);
    } else {
      returnFutureTask = itemValue.getFutureVisitor();
    }

    return returnFutureTask;
  }

  protected void put(String sql, ItemValue itemValue)
  {
    this.map.put(sql, itemValue);
  }

  protected static class ItemValue
  {
    private AtomicReference<SqlType> sqlType = new AtomicReference();

    private AtomicReference<List<String>> tableNameReplacement = new AtomicReference();

    private AtomicReference<FutureTask<ZdalSchemaStatVisitor>> futureVisitor = new AtomicReference();

    public SqlType getSqlType() {
      return (SqlType)this.sqlType.get();
    }

    public SqlType setSqlTypeIfAbsent(SqlType sqlTypeinput)
    {
      if (this.sqlType.compareAndSet(null, sqlTypeinput)) {
        return sqlTypeinput;
      }

      return (SqlType)this.sqlType.get();
    }

    public List<String> getTableNameReplacement()
    {
      return (List)this.tableNameReplacement.get();
    }

    public List<String> setTableNameReplacementIfAbsent(List<String> tableNameReplacementList)
    {
      if (this.tableNameReplacement.compareAndSet(null, tableNameReplacementList)) {
        return tableNameReplacementList;
      }

      return (List)this.tableNameReplacement.get();
    }

    public FutureTask<ZdalSchemaStatVisitor> getFutureVisitor()
    {
      return (FutureTask)this.futureVisitor.get();
    }

    public FutureTask<ZdalSchemaStatVisitor> setFutureVisitorIfAbsent(FutureTask<ZdalSchemaStatVisitor> future)
    {
      if (this.futureVisitor.compareAndSet(null, future)) {
        return future;
      }

      return (FutureTask)this.futureVisitor.get();
    }
  }
}