package DistributeDataBase.rule.bean;

import java.util.ArrayList;
import java.util.List;

public class SimpleTableDatabaseMapProvider extends SimpleTableMapProvider
{
  protected List<String> getSuffixList(int from, int to, int width, int step, String tableFactor, String padding)
  {
    List tableList = new ArrayList(1);
    StringBuilder sb = new StringBuilder();
    sb.append(tableFactor);
    sb.append(padding);

    int multiple = 0;
    try {
      multiple = Integer.valueOf(getParentID()).intValue();
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(new StringBuilder().append("使用SimpleTableDatabaseMapProvider，database的index值必须是个integer数字当前database的index是:").append(getParentID()).toString());
    }

    String suffix = getSuffixInit(width, multiple);
    sb.append(suffix);
    tableList.add(sb.toString());
    return tableList;
  }
}