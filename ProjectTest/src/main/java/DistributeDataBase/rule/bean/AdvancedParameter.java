/**
 * 
 */
package DistributeDataBase.rule.bean;

/** 
* @ClassName: AdvancedParameter 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午7:33:51 
*  
*/
public class AdvancedParameter {
    public String        key;
    public Comparable<?> atomicIncreateValue;
    public Integer       cumulativeTimes;
    public boolean       needMergeValueInCloseInterval;

    public Integer getCumulativeTimes() {
        return this.cumulativeTimes;
    }

    public void setCumulativeTimes(Integer cumulativeTimes) {
        this.cumulativeTimes = cumulativeTimes;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key.toLowerCase();
    }

    public Comparable<?> getAtomicIncreateValue() {
        return this.atomicIncreateValue;
    }

    public void setAtomicIncreateValue(Comparable<?> atomicIncreateValue) {
        this.atomicIncreateValue = atomicIncreateValue;
    }

    public boolean isNeedMergeValueInCloseInterval() {
        return this.needMergeValueInCloseInterval;
    }

    public String toString() {
        return "AdvancedParameter [atomicIncreateValue=" + this.atomicIncreateValue
               + ", cumulativeTimes=" + this.cumulativeTimes + ", key=" + this.key
               + ", needMergeValueInCloseInterval=" + this.needMergeValueInCloseInterval + "]";
    }
}