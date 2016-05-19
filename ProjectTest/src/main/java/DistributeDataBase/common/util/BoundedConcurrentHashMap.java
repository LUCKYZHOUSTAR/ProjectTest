/**
 * 
 */
package DistributeDataBase.common.util;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
* @ClassName: BoundedConcurrentHashMap 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午6:39:25 
*  
*/
public class BoundedConcurrentHashMap<K, V> extends LinkedHashMap<K, V> {
    private static final Logger logger           = LoggerFactory
                                                     .getLogger(BoundedConcurrentHashMap.class);
    private static final long   serialVersionUID = 2615986629983154259L;
    private static final int    DEFAULT_CAPACITY = 386;
    private int                 capacity;
    private final Lock          lock             = new ReentrantLock();

    public BoundedConcurrentHashMap(int capacity) {
        super(capacity, 0.75F, true);
        this.capacity = capacity;
    }

    public BoundedConcurrentHashMap() {
        this(386);
    }

    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        boolean ret = size() > this.capacity;
        if ((logger.isDebugEnabled()) && (ret)) {
            logger.debug("removeEldestEntry size: " + size() + ", capacity: " + this.capacity);
        }

        return ret;
    }

    public void clear() {
        this.lock.lock();
        try {
            super.clear();
        } finally {
            this.lock.unlock();
        }
    }

    public Object clone() {
        this.lock.lock();
        try {
            return super.clone();
        } finally {
            this.lock.unlock();
        }
    }

    public boolean equals(Object o) {
        this.lock.lock();
        try {
            return super.equals(o);
        } finally {
            this.lock.unlock();
        }
    }

    public int hashCode() {
        this.lock.lock();
        try {
            return super.hashCode();
        } finally {
            this.lock.unlock();
        }
    }

    public String toString() {
        this.lock.lock();
        try {
            return super.toString();
        } finally {
            this.lock.unlock();
        }
    }

    public boolean containsValue(Object value) {
        this.lock.lock();
        try {
            return super.containsValue(value);
        } finally {
            this.lock.unlock();
        }
    }

    public V get(Object key) {
        this.lock.lock();
        try {
            return super.get(key);
        } finally {
            this.lock.unlock();
        }
    }

    public boolean containsKey(Object key) {
        this.lock.lock();
        try {
            return super.containsKey(key);
        } finally {
            this.lock.unlock();
        }
    }

    public Set<Map.Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }

    public boolean isEmpty() {
        this.lock.lock();
        try {
            return super.isEmpty();
        } finally {
            this.lock.unlock();
        }
    }

    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    public V put(K key, V value) {
        this.lock.lock();
        try {
            return super.put(key, value);
        } finally {
            this.lock.unlock();
        }
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        this.lock.lock();
        try {
            super.putAll(m);
        } finally {
            this.lock.unlock();
        }
    }

    public V remove(Object key) {
        this.lock.lock();
        try {
            return super.remove(key);
        } finally {
            this.lock.unlock();
        }
    }

    public int size() {
        this.lock.lock();
        try {
            return super.size();
        } finally {
            this.lock.unlock();
        }
    }

    public Collection<V> values() {
        throw new UnsupportedOperationException();
    }
}