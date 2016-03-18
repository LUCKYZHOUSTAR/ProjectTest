/**     
 * @FileName: PerformanceTestTool.java   
 * @Package:com.test   
 * @Description: TODO  
 * @author: LUCKY    
 * @date:2016年1月26日 下午6:42:41   
 * @version V1.0     
 */
package com.test;

import java.util.concurrent.CountDownLatch;

/**
 * @ClassName: PerformanceTestTool
 * @Description: 闭锁的概念
 * @author: LUCKY
 * @date:2016年1月26日 下午6:42:41
 */
public class PerformanceTestTool {
	public long timecost(final int times, final Runnable task)
			throws InterruptedException {
		if (times <= 0)
			throw new IllegalArgumentException();
		final CountDownLatch startLatch = new CountDownLatch(1);
		final CountDownLatch overLatch = new CountDownLatch(times);
		for (int i = 0; i < times; i++) {
			new Thread(new Runnable() {
				public void run() {
					try {
						startLatch.await();
						//
						task.run();
					} catch (InterruptedException ex) {
						Thread.currentThread().interrupt();
					} finally {
						overLatch.countDown();
					}
				}
			}).start();
		}
		//
		long start = System.nanoTime();
		startLatch.countDown();
		overLatch.await();
		return System.nanoTime() - start;
	}
}
