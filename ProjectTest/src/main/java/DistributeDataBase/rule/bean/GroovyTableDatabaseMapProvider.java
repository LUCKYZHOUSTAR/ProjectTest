package com.alipay.zdal.rule.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Binding;

import org.apache.log4j.Logger;

import DistributeDataBase.common.util.TableSuffixTypeEnum;
import DistributeDataBase.rule.config.beans.Preffix;
import DistributeDataBase.rule.ruleengine.entities.abstractentities.SharedElement;

public class GroovyTableDatabaseMapProvider extends SimpleTableMapProvider
{
  private static final Logger log = Logger.getLogger(GroovyTableDatabaseMapProvider.class);
  private List<String> tableNames;
  private int dbNumber;
  private String expression;
  private Binding binding;
  private GroovyShell shell;
  private Preffix tbPreffix;
  private String logicTable;
  private static Map<String, Object> tbSuffixRuleResult = new HashMap();
  private String tbType;
  private DBAndNumberRelation dbAndNumberRelation;

  public GroovyTableDatabaseMapProvider()
  {
    this.binding = new Binding();

    this.shell = new GroovyShell(this.binding);

    this.dbAndNumberRelation = null;
  }

  protected List<String> getTableNames(String tableFactory, String expression)
    throws IllegalArgumentException
  {
    if ((expression == null) || (expression.equals(""))) {
      throw new IllegalArgumentException("groovy script is null or empty!");
    }
    long time1 = System.currentTimeMillis();

    Object value = tbSuffixRuleResult.get(expression);
    if (value == null) {
      value = this.shell.evaluate(expression);
      tbSuffixRuleResult.put(expression, value);
    }
    long time2 = System.currentTimeMillis();
    if (log.isDebugEnabled()) {
      log.debug("tbsuffix shell use time:" + (time2 - time1) + ",expression=" + expression);
    }

    if ((value instanceof List)) {
      List tableNameList = (List)value;
      List finalList = new ArrayList(tableNameList.size());
      String temp;
      if (TableSuffixTypeEnum.groovyTableList.getValue().equals(this.tbType)) {
        temp = getPadding();
        for (String tname : tableNameList) {
          finalList.add(tableFactory + temp + tname);
        }
      }
      else if (TableSuffixTypeEnum.groovyThroughAllDBTableList.getValue().equals(this.tbType))
      {
        int multiple = Integer.valueOf(super.getParentID()).intValue();
        int tableNumberForEachDB = tableNameList.size() / this.dbNumber;
        int begin = multiple * tableNumberForEachDB;
        int end = begin + tableNumberForEachDB;
        String temp = getPadding();
        for (int i = begin; i < end; i++) {
          String tname = (String)tableNameList.get(i);
          finalList.add(tableFactory + temp + tname);
        }
      }
      else if (TableSuffixTypeEnum.groovyAdjustTableList.getValue().equals(this.tbType))
      {
        if ((this.dbAndNumberRelation.tbNumberEnds[(this.dbAndNumberRelation.tbNumber.length - 1)] != tableNameList.size()) || (this.dbAndNumberRelation.dbNumberEnds[(this.dbAndNumberRelation.dbNumber.length - 1)] != this.dbNumber))
        {
          throw new IllegalArgumentException("The size is different, error. tableNameList.size=" + tableNameList.size() + ",dbNumber=" + this.dbNumber);
        }

        int dbId = Integer.valueOf(super.getParentID()).intValue();
        int number = 0;
        for (int i = 0; i < this.dbAndNumberRelation.dbNumberEnds.length; i++) {
          if (dbId < this.dbAndNumberRelation.dbNumberEnds[i]) {
            number = i;
            break;
          }
        }
        int begin = 0;
        if (number > 0) {
          for (int j = 0; j < number; j++) {
            begin += this.dbAndNumberRelation.dbNumber[j] * this.dbAndNumberRelation.tbNumber[j];
          }

          begin += (dbId - this.dbAndNumberRelation.dbNumberEnds[(number - 1)]) * this.dbAndNumberRelation.tbNumber[number];
        }
        else {
          begin = dbId * this.dbAndNumberRelation.tbNumber[0];
        }
        int end = begin + this.dbAndNumberRelation.tbNumber[number];
        String temp = getPadding();
        for (int i = begin; i < end; i++) {
          String tname = (String)tableNameList.get(i);
          finalList.add(tableFactory + temp + tname);
        }

      }

      if (log.isDebugEnabled()) {
        log.debug("dbId=" + super.getParentID() + ",tableNameList=" + finalList);
      }

      return finalList;
    }
    throw new IllegalArgumentException("执行脚本得到了错误的类型!请检查");
  }

