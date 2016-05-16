/**
 * 
 */
package com.thread.daemon;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/** 
* @ClassName: DaemonTest 
* @Description: 
* @author LUCKY
* @date 2016年5月15日 下午5:44:16 
*  
*/
public class DaemonTest {
    public static void main(String[] args) throws IllegalArgumentException, InterruptedException {
        System.out.println("Start time is : " + new Date());
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    //一直沉睡者
                    TimeUnit.DAYS.sleep(Long.MAX_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Daemon-T");
        //        因为是Daemon线程，当主线程执行15S退出之后，进程退出
//        t.setDaemon(false);
                t.setDaemon(true);
        t.start();
        TimeUnit.SECONDS.sleep(5);
        System.out.println("End time is : " + new Date());
    }
}
