package DistributeDataBase.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.apache.log4j.Logger;

public class WeightRandom {
    private static final Logger               logger                 = Logger
                                                                         .getLogger(WeightRandom.class);
    public static final int                   DEFAULT_WEIGHT_NEW_ADD = 0;
    public static final int                   DEFAULT_WEIGHT_INIT    = 10;
    private Map<String, Integer>              cachedWeightConfig;
    private final RuntimeConfigHolder<Weight> weightHolder           = new RuntimeConfigHolder();

    private final Random                      random                 = new Random();

    public WeightRandom(Map<String, Integer> weightConfigs) {
        init(weightConfigs);
    }

    public WeightRandom(String[] keys) {
        Map weightConfigs = new HashMap(keys.length);
        for (String key : keys) {
            weightConfigs.put(key, Integer.valueOf(10));
        }
        init(weightConfigs);
    }

    private void init(Map<String, Integer> weightConfig) {
        this.cachedWeightConfig = weightConfig;
        String[] weightKeys = (String[]) weightConfig.keySet().toArray(new String[0]);
        int[] weights = new int[weightConfig.size()];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = ((Integer) weightConfig.get(weightKeys[i])).intValue();
        }
        int[] weightAreaEnds = genAreaEnds(weights);
        this.weightHolder.set(new Weight(weights, weightKeys, weightAreaEnds));
    }

    public void setWeightConfig(Map<String, Integer> weightConfig) {
        init(weightConfig);
    }

    public Map<String, Integer> getWeightConfig() {
        return this.cachedWeightConfig;
    }

    private String select(int[] areaEnds, String[] keys) {
        int sum = areaEnds[(areaEnds.length - 1)];
        if (sum == 0) {
            logger.error(new StringBuilder().append("areaEnds: ").append(intArray2String(areaEnds))
                .toString());
            return null;
        }

        int rand = this.random.nextInt(sum);
        for (int i = 0; i < areaEnds.length; i++) {
            if (rand < areaEnds[i]) {
                return keys[i];
            }
        }
        return null;
    }

    public int getWeightByKey(String key) {
        int weight = 0;
        boolean flag = false;
        Weight w = (Weight) this.weightHolder.get();
        for (int k = 0; k < w.weightKeys.length; k++) {
            if (w.weightKeys[k].equals(key)) {
                flag = true;
                weight = w.weightValues[k];
            }
        }
        if (!flag) {
            logger.error(new StringBuilder().append("数据源的标识不存在，非法的key=").append(key).toString());
        }
        return weight;
    }

    public String select(List<String> excludeKeys) {
        Weight w = (Weight) this.weightHolder.get();
        if ((excludeKeys == null) || (excludeKeys.isEmpty())) {
            return select(w.weightAreaEnds, w.weightKeys);
        }
        int[] tempWeights = (int[]) w.weightValues.clone();
        for (int k = 0; k < w.weightKeys.length; k++) {
            if (excludeKeys.contains(w.weightKeys[k])) {
                tempWeights[k] = 0;
            }
        }
        int[] tempAreaEnd = genAreaEnds(tempWeights);
        return select(tempAreaEnd, w.weightKeys);
    }

    public <T extends Throwable> List<T> retry(int times, Tryer<T> tryer) {
        List exceptions = new ArrayList(0);
        List excludeKeys = new ArrayList(0);
        for (int i = 0; i < times; i++) {
            String name = select(excludeKeys);
            Throwable e = tryer.tryOne(name);
            if (e != null) {
                exceptions.add(e);
                excludeKeys.add(name);
            } else {
                return null;
            }
        }
        return exceptions;
    }

    public <T extends Throwable> List<T> retry(Tryer<T> tryer) {
        return retry(3, tryer);
    }

    private static int[] genAreaEnds(int[] weights) {
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
            logger.debug(new StringBuilder().append("generate ").append(intArray2String(areaEnds))
                .append(" from ").append(intArray2String(weights)).toString());
        }

        if (sum == 0) {
            logger.warn(new StringBuilder().append("generate ").append(intArray2String(areaEnds))
                .append(" from ").append(intArray2String(weights)).toString());
        }

        return areaEnds;
    }

    private static String intArray2String(int[] inta) {
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

    public String getAllDbKeys() {
        StringBuilder sb = new StringBuilder();
        Weight w = (Weight) this.weightHolder.get();
        sb.append("[").append(w.weightKeys[0]);
        for (int i = 1; i < w.weightKeys.length; i++) {
            sb.append(",").append(w.weightKeys[i]);
        }
        sb.append("]");
        return sb.toString();
    }

    public static abstract interface Tryer<T extends Throwable> {
        public abstract T tryOne(String paramString);
    }

    private static class Weight {
        public final String[] weightKeys;
        public final int[]    weightValues;
        public final int[]    weightAreaEnds;

        public Weight(int[] weights, String[] weightKeys, int[] weightAreaEnds) {
            this.weightKeys = weightKeys;
            this.weightValues = weights;
            this.weightAreaEnds = weightAreaEnds;
        }
    }
}