/**     
 * @FileName: ConditionTest.java   
 * @Package:com.thread   
 * @Description: TODO  
 * @author: LUCKY    
 * @date:2016年3月21日 下午7:20:51   
 * @version V1.0     
 */
package com.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

/**
 * @ClassName: ConditionTest
 * @Description: TODO
 * @author: LUCKY
 * @date:2016年3月21日 下午7:20:51
 */
public class ConditionTest {

    Lock      lock    = new ReentrantLock();
    Condition notFull = lock.newCondition();
    Condition full    = lock.newCondition();
    int       Count   = 30;

    @Test
    public void test() {
        final ConditionTest test = new ConditionTest();
        for (int i = 0; i < 10000; i++) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    test.add();
                }
            }).start();

            new Thread(new Runnable() {

                @Override
                public void run() {
                    test.sub();
                }
            }).start();

        }
    }

    /**   
     * @Title: add   
     * @Description:如果count大于10的话，则阻塞当前的线程，否则的话，就add操作
     * @param @param count  
     * @return void  
     * @throws   
     */
    public void add() {
        lock.lock();
        try {
            if (Count >=10) {
                // 阻塞当前的线程操作
                notFull.await();
            }
            Count++;
            if (Count > 0 && Count < 10) {

                //开启另外一个线程
                full.signal();
            }
            System.out.println(Count);

        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            lock.unlock();
        }
    }

    public void sub() {
        lock.lock();
        try {
            if (Count <= 0) {
                full.await();// 阻塞当前的线程
            }

            Count--;
             //如果符合预期的话
            if (Count > 0 && Count < 10) {

                notFull.signal();
            }
            System.out.println(Count);

        } catch (Exception e) {
        } finally {
            lock.unlock();
        }

    }

}
