/**
 * 
 */
package DistributeDataBase.rule.config.beans;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Binding;

import org.apache.commons.lang.StringUtils;

import DistributeDataBase.common.util.SimpleNamedMessageFormat;
import DistributeDataBase.common.util.TableSuffixTypeEnum;

/** 
* @ClassName: TableRule 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午6:45:22 
*  
*/
public class TableRule
implements Cloneable
{
private String logicTableName;
//代表的是PhysicalDataSourceBean的name属性
//也就是数据源的name属性
private String[] dbIndexes;
private String dbIndexPrefix;
private int dbIndexCount;
private List<Object> shardingRules;
private String tbSuffixPadding;
//dbRules的配置信息
private Object[] dbRules;
private Object[] tbRules;
private String[] uniqueKeys;
private boolean allowReverseOutput;
private boolean needRowCopy;
private boolean disableFullTableScan;
private String[] ruleParames;
private String[] dbRuleParames;
private String[] tbRuleParames;
private Binding binding;
private GroovyShell shell;
private List<String> dbRuleList;
private SuffixManager suffixManager;
private Preffix tbPreffix;

public TableRule()
{
  this.shardingRules = new ArrayList();

  this.disableFullTableScan = true;

  this.binding = new Binding();

  this.shell = new GroovyShell(this.binding);

  this.suffixManager = new SuffixManager();
}

public void parseTbSuffix()
  throws TableRule.ParseException
{
  this.suffixManager.parseTbSuffix(this.dbIndexes);
}

public void init()
  throws TableRule.ParseException
{
  if (this.dbIndexes == null) {
    if ((this.dbIndexPrefix == null) || (this.dbIndexCount <= 0)) {
      throw new IllegalArgumentException("dbIndexes没有配置");
    }

    int suffixLen = Integer.valueOf(this.dbIndexCount).toString().length();
    this.dbIndexes = new String[this.dbIndexCount];
    for (int i = 0; i < this.dbIndexCount; i++) {
      String suffix = String.valueOf(i);
      while (suffix.length() < suffixLen) {
        suffix = new StringBuilder().append("0").append(suffix).toString();
      }
      this.dbIndexes[i] = new StringBuilder().append(this.dbIndexPrefix).append(suffix).toString();
    }
  }

  setDbIndexCount(this.dbIndexes.length);
  replaceWithParam(this.dbRules, this.dbRuleParames != null ? this.dbRuleParames : this.ruleParames);
  replaceWithParam(this.tbRules, this.tbRuleParames != null ? this.tbRuleParames : this.ruleParames);
  String tbSuffix = this.suffixManager.getTbSuffix();
  if (tbSuffix != null) {
    if (!tbSuffix.startsWith(TableSuffixTypeEnum.groovyTableList.getValue())) {
      tbSuffix = replaceWithParam(tbSuffix, this.ruleParames);
    }
    this.suffixManager.setTbSuffix(tbSuffix);
    this.suffixManager.parseTbSuffix(this.dbIndexes);
  } else {
    this.suffixManager.init(this.dbIndexes);
  }
}

private static void replaceWithParam(Object[] rules, String[] params) {
  if ((params == null) || (rules == null)) {
    return;
  }
  for (int i = 0; i < rules.length; i++)
    if ((rules[i] instanceof String))
    {
      rules[i] = replaceWithParam((String)rules[i], params);
    } else if ((rules[i] instanceof MappingRuleBean)) {
      MappingRuleBean tmr = (MappingRuleBean)rules[i];

      String finalParameter = replaceWithParam(tmr.getParameter(), params);
      String finalExpression = replaceWithParam(tmr.getExpression(), params);
      tmr.setParameter(finalParameter);
      tmr.setExpression(finalExpression);
    }
}

private static String replaceWithParam(String template, String[] params)
{
  if ((params == null) || (template == null)) {
    return template;
  }
  if ((params.length != 0) && (params[0].indexOf(":") != -1))
  {
    return replaceWithNamedParam(template, params);
  }
  return new MessageFormat(template).format(params);
}

private static String replaceWithNamedParam(String template, String[] params) {
  Map args = new HashMap();
  for (String param : params) {
    int index = param.indexOf(":");
    if (index == -1) {
      throw new IllegalArgumentException(new StringBuilder().append("使用名字化的占位符替换失败！请检查配置。 params:").append(Arrays.asList(params)).toString());
    }

    args.put(param.substring(0, index).trim(), param.substring(index + 1).trim());
  }
  return new SimpleNamedMessageFormat(template).format(args);
}

private String toCommaString(String[] strArray) {
  if (strArray == null)
    return null;
  StringBuilder sb = new StringBuilder();
  for (String str : strArray) {
    sb.append(",").append(str);
  }
  if (strArray.length > 0) {
    sb.deleteCharAt(0);
  }
  return sb.toString();
}

public void setDbIndexes(String dbIndexes)
{
  if (StringUtils.isBlank(dbIndexes)) {
    throw new IllegalArgumentException("The dbIndexes set empty!");
  }
  String[] temp = dbIndexes.split(":");
  if (DBINDEX_TYPE.SETBYPOOL.getValue().equals(temp[0].trim())) {
    if (temp.length != 2) {
      throw new IllegalArgumentException("The dbIndexes set error!");
    }
    dbIndexes = temp[1].trim();
  } else if (DBINDEX_TYPE.SETBYGROOVY.getValue().equals(temp[0].trim())) {
    String expression = temp[1].trim();

    Object value = this.shell.evaluate(expression);
    if ((value instanceof List)) {
      List tableNameList = (List)value;
      this.dbIndexes = new String[tableNameList.size()];
      for (int i = 0; i < tableNameList.size(); i++) {
        this.dbIndexes[i] = ((String)tableNameList.get(i)).trim();
      }
      return;
    }
  }
  else
  {
    dbIndexes = temp[0].trim();
  }
  this.dbIndexes = dbIndexes.split(",");
}

public void setDbIndexes(String[] dbIndexes)
{
  for (String dbIndex : dbIndexes)
    setDbIndexes(dbIndex);
}

public String getDbIndexes()
{
  return toCommaString(this.dbIndexes);
}

public void setDbIndexArray(String[] array) {
  this.dbIndexes = array;
}

public String[] getDbIndexArray() {
  return this.dbIndexes;
}

public String[] getUniqueKeyArray() {
  return this.uniqueKeys;
}

public void setUniqueKeys(String uniquekeys) {
  this.uniqueKeys = uniquekeys.split(",");
}

public Object[] getDbRuleArray() {
  return this.dbRules;
}

public void setDbRuleArray(Object[] dbRules) {
  this.dbRules = dbRules;
}

public void setDbRuleArray(List<String> dbRules) {
  if (dbRules == null) {
    throw new IllegalArgumentException("The dbRules can't be null!");
  }
  if (this.dbRules == null) {
    this.dbRules = new Object[dbRules.size()];
  }
  for (int i = 0; i < dbRules.size(); i++)
    this.dbRules[i] = ((String)dbRules.get(i)).trim();
}

public void setDbRules(String dbRules)
{
  if (this.dbRules == null)
  {
    this.dbRules = dbRules.split("\\|");
  }
}

public Object[] getTbRuleArray() {
  return this.tbRules;
}

public void setTbRuleArray(Object[] tbRules) {
  this.tbRules = tbRules;
}

public void setTbRuleArray(List<String> tbRules) {
  this.tbRules = tbRules.toArray();
}

public void setTbRules(String tbRules) {
  if (this.tbRules == null)
  {
    this.tbRules = tbRules.split("\\|");
  }
}

public void setRuleParames(String ruleParames) {
  if (ruleParames.indexOf(124) != -1)
  {
    this.ruleParames = ruleParames.split("\\|");
  }
  else this.ruleParames = ruleParames.split(",");
}

public void setRuleParameArray(String[] ruleParames)
{
  this.ruleParames = ruleParames;
}

public void setDbRuleParames(String dbRuleParames) {
  this.dbRuleParames = dbRuleParames.split(",");
}

public void setDbRuleParameArray(String[] dbRuleParames) {
  this.dbRuleParames = dbRuleParames;
}

public void setTbRuleParames(String tbRuleParames) {
  this.tbRuleParames = tbRuleParames.split(",");
}

public void setTbRuleParameArray(String[] tbRuleParames) {
  this.tbRuleParames = tbRuleParames;
}

public void setTbSuffix(String tbSuffix)
{
  this.suffixManager.setTbSuffix(tbSuffix);
}

public boolean isAllowReverseOutput() {
  return this.allowReverseOutput;
}

public void setAllowReverseOutput(boolean allowReverseOutput) {
  this.allowReverseOutput = allowReverseOutput;
}

public boolean isNeedRowCopy() {
  return this.needRowCopy;
}

public void setNeedRowCopy(boolean needRowCopy) {
  this.needRowCopy = needRowCopy;
}

public String getDbIndexPrefix() {
  return this.dbIndexPrefix;
}

public void setDbIndexPrefix(String dbIndexPrefix) {
  this.dbIndexPrefix = dbIndexPrefix;
}

public int getDbIndexCount() {
  return this.dbIndexCount;
}

public void setDbIndexCount(int dbIndexCount) {
  this.dbIndexCount = dbIndexCount;
}

public void setTbSuffixFrom(int tbSuffixFrom) {
  this.suffixManager.getSuffix(0).setTbSuffixFrom(tbSuffixFrom);
}

public void setTbSuffixTo(int tbSuffixTo) {
  this.suffixManager.getSuffix(0).setTbSuffixTo(tbSuffixTo);
}

public void setTbSuffixWidth(int tbSuffixWidth) {
  this.suffixManager.getSuffix(0).setTbSuffixWidth(tbSuffixWidth);
}

public void setTbSuffixPadding(String tbSuffixPadding) {
  this.suffixManager.getSuffix(0).setTbSuffixPadding(tbSuffixPadding);
}

public String getTbSuffixPadding() {
  return this.tbSuffixPadding;
}

public void setTbNumForEachDb(int tbNumForEachDb) {
  this.suffixManager.getSuffix(0).setTbNumForEachDb(tbNumForEachDb);
}

public TableRule clone() throws CloneNotSupportedException
{
  return (TableRule)super.clone();
}

public boolean isDisableFullTableScan() {
  return this.disableFullTableScan;
}

public void setDisableFullTableScan(boolean disableFullTableScan) {
  this.disableFullTableScan = disableFullTableScan;
}

public SuffixManager getSuffixManager() {
  return this.suffixManager;
}

public Preffix getTbPreffix() {
  return this.tbPreffix;
}

public void setTbPreffix(Preffix tbPreffix) {
  this.tbPreffix = tbPreffix;
}

public void setShardingRules(List<Object> shardingRules) {
  this.shardingRules = shardingRules;
}

public List<Object> getShardingRules() {
  return this.shardingRules;
}

public String getLogicTableName()
{
  return this.logicTableName;
}

public void setLogicTableName(String logicTableName)
{
  this.logicTableName = logicTableName;
}

public List<String> getDbRuleList() {
  return this.dbRuleList;
}

public void setDbRuleList(List<String> dbRuleList) {
  this.dbRuleList = dbRuleList;
  int index;
  if ((null != dbRuleList) && (!dbRuleList.isEmpty())) {
    this.dbRules = new String[dbRuleList.size()];
    index = 0;
    for (String rule : this.dbRuleList)
      this.dbRules[(index++)] = rule.trim();
  }
}

public static class ParseException extends Exception
{
  private static final long serialVersionUID = 1L;

  public ParseException()
  {
  }

  public ParseException(String msg)
  {
    super();
  }
}

private static enum DBINDEX_TYPE
{
  SETBYPOOL("SETBYPOOL"), SETBYGROOVY("SETBYGROOVY");

  private String value;

  private DBINDEX_TYPE(String value) { this.value = value; }

  public String getValue()
  {
    return this.value;
  }
}
}