  public Map<String, SharedElement> getTablesMap()
  {
    if (getTbPreffix() != null)
      setTableFactor(getTbPreffix().getTbPreffix());
    else if ((getTableFactor() == null) && (getLogicTable() != null)) {
      setTableFactor(this.logicTable);
    }
    if (getTableFactor() == null) {
      throw new IllegalArgumentException("没有表名生成因子");
    }
    try
    {
      this.tableNames = getTableNames(getTableFactor(), getExpression());
    } catch (Exception e) {
      log.error("不能得到tablesNames ,直接返回null. ", e);
      return null;
    }
    List tables = null;
    tables = new ArrayList(this.tableNames.size());
    for (String tableName : this.tableNames) {
      Table tab = new Table();
      tab.setTableName(tableName);
      if (log.isDebugEnabled()) {
        log.debug("Groovy table databases provide : get table names from groovy script " + tableName);
      }

      tables.add(tab);
    }
    Map returnMap = getSharedElemenetMapBySharedElementList(tables);

    return returnMap;
  }

  public Map<String, SharedElement> getSharedElemenetMapBySharedElementList(List<? extends SharedElement> sharedElementList)
  {
    Map returnMap = new HashMap();

    if (sharedElementList != null) {
      for (SharedElement sharedElement : sharedElementList) {
        Table t = (Table)sharedElement;
        String key = t.getTableName().substring(getTableFactor().length() + getPadding().length());

        returnMap.put(key, sharedElement);
      }
    }
    return returnMap;
  }

  protected List<String> getSuffixList(int from, int to, int width, int step, String tableFactor, String padding)
  {
    return null;
  }

  public List<String> getTableNames()
  {
    return this.tableNames;
  }

  public void setTableNames(List<String> tableNames) {
    this.tableNames = tableNames;
  }

  public String getExpression() {
    return this.expression;
  }

  public void setExpression(String expression)
  {
    if (TableSuffixTypeEnum.groovyAdjustTableList.getValue().equals(this.tbType)) {
      String[] temp = expression.split(":");

      if ((!temp[0].startsWith("[")) || (!temp[0].endsWith("]"))) {
        throw new IllegalArgumentException("The tbsuffix of groovyAjustTableList error!");
      }

      String part = temp[0].substring(1, temp[0].length() - 1);
      String[] part2 = part.split(",");
      int[] dbNumber = new int[part2.length];
      int[] tbNumber = new int[part2.length];
      for (int i = 0; i < part2.length; i++) {
        String[] temp2 = part2[i].split("_");
        if (temp2.length != 2) {
          throw new IllegalArgumentException("The db_table number relation error! it is " + part);
        }

        dbNumber[i] = Integer.parseInt(temp2[0].trim());
        tbNumber[i] = Integer.parseInt(temp2[1].trim());
      }
      setDbAndNumberRelation(new DBAndNumberRelation(dbNumber, tbNumber));
      expression = temp[1].trim();
    }
    this.expression = expression;
  }

  public String getLogicTable() {
    return this.logicTable;
  }

  public void setLogicTable(String logicTable) {
    this.logicTable = logicTable;
  }

  public Preffix getTbPreffix() {
    return this.tbPreffix;
  }

  public void setTbPreffix(Preffix tbPreffix) {
    this.tbPreffix = tbPreffix;
  }

  public void setDbNumber(int dbNumber)
  {
    this.dbNumber = dbNumber;
  }

  public int getDbNumber()
  {
    return this.dbNumber;
  }

  public String getTbType() {
    return this.tbType;
  }

  public void setTbType(String tbType) {
    this.tbType = tbType;
  }

  public void setDbAndNumberRelation(DBAndNumberRelation dbAndNumberRelation)
  {
    this.dbAndNumberRelation = dbAndNumberRelation;
  }

  public DBAndNumberRelation getDbAndNumberRelation()
  {
    return this.dbAndNumberRelation;
  }

  private static class DBAndNumberRelation
  {
    private final int[] dbNumber;
    private final int[] tbNumber;
    private final int[] dbNumberEnds;
    private final int[] tbNumberEnds;

    public DBAndNumberRelation(int[] dbNumber, int[] tbNumber)
    {
      this.dbNumber = dbNumber;
      this.tbNumber = tbNumber;
      this.dbNumberEnds = genDBNumberEnds(dbNumber);
      this.tbNumberEnds = genTbNumberEnds(dbNumber, tbNumber);
    }

    private int[] genDBNumberEnds(int[] dbNumber)
    {
      if (dbNumber == null) {
        return null;
      }
      int[] dbNumberEnds = new int[dbNumber.length];
      int sum = 0;
      for (int i = 0; i < dbNumber.length; i++) {
        sum += dbNumber[i];
        dbNumberEnds[i] = sum;
      }
      return dbNumberEnds;
    }

    private int[] genTbNumberEnds(int[] dbNumber, int[] tbNumber)
    {
      if (dbNumber == null) {
        return null;
      }
      int[] tbNumberEnds = new int[dbNumber.length];
      int sum = 0;
      for (int i = 0; i < dbNumber.length; i++) {
        sum += dbNumber[i] * tbNumber[i];
        tbNumberEnds[i] = sum;
      }
      return tbNumberEnds;
    }
  }
}