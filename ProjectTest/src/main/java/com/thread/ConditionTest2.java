/**     
 * @FileName: ConditionTest2.java   
 * @Package:com.thread   
 * @Description: TODO  
 * @author: LUCKY    
 * @date:2016年3月22日 上午10:24:46   
 * @version V1.0     
 */
package com.thread;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**  
 * @ClassName: ConditionTest2   
 * @Description: TODO  
 * @author: LUCKY  
 * @date:2016年3月22日 上午10:24:46     
 */
public class ConditionTest2 {
    Lock      lock    = new ReentrantLock();
    Condition notFull = lock.newCondition();
    Condition full    = lock.newCondition();
    int       Count   = 30;

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        final ConditionTest2 test = new ConditionTest2();

        for (int i = 0; i < 100; i++) {//
            new Thread(new Runnable() {

                @Override
                public void run() {
                    test.add();
                }
            }).start();

        }
        for (int i = 0; i < 100; i++) {
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
            if (Count > 10) {
                // 阻塞当前的线程操作
                notFull.await();
            } else {
                Count++;
                System.out.println(Count);
                full.signal();
            }

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
            } else {
                Count--;
                System.out.println(Count);
                notFull.signal();
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            lock.unlock();
        }

    }
}
