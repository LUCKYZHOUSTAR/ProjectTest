/**
 * 
 */
package DistributeDataBase.common.util;

import java.util.concurrent.ConcurrentMap;

/** 
* @ClassName: ConcurrentNavigableMap 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午6:37:52 
*  
*/
public abstract interface ConcurrentNavigableMap<K, V> extends ConcurrentMap<K, V>,
                                                       NavigableMap<K, V> {
    public abstract ConcurrentNavigableMap<K, V> subMap(K paramK1, boolean paramBoolean1,
                                                        K paramK2, boolean paramBoolean2);

    public abstract ConcurrentNavigableMap<K, V> headMap(K paramK, boolean paramBoolean);

    public abstract ConcurrentNavigableMap<K, V> tailMap(K paramK, boolean paramBoolean);

    public abstract ConcurrentNavigableMap<K, V> subMap(K paramK1, K paramK2);

    public abstract ConcurrentNavigableMap<K, V> headMap(K paramK);

    public abstract ConcurrentNavigableMap<K, V> tailMap(K paramK);

    public abstract ConcurrentNavigableMap<K, V> descendingMap();

    public abstract NavigableSet<K> navigableKeySet();

    public abstract NavigableSet<K> keySet();

    public abstract NavigableSet<K> descendingKeySet();
}
