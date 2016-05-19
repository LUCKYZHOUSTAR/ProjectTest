package DistributeDataBase.dataSource.resource.util.collection;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WeakValueHashMap extends AbstractMap
  implements Map
{
  private final Map hash;
  private final ReferenceQueue queue = new ReferenceQueue();

  public Set entrySet()
  {
    processQueue();
    return this.hash.entrySet();
  }

  private void processQueue()
  {
    WeakValueRef ref;
    while ((ref = (WeakValueRef)this.queue.poll()) != null)
      if (ref == (WeakValueRef)this.hash.get(ref.key))
      {
        this.hash.remove(ref.key);
      }
  }

  public WeakValueHashMap(int initialCapacity, float loadFactor)
  {
    this.hash = new HashMap(initialCapacity, loadFactor);
  }

  public WeakValueHashMap(int initialCapacity)
  {
    this.hash = new HashMap(initialCapacity);
  }

  public WeakValueHashMap()
  {
    this.hash = new HashMap();
  }

  public WeakValueHashMap(Map t)
  {
    this(Math.max(2 * t.size(), 11), 0.75F);
    putAll(t);
  }

  public int size()
  {
    processQueue();
    return this.hash.size();
  }

  public boolean isEmpty()
  {
    processQueue();
    return this.hash.isEmpty();
  }

  public boolean containsKey(Object key)
  {
    processQueue();
    return this.hash.containsKey(key);
  }

  public Object get(Object key)
  {
    processQueue();
    WeakReference ref = (WeakReference)this.hash.get(key);
    if (ref != null)
      return ref.get();
    return null;
  }

  public Object put(Object key, Object value)
  {
    processQueue();
    Object rtn = this.hash.put(key, WeakValueRef.create(key, value, this.queue));
    if (rtn != null)
      rtn = ((WeakReference)rtn).get();
    return rtn;
  }

  public Object remove(Object key)
  {
    processQueue();
    return this.hash.remove(key);
  }

  public void clear()
  {
    processQueue();
    this.hash.clear();
  }

  private static class WeakValueRef extends WeakReference
  {
    public Object key;

    private WeakValueRef(Object key, Object val, ReferenceQueue q)
    {
      super(q);
      this.key = key;
    }

    private static WeakValueRef create(Object key, Object val, ReferenceQueue q) {
      if (val == null) {
        return null;
      }
      return new WeakValueRef(key, val, q);
    }
  }
}