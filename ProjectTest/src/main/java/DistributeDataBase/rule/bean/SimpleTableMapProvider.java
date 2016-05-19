package DistributeDataBase.rule.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import DistributeDataBase.rule.ruleengine.entities.abstractentities.SharedElement;
import DistributeDataBase.rule.ruleengine.entities.convientobjectmaker.TableMapProvider;
import DistributeDataBase.rule.ruleengine.util.RuleUtils;

public class SimpleTableMapProvider
  implements TableMapProvider
{
  public static final String NORMAL_TAOBAO_TYPE = "NORMAL";
  public static final String DEFAULT_PADDING = "_";
  protected static final int DEFAULT_INT = -1;
  public static final int DEFAULT_TABLES_NUM_FOR_EACH_DB = -1;
  private String type = "NORMAL";

  private String padding = "_";

  private int width = -1;
  private String tableFactor;
  private String logicTable;
  private int step = 1;

  private int tablesNumberForEachDatabases = -1;
  private String parentID;
  private int from = -1;

  private int to = -1;

  protected boolean doesNotSetTablesNumberForEachDatabases() {
    return this.tablesNumberForEachDatabases == -1;
  }

  public int getFrom() {
    return this.from;
  }

  public String getPadding() {
    return this.padding;
  }

  public String getParentID() {
    return this.parentID;
  }

  public int getStep() {
    return this.step;
  }

  public static String getSuffixInit(int w, int i) {
    String suffix = null;
    if (w != -1) {
      suffix = RuleUtils.placeHolder(w, i);
    }
    else {
      suffix = String.valueOf(i);
    }
    return suffix;
  }

  protected List<String> getSuffixList(int from, int to, int width, int step, String tableFactor, String padding)
  {
    if ((from == -1) || (to == -1)) {
      throw new IllegalArgumentException("from,to must be spec!");
    }
    int length = to - from + 1;
    List tableList = new ArrayList(length);
    StringBuilder sb = new StringBuilder();
    sb.append(tableFactor);
    sb.append(padding);

    for (int i = from; i <= to; i += step) {
      StringBuilder singleTableBuilder = new StringBuilder(sb.toString());

      String suffix = getSuffixInit(width, i);
      singleTableBuilder.append(suffix);
      tableList.add(singleTableBuilder.toString());
    }

    return tableList;
  }

  public String getTableFactor() {
    return this.tableFactor;
  }

  public Map<String, SharedElement> getTablesMap()
  {
    TYPE typeEnum = TYPE.valueOf(this.type);

    makeRealTableNameTaobaoLike(typeEnum);

    if ((this.tableFactor == null) && (this.logicTable != null)) {
      this.tableFactor = this.logicTable;
    }
    if (this.tableFactor == null)
      throw new IllegalArgumentException("没有表名生成因子");
    List tableNames;
    if (doesNotSetTablesNumberForEachDatabases()) {
      tableNames = getSuffixList(this.from, this.to, this.width, this.step, this.tableFactor, this.padding);
    }
    else {
      int multiple = 0;
      try {
        multiple = Integer.valueOf(this.parentID).intValue();
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException(new StringBuilder().append("使用simpleTableMapProvider并且指定了tablesNumberForEachDatabase参数，database的index值必须是个integer数字当前database的index是:").append(this.parentID).toString());
      }

      int start = this.tablesNumberForEachDatabases * multiple;

      int end = start + this.tablesNumberForEachDatabases - 1;

      tableNames = getSuffixList(start, end, this.width, this.step, this.tableFactor, this.padding);
    }
    List tables = null;
    tables = new ArrayList(tableNames.size());
    for (String tableName : tableNames) {
      Table tab = new Table();
      tab.setTableName(tableName);
      tables.add(tab);
    }
    Map returnMap = RuleUtils.getSharedElemenetMapBySharedElementList(tables);

    return returnMap;
  }

  public int getTablesNumberForEachDatabases() {
    return this.tablesNumberForEachDatabases;
  }

  public int getTo() {
    return this.to;
  }

  public String getType() {
    return this.type;
  }

  public int getWidth() {
    return this.width;
  }

  protected void makeRealTableNameTaobaoLike(TYPE typeEnum)
  {
    if (typeEnum != TYPE.CUSTOM)
    {
      if (this.padding == null)
        this.padding = "_";
      if ((this.to != -1) && (this.width == -1))
        this.width = String.valueOf(this.to).length();
    }
  }

  public void setFrom(int from)
  {
    this.from = from;
  }

  public void setLogicTable(String logicTable) {
    this.logicTable = logicTable;
  }

  public void setPadding(String padding) {
    this.padding = padding;
  }

  public void setParentID(String parentID) {
    this.parentID = parentID;
  }

  public void setStep(int step) {
    this.step = step;
  }

  public void setTableFactor(String tableFactor) {
    this.tableFactor = tableFactor;
  }

  public void setTablesNumberForEachDatabases(int tablesNumberForEachDatabases) {
    this.tablesNumberForEachDatabases = tablesNumberForEachDatabases;
  }

  public void setTo(int to) {
    this.to = to;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setWidth(int width) {
    if (width > 8) {
      throw new IllegalArgumentException("占位符不能超过8位");
    }

    if (width < 0) {
      throw new IllegalArgumentException("占位符不能为负值");
    }
    this.width = width;
  }

  public static enum TYPE
  {
    NORMAL, CUSTOM;
  }
}