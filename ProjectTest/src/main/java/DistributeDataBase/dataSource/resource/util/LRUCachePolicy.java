package DistributeDataBase.dataSource.resource.util;

import java.util.HashMap;

public class LRUCachePolicy
  implements CachePolicy
{
  protected HashMap m_map;
  protected LRUList m_list;
  protected int m_maxCapacity;
  protected int m_minCapacity;

  public LRUCachePolicy()
  {
  }

  public LRUCachePolicy(int min, int max)
  {
    if ((min < 2) || (min > max)) {
      throw new IllegalArgumentException("Illegal cache capacities");
    }
    this.m_minCapacity = min;
    this.m_maxCapacity = max;
  }

  public void create()
  {
    this.m_map = new HashMap();
    this.m_list = createList();
    this.m_list.m_maxCapacity = this.m_maxCapacity;
    this.m_list.m_minCapacity = this.m_minCapacity;
    this.m_list.m_capacity = this.m_maxCapacity;
  }

  public void start()
  {
  }

  public void stop()
  {
    if (this.m_list != null)
      flush();
  }

  public void destroy()
  {
    if (this.m_map != null)
      this.m_map.clear();
    if (this.m_list != null)
      this.m_list.clear();
  }

  public Object get(Object key) {
    if (key == null) {
      throw new IllegalArgumentException("Requesting an object using a null key");
    }

    LRUCacheEntry value = (LRUCacheEntry)this.m_map.get(key);
    if (value != null) {
      this.m_list.promote(value);
      return value.m_object;
    }
    cacheMiss();
    return null;
  }

  public Object peek(Object key)
  {
    if (key == null) {
      throw new IllegalArgumentException("Requesting an object using a null key");
    }

    LRUCacheEntry value = (LRUCacheEntry)this.m_map.get(key);
    if (value == null) {
      return null;
    }
    return value.m_object;
  }

  public void insert(Object key, Object o)
  {
    if (o == null) {
      throw new IllegalArgumentException("Cannot insert a null object in the cache");
    }
    if (key == null) {
      throw new IllegalArgumentException("Cannot insert an object in the cache with null key");
    }
    if (this.m_map.containsKey(key)) {
      throw new IllegalStateException("Attempt to put in the cache an object that is already there");
    }

    this.m_list.demote();
    LRUCacheEntry entry = createCacheEntry(key, o);
    this.m_map.put(key, entry);
    this.m_list.promote(entry);
  }

  public void remove(Object key) {
    if (key == null) {
      throw new IllegalArgumentException("Removing an object using a null key");
    }

    Object value = this.m_map.remove(key);
    if (value != null)
      this.m_list.remove((LRUCacheEntry)value);
  }

  public void flush()
  {
    LRUCacheEntry entry = null;
    while ((entry = this.m_list.m_tail) != null)
      ageOut(entry);
  }

  public int size()
  {
    return this.m_list.m_count;
  }

  protected LRUList createList()
  {
    return new LRUList();
  }

  protected void ageOut(LRUCacheEntry entry)
  {
    remove(entry.m_key);
  }

  protected void cacheMiss()
  {
  }

  protected LRUCacheEntry createCacheEntry(Object key, Object value)
  {
    return new LRUCacheEntry(key, value);
  }

  public class LRUCacheEntry
  {
    public LRUCacheEntry m_next;
    public LRUCacheEntry m_prev;
    public Object m_key;
    public Object m_object;
    public long m_time;

    protected LRUCacheEntry(Object key, Object object)
    {
      this.m_key = key;
      this.m_object = object;
      this.m_next = null;
      this.m_prev = null;
      this.m_time = 0L;
    }

    public String toString() {
      return new StringBuilder().append("key: ").append(this.m_key).append(", object: ").append(this.m_object == null ? "null" : Integer.toHexString(this.m_object.hashCode())).append(", entry: ").append(Integer.toHexString(super.hashCode())).toString();
    }
  }

  public class LRUList
  {
    public int m_maxCapacity;
    public int m_minCapacity;
    public int m_capacity;
    public int m_count;
    public LRUCachePolicy.LRUCacheEntry m_head;
    public LRUCachePolicy.LRUCacheEntry m_tail;
    public int m_cacheMiss;

    protected LRUList()
    {
      this.m_head = null;
      this.m_tail = null;
      this.m_count = 0;
    }

    protected void promote(LRUCachePolicy.LRUCacheEntry entry)
    {
      if (entry == null) {
        throw new IllegalArgumentException("Trying to promote a null object");
      }
      if (this.m_capacity < 1) {
        throw new IllegalStateException("Can't work with capacity < 1");
      }

      entryPromotion(entry);

      entry.m_time = System.currentTimeMillis();
      if (entry.m_prev == null) {
        if (entry.m_next == null)
        {
          if (this.m_count == 0)
          {
            this.m_head = entry;
            this.m_tail = entry;
            this.m_count += 1;
            entryAdded(entry);
          } else if ((this.m_count != 1) || (this.m_head != entry))
          {
            if (this.m_count < this.m_capacity) {
              entry.m_prev = null;
              entry.m_next = this.m_head;
              this.m_head.m_prev = entry;
              this.m_head = entry;
              this.m_count += 1;
              entryAdded(entry);
            } else if (this.m_count < this.m_maxCapacity) {
              entry.m_prev = null;
              entry.m_next = this.m_head;
              this.m_head.m_prev = entry;
              this.m_head = entry;
              this.m_count += 1;
              int oldCapacity = this.m_capacity;
              this.m_capacity += 1;
              entryAdded(entry);
              capacityChanged(oldCapacity);
            } else {
              throw new IllegalStateException("Attempt to put a new cache entry on a full cache");
            }
          }
        }

      }
      else if (entry.m_next == null)
      {
        LRUCachePolicy.LRUCacheEntry beforeLast = entry.m_prev;
        beforeLast.m_next = null;
        entry.m_prev = null;
        entry.m_next = this.m_head;
        this.m_head.m_prev = entry;
        this.m_head = entry;
        this.m_tail = beforeLast;
      }
      else {
        LRUCachePolicy.LRUCacheEntry previous = entry.m_prev;
        previous.m_next = entry.m_next;
        entry.m_next.m_prev = previous;
        entry.m_prev = null;
        entry.m_next = this.m_head;
        this.m_head.m_prev = entry;
        this.m_head = entry;
      }
    }

    protected void demote()
    {
      if (this.m_capacity < 1) {
        throw new IllegalStateException("Can't work with capacity < 1");
      }

      if ((this.m_capacity <= this.m_maxCapacity) && (this.m_count > this.m_maxCapacity)) {
        throw new IllegalStateException("Cache list entries number (" + this.m_count + ") > than the maximum allowed (" + this.m_maxCapacity + ")");
      }

      if (this.m_count >= this.m_maxCapacity) {
        LRUCachePolicy.LRUCacheEntry entry = this.m_tail;

        LRUCachePolicy.this.ageOut(entry);
      }
    }

    protected void remove(LRUCachePolicy.LRUCacheEntry entry)
    {
      if (entry == null) {
        throw new IllegalArgumentException("Cannot remove a null entry from the cache");
      }
      if (this.m_count < 1) {
        throw new IllegalStateException("Trying to remove an entry from an empty cache");
      }

      entry.m_key = (entry.m_object = null);
      if (this.m_count == 1) {
        this.m_head = (this.m_tail = null);
      }
      else if (entry.m_prev == null)
      {
        this.m_head = entry.m_next;
        this.m_head.m_prev = null;
        entry.m_next = null;
      } else if (entry.m_next == null)
      {
        this.m_tail = entry.m_prev;
        this.m_tail.m_next = null;
        entry.m_prev = null;
      }
      else {
        entry.m_next.m_prev = entry.m_prev;
        entry.m_prev.m_next = entry.m_next;
        entry.m_prev = null;
        entry.m_next = null;
      }

      this.m_count -= 1;
      entryRemoved(entry);
    }

    protected void entryPromotion(LRUCachePolicy.LRUCacheEntry entry)
    {
    }

    protected void entryAdded(LRUCachePolicy.LRUCacheEntry entry)
    {
    }

    protected void entryRemoved(LRUCachePolicy.LRUCacheEntry entry)
    {
    }

    protected void capacityChanged(int oldCapacity)
    {
    }

    protected void clear()
    {
      LRUCachePolicy.LRUCacheEntry entry = this.m_head;
      this.m_head = null;
      this.m_tail = null;
      this.m_count = 0;
      for (; entry != null; entry = entry.m_next)
        entryRemoved(entry);
    }

    public String toString() {
      String s = Integer.toHexString(super.hashCode());
      s = s + " size: " + this.m_count;
      for (LRUCachePolicy.LRUCacheEntry entry = this.m_head; entry != null; entry = entry.m_next) {
        s = s + "\n" + entry;
      }
      return s;
    }
  }
}