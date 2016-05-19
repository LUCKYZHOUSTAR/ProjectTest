package DistributeDataBase.client.dataSource.keyweight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import DistributeDataBase.common.RuntimeConfigHolder;

public class ZdalDataSourceKeyWeightRandom
{
  private static final Logger logger = Logger.getLogger(ZdalDataSourceKeyWeightRandom.class);
  private final int dataSourceNumberInGroup;
  private final Map<String, Integer> cachedWeightConfig = new HashMap();

  private final RuntimeConfigHolder<Weight> weightHolder = new RuntimeConfigHolder();

  private final Random random = new Random();

  public ZdalDataSourceKeyWeightRandom(String[] weightKeys, int[] weights)
  {
    for (int i = 0; i < weightKeys.length; i++) {
      this.cachedWeightConfig.put(weightKeys[i], Integer.valueOf(weights[i]));
    }
    int[] weightAreaEnds = genAreaEnds(weights);
    this.dataSourceNumberInGroup = weightKeys.length;
    this.weightHolder.set(new Weight(weights, weightKeys, weightAreaEnds));
  }

  public Map<String, Integer> getWeightConfig() {
    return this.cachedWeightConfig;
  }

  public int select()
  {
    Weight w = (Weight)this.weightHolder.get();
    int[] areaEnds = w.weightAreaEnds;
    int sum = areaEnds[(areaEnds.length - 1)];
    if (sum == 0) {
      logger.error(new StringBuilder().append("该组数据源权重全部为0，areaEnds: ").append(intArray2String(areaEnds)).toString());
      return -1;
    }
    int rand = this.random.nextInt(sum);
    for (int i = 0; i < areaEnds.length; i++) {
      if (rand < areaEnds[i]) {
        return i;
      }
    }
    logger.error(new StringBuilder().append("Choose the dataSource in the areaEnds failed, the rand=").append(rand).append(", areaEnds:").append(intArray2String(areaEnds)).toString());

    return -1;
  }

  private static int[] genAreaEnds(int[] weights)
  {
    if (weights == null) {
      return null;
    }
    int[] areaEnds = new int[weights.length];
    int sum = 0;
    for (int i = 0; i < weights.length; i++) {
      sum += weights[i];
      areaEnds[i] = sum;
    }
    if (logger.isDebugEnabled()) {
      logger.debug(new StringBuilder().append("generate areaEnds").append(intArray2String(areaEnds)).append(" from weights").append(intArray2String(weights)).toString());
    }

    if (sum == 0) {
      logger.warn(new StringBuilder().append("generate areaEnds").append(intArray2String(areaEnds)).append(" from weights").append(intArray2String(weights)).toString());
    }

    return areaEnds;
  }

  private static String intArray2String(int[] inta)
  {
    if (inta == null)
      return "null";
    if (inta.length == 0) {
      return "[]";
    }
    StringBuilder sb = new StringBuilder();
    sb.append("[").append(inta[0]);
    for (int i = 1; i < inta.length; i++) {
      sb.append(", ").append(inta[i]);
    }
    sb.append("]");
    return sb.toString();
  }

  public String getAllDbKeys()
  {
    StringBuilder sb = new StringBuilder();
    Weight w = (Weight)this.weightHolder.get();
    sb.append("[").append(w.weightKeys[0]);
    for (int i = 1; i < w.weightKeys.length; i++) {
      sb.append(",").append(w.weightKeys[i]);
    }
    sb.append("]");
    return sb.toString();
  }

  public int getRandomDBIndexByWeight(List<Integer> excludeNums)
  {
    Weight w = (Weight)this.weightHolder.get();
    int[] weights = w.weightValues;
    List dbIndexes = new ArrayList();
    for (int i = 0; i < weights.length; i++) {
      if ((weights[i] > 0) && (!excludeNums.contains(Integer.valueOf(i)))) {
        dbIndexes.add(Integer.valueOf(i));
      }
    }
    int size = dbIndexes.size();
    if (size <= 0) {
      throw new IllegalArgumentException("没有可用的数据源了，权重全部为0！");
    }
    int rand = this.random.nextInt(size);
    return ((Integer)dbIndexes.get(rand)).intValue();
  }

  public boolean isDataBaseAvailable(int dbNumber)
  {
    Weight w = (Weight)this.weightHolder.get();
    int[] weights = w.weightValues;
    if (weights[dbNumber] > 0) {
      return true;
    }
    return false;
  }

  public List<Integer> getNotAvailableDBIndexes()
  {
    Weight w = (Weight)this.weightHolder.get();
    int[] weights = w.weightValues;
    List dbIndexes = new ArrayList();
    for (int i = 0; i < weights.length; i++) {
      if (weights[i] <= 0) {
        dbIndexes.add(Integer.valueOf(i));
      }
    }
    return dbIndexes;
  }

  public List<Integer> getAvailableDBIndexes()
  {
    Weight w = (Weight)this.weightHolder.get();
    int[] weights = w.weightValues;
    List dbIndexes = new ArrayList();
    for (int i = 0; i < weights.length; i++) {
      if (weights[i] > 0) {
        dbIndexes.add(Integer.valueOf(i));
      }
    }
    return dbIndexes;
  }

  public String getDBKeyByNumber(int number)
  {
    Weight w = (Weight)this.weightHolder.get();
    if (number >= w.weightKeys.length) {
      throw new IllegalArgumentException(new StringBuilder().append("The db number is out of scope, number= ").append(number).append(",the largest is ").append(w.weightKeys.length).toString());
    }

    return w.weightKeys[number];
  }

  public String[] getDBWeightKeys() {
    Weight w = (Weight)this.weightHolder.get();
    return w.weightKeys;
  }

  public int[] getDBWeightValues() {
    Weight w = (Weight)this.weightHolder.get();
    return w.weightValues;
  }

  public int getDBWeightByNumber(int number)
  {
    Weight w = (Weight)this.weightHolder.get();
    if (number >= w.weightKeys.length) {
      throw new IllegalArgumentException(new StringBuilder().append("The db number is out of scope, number= ").append(number).append(",the largest is ").append(w.weightKeys.length).toString());
    }

    return w.weightValues[number];
  }

  public String[] getDBKeysArray()
  {
    String[] keys = ((Weight)this.weightHolder.get()).weightKeys;
    return keys;
  }

  public int getDataSourceNumberInGroup() {
    return this.dataSourceNumberInGroup;
  }

  private static class Weight
  {
    public final String[] weightKeys;
    public final int[] weightValues;
    public final int[] weightAreaEnds;

    public Weight(int[] weights, String[] weightKeys, int[] weightAreaEnds)
    {
      this.weightKeys = weightKeys;
      this.weightValues = weights;
      this.weightAreaEnds = weightAreaEnds;
    }
  }
}