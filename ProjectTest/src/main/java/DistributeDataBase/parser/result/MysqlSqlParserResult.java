package DistributeDataBase.parser.result;

import java.util.List;
import java.util.Set;

public class MysqlSqlParserResult extends DefaultSqlParserResult
{
  public MysqlSqlParserResult(ZdalSchemaStatVisitor visitor)
  {
    super(visitor);
  }

  public int isRowCountBind()
  {
    ZdalMySqlSchemaStatVisitor mysqlVisitor = (ZdalMySqlSchemaStatVisitor)this.visitor;
    Set limits = mysqlVisitor.getLimits();
    if ((limits == null) || (limits.isEmpty())) {
      return -1;
    }
    for (BindVarCondition limit : limits) {
      if ("ROWCOUNT".equals(limit.getColumnName())) {
        if (limit.getValue() == null) {
          return limit.getIndex();
        }
        return -1;
      }
    }

    return -1;
  }

  public int getMax(List<Object> arguments) {
    int skip = getSkip(arguments);
    if ((skip != -1000) && (skip < 0)) {
      throw new SqlParserException("ERROR ## the skip is less than 0");
    }

    int rowCount = getRowCount(arguments);
    if ((rowCount != -1000) && (rowCount < 0)) {
      throw new SqlParserException("ERROR ## the rowcount is less than 0");
    }
    if (skip == -1000) {
      if (rowCount == -1000)
      {
        return -1000;
      }

      return rowCount;
    }

    if (rowCount == -1000) {
      return skip;
    }
    return skip + rowCount;
  }

  public int isSkipBind()
  {
    ZdalMySqlSchemaStatVisitor mysqlVisitor = (ZdalMySqlSchemaStatVisitor)this.visitor;
    Set limits = mysqlVisitor.getLimits();
    if ((limits == null) || (limits.isEmpty())) {
      return -1;
    }
    for (BindVarCondition limit : limits) {
      if ("OFFSET".equals(limit.getColumnName())) {
        if (limit.getValue() == null) {
          return limit.getIndex();
        }
        return -1;
      }
    }

    return -1;
  }

  public int getSkip(List<Object> arguments) {
    ZdalMySqlSchemaStatVisitor mysqlVisitor = (ZdalMySqlSchemaStatVisitor)this.visitor;
    Set limits = mysqlVisitor.getLimits();
    if ((limits == null) || (limits.isEmpty())) {
      return -1000;
    }
    int result = -1000;

    for (BindVarCondition limit : limits) {
      if ("OFFSET".equals(limit.getColumnName()))
      {
        if (limit.getValue() == null) {
          Object obj = arguments.get(limit.getIndex());
          if ((obj instanceof Long))
            throw new SqlParserException("ERROR ## row selecter can't handle long data");
          if ((obj instanceof Integer)) {
            int tmp = ((Integer)obj).intValue();
            if (tmp > result)
              result = tmp;
          }
          else {
            throw new SqlParserException("ERROR ## bind offset var has an error , " + obj + " is not a int value");
          }
        }
        else
        {
          Comparable tmp = limit.getValue();
          if ((tmp instanceof Number)) {
            int skip = ((Integer)tmp).intValue();
            if (skip > result)
              result = skip;
          }
          else {
            throw new SqlParserException("ERROR ## get offset's value has an error," + tmp + " is not a int value");
          }
        }
      }
    }

    return result;
  }

  private int getRowCount(List<Object> args)
  {
    ZdalMySqlSchemaStatVisitor mysqlVisitor = (ZdalMySqlSchemaStatVisitor)this.visitor;
    Set limits = mysqlVisitor.getLimits();
    if ((limits == null) || (limits.isEmpty())) {
      return -1000;
    }
    int result = -1000;

    for (BindVarCondition limit : limits) {
      if ("ROWCOUNT".equals(limit.getColumnName()))
      {
        if (limit.getValue() == null) {
          Object obj = args.get(limit.getIndex());
          if ((obj instanceof Long))
            throw new SqlParserException("ERROR ## row selecter can't handle long data");
          if ((obj instanceof Integer)) {
            int tmp = ((Integer)obj).intValue();
            if (tmp > result)
              result = tmp;
          }
          else {
            throw new SqlParserException("ERROR ## bind rowcount var has an error , " + obj + " is not a int value");
          }
        }
        else {
          Comparable tmp = limit.getValue();
          if ((tmp instanceof Number)) {
            int skip = ((Integer)tmp).intValue();
            if (skip > result)
              result = skip;
          }
          else {
            throw new SqlParserException("ERROR ## get rowcount's value has an error," + tmp + " is not a int value");
          }
        }
      }
    }

    return result;
  }
}