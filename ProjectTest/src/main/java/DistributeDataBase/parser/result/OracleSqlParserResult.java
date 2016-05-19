package DistributeDataBase.parser.result;

import java.util.List;
import java.util.Set;

public class OracleSqlParserResult extends DefaultSqlParserResult
{
  public OracleSqlParserResult(ZdalSchemaStatVisitor visitor)
  {
    super(visitor);
  }

  public int isRowCountBind()
  {
    ZdalOracleSchemaStatVisitor mysqlVisitor = (ZdalOracleSchemaStatVisitor)this.visitor;
    Set rowNums = mysqlVisitor.getRownums();
    if ((rowNums == null) || (rowNums.isEmpty())) {
      return -1;
    }
    for (BindVarCondition rowNum : rowNums) {
      if ("ROWCOUNT".equalsIgnoreCase(rowNum.getColumnName())) {
        if (rowNum.getValue() == null) {
          return rowNum.getIndex();
        }
        return -1;
      }
    }

    return -1;
  }

  public int getMax(List<Object> arguments) {
    ZdalOracleSchemaStatVisitor oracleVisitor = (ZdalOracleSchemaStatVisitor)this.visitor;
    Set rowNums = oracleVisitor.getRownums();
    if ((rowNums == null) || (rowNums.isEmpty())) {
      return -1000;
    }
    int result = -1000;

    for (BindVarCondition rowNum : rowNums) {
      if ("ROWCOUNT".equalsIgnoreCase(rowNum.getColumnName()))
      {
        if (rowNum.getValue() == null) {
          Object obj = arguments.get(rowNum.getIndex());
          if ((obj instanceof Long))
            throw new SqlParserException("ERROR ## row selecter can't handle long data");
          if ((obj instanceof Integer)) {
            int tmp = ((Integer)obj).intValue();
            if (rowNum.getOperator().equals(SQLBinaryOperator.LessThan.name)) {
              tmp -= 1;
            }
            if (tmp > result)
              result = tmp;
          }
          else {
            throw new SqlParserException("ERROR ## bind rowcount var has an error , " + obj + " is not a int value");
          }
        }
        else {
          Comparable tmp = rowNum.getValue();
          if ((tmp instanceof Number)) {
            int rowcount = ((Integer)tmp).intValue();
            if (rowNum.getOperator().equals(SQLBinaryOperator.LessThan.name)) {
              rowcount -= 1;
            }
            if (rowcount > result)
              result = rowcount;
          }
          else {
            throw new SqlParserException("ERROR ## get rowcount's value has an error," + tmp + " is not a int value");
          }
        }
      }
    }

    return result;
  }

  public int isSkipBind() {
    ZdalOracleSchemaStatVisitor oracleVisitor = (ZdalOracleSchemaStatVisitor)this.visitor;
    Set rowNums = oracleVisitor.getRownums();
    if ((rowNums == null) || (rowNums.isEmpty())) {
      return -1;
    }
    for (BindVarCondition rowNum : rowNums) {
      if ("OFFSET".equalsIgnoreCase(rowNum.getColumnName())) {
        if (rowNum.getValue() == null) {
          return rowNum.getIndex();
        }
        return -1;
      }
    }

    return -1;
  }

  public int getSkip(List<Object> arguments) {
    ZdalOracleSchemaStatVisitor oracleVisitor = (ZdalOracleSchemaStatVisitor)this.visitor;
    Set rowNums = oracleVisitor.getRownums();
    if ((rowNums == null) || (rowNums.isEmpty())) {
      return -1000;
    }
    int result = -1000;

    for (BindVarCondition rowNum : rowNums) {
      if ("OFFSET".equals(rowNum.getColumnName()))
      {
        if (rowNum.getValue() == null) {
          Object obj = arguments.get(rowNum.getIndex());
          if ((obj instanceof Long))
            throw new SqlParserException("ERROR ## row selecter can't handle long data");
          if ((obj instanceof Integer)) {
            int tmp = ((Integer)obj).intValue();
            if (rowNum.getOperator().equals(SQLBinaryOperator.GreaterThanOrEqual.name)) {
              tmp -= 1;
            }
            if (tmp > result)
              result = tmp;
          }
          else {
            throw new SqlParserException("ERROR ## bind offset var has an error , " + obj + " is not a int value");
          }
        }
        else {
          Comparable tmp = rowNum.getValue();
          if ((tmp instanceof Number)) {
            int offset = ((Integer)tmp).intValue();
            if (rowNum.getOperator().equals(SQLBinaryOperator.GreaterThanOrEqual.name)) {
              offset -= 1;
            }
            if (offset > result)
              result = offset;
          }
          else {
            throw new SqlParserException("ERROR ## get offset's value has an error," + tmp + " is not a int value");
          }
        }
      }
    }

    return result;
  }
}