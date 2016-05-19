package DistributeDataBase.parser.result;

import DistributeDataBase.common.DBType;

public class SqlParserResultFactory
{
  public static SqlParserResult createSqlParserResult(ZdalSchemaStatVisitor visitor, DBType dbType)
  {
    if (dbType.isMysql()) {
      if (!(visitor instanceof ZdalMySqlSchemaStatVisitor)) {
        throw new SqlParserException("ERROR ## the visitor is not ZdalMySqlSchemaStatVisitor");
      }

      return new MysqlSqlParserResult(visitor);
    }if (dbType.isOracle()) {
      if (!(visitor instanceof ZdalOracleSchemaStatVisitor)) {
        throw new SqlParserException("ERROR ## the visitor is not ZdalOracleSchemaStatVisitor");
      }

      return new OracleSqlParserResult(visitor);
    }if (dbType.isDB2()) {
      if (!(visitor instanceof ZdalDB2SchemaStatVisitor)) {
        throw new SqlParserException("ERROR ## the visitor is not ZdalDB2SchemaStatVisitor");
      }
      return new DB2SqlParserResult(visitor);
    }
    throw new IllegalArgumentException("ERROR ## dbType = " + dbType + " is not support");
  }
}