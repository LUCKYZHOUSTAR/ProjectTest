package DistributeDataBase.rule.bean;

import java.util.ArrayList;
import java.util.List;

public class SimpleTableTwoColumnsMapProvider extends SimpleTableMapProvider
{
  int from2 = 0;
  int to2 = 11;
  int step2 = 1;
  String padding2;
  String tableFactor2;
  int width2 = 2;

  protected List<String> getSuffixList(int from, int to, int width, int step, String tableFactor, String padding)
  {
    if (this.padding2 == null) {
      this.padding2 = padding;
    }
    if (this.tableFactor2 == null) {
      this.tableFactor2 = tableFactor;
    }

    if ((from == -1) || (to == -1)) {
      throw new IllegalArgumentException("from,to must be spec!");
    }
    int length = (to - from + 1) * (this.to2 - this.from2 + 1);
    List tableList = new ArrayList(length);
    StringBuilder sb = new StringBuilder();
    sb.append(tableFactor);
    sb.append(padding);

    for (int i = from; i <= to; i += step) {
      String suffix = getSuffixInit(width, i);
      for (int j = this.from2; j <= this.to2; j += this.step2) {
        StringBuilder singleTableBuilder = new StringBuilder(sb.toString());
        String suffix2 = getSuffixInit(this.width2, j);
        singleTableBuilder.append(suffix).append(this.padding2).append(suffix2);
        tableList.add(singleTableBuilder.toString());
      }
    }
    return tableList;
  }

  public int getFrom2() {
    return this.from2;
  }

  public void setFrom2(int from2) {
    this.from2 = from2;
  }

  public int getTo2() {
    return this.to2;
  }

  public void setTo2(int to2) {
    this.to2 = to2;
  }

  public int getStep2() {
    return this.step2;
  }

  public void setStep2(int step2) {
    this.step2 = step2;
  }

  public String getPadding2() {
    return this.padding2;
  }

  public void setPadding2(String padding2) {
    this.padding2 = padding2;
  }

  public String getTableFactor2() {
    return this.tableFactor2;
  }

  public void setTableFactor2(String tableFactor2) {
    this.tableFactor2 = tableFactor2;
  }

  public int getWidth2() {
    return this.width2;
  }

  public void setWidth2(int width2) {
    this.width2 = width2;
  }
}