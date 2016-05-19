package DistributeDataBase.rule.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DistributeDataBase.rule.ruleengine.entities.convientobjectmaker.DatabaseMapProvider;

public class SimpleListDatabaseMapProvider
  implements DatabaseMapProvider
{
  List<String> datasourceKeys = new ArrayList();

  public Map<String, Database> getDatabaseMap() {
    Map returnedMap = new HashMap();
    int index = 0;
    if (this.datasourceKeys == null) {
      return Collections.emptyMap();
    }
    for (String str : this.datasourceKeys) {
      Database db = new Database();
      db.setDataSourceKey(str);
      returnedMap.put(String.valueOf(index), db);
      index++;
    }
    return returnedMap;
  }

  public List<String> getDatasourceKeys() {
    return this.datasourceKeys;
  }

  public void setDatasourceKeys(List<String> datasourceKeys) {
    this.datasourceKeys = datasourceKeys;
  }
}