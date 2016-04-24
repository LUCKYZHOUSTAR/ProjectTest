package com.Concurrent.ThreadPool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class ThreadPoolExecutorTest {

    @Test
    public void Test() {
        BlockingQueue<Runnable> blockingqueue = new LinkedBlockingDeque<>(300);
        //线程数目还没有达到1个的时候，则会创建线程
        //如果达到1个线程的话，则会把创建的线程方知道缓存队列里面
        //如果队列满的话，则会在此创建新的线程
        //如果大于max线程数目的话，则会采取抛弃的策略来应对
        /** 
         * Param:  
         * corePoolSize - 池中所保存的线程数，包括空闲线程。  
         * maximumPoolSize - 池中允许的最大线程数(采用LinkedBlockingQueue时没有作用)。  
         * keepAliveTime -当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间，线程池维护线程所允许的空闲时间。  
         * unit - keepAliveTime参数的时间单位，线程池维护线程所允许的空闲时间的单位:秒 。  
         * workQueue - 执行前用于保持任务的队列（缓冲队列）。此队列仅保持由execute 方法提交的 Runnable 任务。  
         * RejectedExecutionHandler -线程池对拒绝任务的处理策略(重试添加当前的任务，自动重复调用execute()方法) 
         */
        ExecutorService executor = new ThreadPoolExecutor(1, 5, 5000, TimeUnit.SECONDS,
            blockingqueue);
        //        executor.submit(new threadTest());
        for (int i = 0; i < 100; i++) {
            executor.submit(new threadTest());
        }
        for (int i = 0; i < 100; i++) {
            executor.submit(new threadTest());
        }
    }
}

class threadTest implements Runnable {

    @Override
    public void run() {

        System.out.println(Thread.currentThread().getName() + "开始执行了");

    }

}