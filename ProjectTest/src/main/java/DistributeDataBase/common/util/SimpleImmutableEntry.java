/**
 * 
 */
package DistributeDataBase.common.util;

import java.io.Serializable;
import java.util.Map;

/** 
* @ClassName: SimpleImmutableEntry 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午6:33:45 
*  
*/
public class SimpleImmutableEntry<K, V> implements Map.Entry<K, V>, Serializable {
    private static final long serialVersionUID = 7138329143949025153L;
    private final K           key;
    private final V           value;

    public SimpleImmutableEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public SimpleImmutableEntry(Map.Entry<? extends K, ? extends V> entry) {
        this.key = entry.getKey();
        this.value = entry.getValue();
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }

    public V setValue(V value) {
        throw new UnsupportedOperationException();
    }

    public boolean equals(Object o) {
        if (!(o instanceof Map.Entry))
            return false;
        Map.Entry e = (Map.Entry) o;
        return (eq(this.key, e.getKey())) && (eq(this.value, e.getValue()));
    }

    public int hashCode() {
        return (this.key == null ? 0 : this.key.hashCode())
               ^ (this.value == null ? 0 : this.value.hashCode());
    }

    public String toString() {
        return this.key + "=" + this.value;
    }

    private static boolean eq(Object o1, Object o2) {
        return o1 == null ? false : o2 == null ? true : o1.equals(o2);
    }
}
