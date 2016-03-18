package com.Concurrent.Exchanger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

/**
 * Main class of the example
 *
 */
public class Main {

	/**
	 * Main method of the example
	 * @param args
	 */
	
	
	/*
	 *  首先是在明细表之外再建立一个数据统计（xxx_statistics）表，
	 *  考虑到目前数据库的压力以及公司内部质管流控等因素，暂没有分库存放，
	 *  仍旧与原明细表放在同一个库。再设置一个定时任务于每日凌晨对明细表进行查询、过滤、统计、排序等
	 *  操作，把统计结果插入到统计表中。然后对外暴露统计接口查询统计报表
	 *  。现在的设计与原来的实现相比，虽然牺牲了统计表所占用的少量额外的存储空间（
	 *  每日新增的十来万条业务办理明细记录经过处理最终会变成几百条统计表的记录），
	 *  但是却能把select、count这样耗时的数据统计操作放到凌晨时段执行以避开白天的业务办理高峰，
	 *  分表处理能够大幅降低对生产业务明细表的性能影响，而对外提供的统计接口的查询速度也将
	 *  得到几个数量级的提升。
	 *  当然，还有一个缺点是，不能实时提供当天的统计数据，不过这也是双方可以接受的。
	 */
	public static void main(String[] args) {
		
		// Creates two buffers
		List<String> buffer1=new ArrayList<>();
		List<String> buffer2=new ArrayList<>();
		
		// Creates the exchanger
		Exchanger<List<String>> exchanger=new Exchanger<>();
		
		// Creates the producer
		Producer producer=new Producer(buffer1, exchanger);
		// Creates the consumer
		Consumer consumer=new Consumer(buffer2, exchanger);
		
		// Creates and starts the threads
		Thread threadProducer=new Thread(producer);
		Thread threadConsumer=new Thread(consumer);
		
		threadProducer.start();
		threadConsumer.start();

	}

}
