package test.alrotic;


import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by liuzhao on 14-5-15.
 */
public class LRUCacheTest  {

    public static void main(String[] args) throws Exception {
        System.out.println("start...");

//        lruCache1();
        lruCache2();
//        lruCache3();
//        lruCache4();
     
        System.out.println("over...");
    }
 

 static   void lruCache1() {
        System.out.println();
        System.out.println("===========================LRU 链表实现===========================");
        LRUCache1<Integer, String> lru = new LRUCache1(5);
        lru.put(1, "11");
        lru.put(2, "11");
        lru.put(3, "11");
        lru.put(4, "11");
        lru.put(5, "11");
        System.out.println(lru.toString());
        lru.put(6, "66");
        lru.get(2);
        lru.put(7, "77");
        lru.get(4);
        System.out.println(lru.toString());
        System.out.println();
    }

/*
 * LRU是Least Recently Used 的缩写，翻译过来就是“最近最少使用”，LRU缓存就是使用这种原理实现
 * ，简单的说就是缓存一定量的数据，当超过设定的阈值时就把一些过期的数据删除掉，
 * 比如我们缓存10000条数据，当数据小于10000时可以随意添加，
 * 当超过10000时就需要把新的数据添加进来，同时要把过期数据删除，以确保我们最大缓存10000条，
 * 那怎么确定删除哪条过期数据呢，采用LRU算法实现的话就是将最老的数据删掉，废话不多说

 */
static   <T> void lruCache2() {
        System.out.println();
        System.out.println("===========================LRU LinkedHashMap(inheritance)实现===========================");
        LRUCache2<Integer, String> lru = new LRUCache2(5);
        lru.put(1, "11");
        lru.put(2, "11");
        lru.put(3, "11");
        lru.put(4, "11");
        lru.put(5, "11");
        System.out.println(lru.toString());
        lru.put(6, "66");
        System.out.println(lru.toString());
        lru.get(2);
        lru.put(7, "77");
        System.out.println(lru.toString());
        lru.get(4);
        System.out.println(lru.toString());
        System.out.println();
    }

  static  void lruCache3() {
        System.out.println();
        System.out.println("===========================LRU LinkedHashMap(delegation)实现===========================");
        LRUCache3<Integer, String> lru = new LRUCache3(5);
        lru.put(1, "11");
        lru.put(2, "11");
        lru.put(3, "11");
        lru.put(4, "11");
        lru.put(5, "11");
        System.out.println(lru.toString());
        lru.put(6, "66");
        lru.get(2);
        lru.put(7, "77");
        lru.get(4);
        System.out.println(lru.toString());
        System.out.println();
    }

  static  void lruCache4() {
        System.out.println();
        System.out.println("===========================FIFO LinkedHashMap默认实现===========================");
        final int cacheSize = 5;
        LinkedHashMap<Integer, String> lru = new LinkedHashMap<Integer, String>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, String> eldest) {
                return size() > cacheSize;
            }
        };
        lru.put(1, "11");
        lru.put(2, "11");
        lru.put(3, "11");
        lru.put(4, "11");
        lru.put(5, "11");
        System.out.println(lru.toString());
        lru.put(6, "66");
        lru.get(2);
        lru.put(7, "77");
        lru.get(4);
        System.out.println(lru.toString());
        System.out.println();
    }
}