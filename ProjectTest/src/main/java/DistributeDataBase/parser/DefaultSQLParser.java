package DistributeDataBase.parser;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.apache.log4j.Logger;

import DistributeDataBase.common.DBType;

public class DefaultSQLParser
  implements SQLParser
{
  private static final Logger LOG = Logger.getLogger(DefaultSQLParser.class);

  private static final ParserCache GLOBALCACHE = ParserCache.instance();

  public SqlParserResult parse(String sql, DBType dbType) {
    parseSQL(sql, dbType);
    ZdalSchemaStatVisitor visitor = getStatement(sql);
    try {
      if (visitor == null)
      {
        parseSQL(sql, dbType);
        visitor = getStatement(sql);
      }
    }
    catch (Exception e) {
      throw new SqlParserException(new StringBuilder().append("the sql = ").append(sql).append(" is not support yet ").append(e.getMessage()).toString());
    }

    return SqlParserResultFactory.createSqlParserResult(visitor, dbType);
  }

  public void parseSQL(String sql)
  {
    nestedParseSql(sql, DBType.MYSQL);
  }

  public void parseSQL(String sql, DBType dbType) {
    nestedParseSql(sql, dbType);
  }

  private void nestedParseSql(final String sql, final DBType dbType) {
    if (sql == null) {
      throw new SqlParserException("sql must not be null");
    }

    FutureTask future = GLOBALCACHE.getFutureTask(sql);
    if (future == null) {
      Callable parserHandler = new Callable() {
        public ZdalSchemaStatVisitor call() throws Exception {
          List parserResults = DefaultSQLParser.this.getSqlStatements(sql, dbType);
          if ((parserResults == null) || (parserResults.isEmpty())) {
            DefaultSQLParser.LOG.error("ERROR ## the sql parser result is null,the sql = " + sql);
            return null;
          }
          if (parserResults.size() > 1) {
            DefaultSQLParser.LOG.warn("WARN ## after this sql parser,has more than one SQLStatement,the sql = " + sql);
          }

          SQLStatement statement = (SQLStatement)parserResults.get(0);
          ZdalSchemaStatVisitor visitor = null;
          if (dbType.isMysql()) {
            visitor = new ZdalMySqlSchemaStatVisitor();
            statement.accept(visitor);
          } else if (dbType.isOracle()) {
            visitor = new ZdalOracleSchemaStatVisitor();
            statement.accept(visitor);
          } else if (dbType.isDB2()) {
            visitor = new ZdalDB2SchemaStatVisitor();
            statement.accept(visitor);
          } else {
            throw new IllegalArgumentException("ERROR ## dbType = " + dbType + " is not support");
          }

          return visitor;
        }
      };
      future = new FutureTask(parserHandler);
      future = GLOBALCACHE.setFutureTaskIfAbsent(sql, future);
      future.run();
    }
  }

  public String outputParsedSql(List<SQLStatement> parserResults, boolean isMysql)
  {
    StringBuilder out = new StringBuilder();
    SQLASTOutputVisitor visitor = null;
    if (isMysql)
      visitor = new MySqlOutputVisitor(out);
    else {
      visitor = new OracleOutputVisitor(out);
    }
    for (SQLStatement stmt : parserResults) {
      stmt.accept(visitor);
    }

    return out.toString();
  }

  private List<SQLStatement> getSqlStatements(String sql, DBType dbType)
  {
    if (dbType.isMysql()) {
      MySqlStatementParser parser = new MySqlStatementParser(sql);
      return parser.parseStatementList();
    }if (dbType.isOracle()) {
      OracleStatementParser parser = new OracleStatementParser(sql);
      return parser.parseStatementList();
    }if (dbType.isDB2()) {
      OracleStatementParser parser = new OracleStatementParser(sql);
      return parser.parseStatementList();
    }
    throw new IllegalArgumentException(new StringBuilder().append("ERROR ## dbType = ").append(dbType).append(" is not support").toString());
  }

  private ZdalSchemaStatVisitor getStatement(String sql)
  {
    try
    {
      FutureTask future = GLOBALCACHE.getFutureTask(sql);
      if (future == null) {
        return null;
      }
      return (ZdalSchemaStatVisitor)future.get();
    }
    catch (Exception e) {
      throw new SqlParserException("ERROR ## get sqlparser result from cache has an error:", e);
    }
  }
}