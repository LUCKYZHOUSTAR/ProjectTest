package DistributeDataBase.client.dataSource.keyweight;

import com.alipay.zdal.common.lang.StringUtil;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.log4j.Logger;

public class ZdalDataSourceKeyWeightRumtime
{
  private static Logger logger = Logger.getLogger(ZdalDataSourceKeyWeightRumtime.class);
  private static final int DEFAULT_DATASOURCE_WEIGHT = 10;
  private Map<String, ZdalDataSourceKeyWeightRandom> keyWeightMapHolder;

  public ZdalDataSourceKeyWeightRumtime(Map<String, ZdalDataSourceKeyWeightRandom> keyWeightMapHolder)
  {
    this.keyWeightMapHolder = keyWeightMapHolder;
  }

  public static Map<String, ZdalDataSourceKeyWeightRandom> buildKeyWeightConfig(Map<String, String> keyWeightConfig, Map<String, ? extends Object> dataSourceKeyConfig)
  {
    Map map = new ConcurrentHashMap(keyWeightConfig.size());

    for (Map.Entry entry : keyWeightConfig.entrySet()) {
      String groupKey = (String)entry.getKey();
      String value = (String)entry.getValue();
      if ((StringUtil.isBlank(groupKey)) || (StringUtil.isBlank(value))) {
        logger.error("数据源key=" + groupKey + "分组权重配置信息不能为空,value=" + value);
        return null;
      }
      String[] keyWeightStr = value.split(",");
      String[] weightKeys = new String[keyWeightStr.length];
      int[] weights = new int[keyWeightStr.length];

      if (keyWeightStr.length == 1) {
        if (StringUtil.isBlank(keyWeightStr[0])) {
          logger.error("单数据源keyWeightStr[0]分组权重配置信息为空.");
          return null;
        }
        String[] keyAndWeight = keyWeightStr[0].split(":");
        if ((dataSourceKeyConfig.keySet() == null) || (!dataSourceKeyConfig.keySet().contains(keyAndWeight[0].trim())))
        {
          logger.error("数据源key=" + keyAndWeight[0] + "在数据源配置中不存在.");
          return null;
        }
        weightKeys[0] = keyAndWeight[0].trim();
        weights[0] = 10;
      } else if (keyWeightStr.length > 1)
      {
        for (int i = 0; i < keyWeightStr.length; i++) {
          if (StringUtil.isBlank(keyWeightStr[i])) {
            logger.error("多数据源keyWeightStr[" + i + "]分组权重配置信息为空.");
            return null;
          }
          String[] keyAndWeight = keyWeightStr[i].split(":");
          if (keyAndWeight.length != 2) {
            logger.error("数据源key按组配置权重错误,keyWeightStr[" + i + "]=" + keyWeightStr[i] + ".");

            return null;
          }
          String key = keyAndWeight[0];
          if ((dataSourceKeyConfig.keySet() == null) || (!dataSourceKeyConfig.keySet().contains(key)))
          {
            logger.error("数据源key=" + key + "在数据源dataSourcePool配置中不存在.");
            return null;
          }
          String weightStr = keyAndWeight[1];
          if ((StringUtil.isBlank(key)) || (StringUtil.isBlank(weightStr))) {
            logger.error("数据源key=" + key + "或其权重配置weightStr=" + weightStr + "不能为空.");
            return null;
          }
          weightKeys[i] = key.trim();
          weights[i] = Integer.parseInt(weightStr.trim());
        }
      } else {
        logger.error("该分组groupKey=" + groupKey + "中数据源的个数不对，length=" + keyWeightStr.length);
        return null;
      }

      ZdalDataSourceKeyWeightRandom TDataSourceKeyWeightRandom = new ZdalDataSourceKeyWeightRandom(weightKeys, weights);

      map.put(groupKey, TDataSourceKeyWeightRandom);
    }
    return map;
  }

  public void setKeyWeightMapHolder(Map<String, ZdalDataSourceKeyWeightRandom> keyWeightMapHolder)
  {
    this.keyWeightMapHolder = keyWeightMapHolder;
  }

  public Map<String, ZdalDataSourceKeyWeightRandom> getKeyWeightMapHolder()
  {
    return this.keyWeightMapHolder;
  }
}