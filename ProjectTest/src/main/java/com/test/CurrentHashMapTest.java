/**     
 * @FileName: CurrentHashMap.java   
 * @Package:com.test   
 * @Description: TODO  
 * @author: LUCKY    
 * @date:2016年1月26日 下午8:05:04   
 * @version V1.0     
 */
package com.test;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/**  
 * @ClassName: CurrentHashMap   
 * @Description: CurrentHashMapTest线程安全类  
 * @author: LUCKY  
 * @date:2016年1月26日 下午8:05:04     
 */
/*
 * hashtable是做了同步的，hashmap未考虑同步
 * 解决方案有Hashtable或者 Collections.synchronizedMap(hashMap)，这两种方式基本都是对整个hash表结构做锁定操作的
 * ，这样在锁表的期间， 别的线程就需要等待了，无疑性能不高。
 * 	ConcurrentMap  主要的子类是ConcurrentHashMap。
	原理：一个ConcurrentHashMap 由多个segment 组成，每个segment 包含一个Entity 的数组。这里比HashMap 多了一个segment 类。该类继承了ReentrantLock 类，所以本身是一个锁。当多线程对ConcurrentHashMap 操作时，不是完全锁住map， 而是锁住相应的segment 。这样提高了并发效率。
	：当遍历map 中的元素时，需要获取所有的segment 的锁，使用遍历时慢。锁的增多，占用了系统的资源。使得对整个集合进行操作的一些方法（例如 size() 或 isEmpty() ）的实现更加困难，因为这些方法要求一次获得许多的锁，并且还存在返回不正确的结果的风险。

 */
public class CurrentHashMapTest {

	public static void main(String[] args) {
		ConcurrentHashMap<String, String> chm=new ConcurrentHashMap<>();
		
		chm.put("张三", "李四");
		System.out.println(chm.containsKey("张三"));
		System.out.println(chm.containsValue("李四"));
		System.out.println(chm.get("张三"));
		chm.put("王五", "赵刘");
		Enumeration<String> ele=chm.keys();
		
		while (ele.hasMoreElements()) {
			System.out.println(ele.nextElement());
			
		}
		
		
		//使用iterator进行迭代的操作
		Iterator<Entry<String, String>> iter=chm.entrySet().iterator();
		while (iter.hasNext()) {
			System.out.println( iter.next().getValue());	
		}
		//使用Entry进行迭代
		Set<Entry<String, String>>  set=chm.entrySet();
		for(Entry<String, String> entry:set){
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());
		}
	}
}
