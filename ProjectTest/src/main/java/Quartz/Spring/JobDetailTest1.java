/**
 * 
 */
package Quartz.Spring;

/**
 * @author LUCKY
 *
 */
public class JobDetailTest1 {

    
    //考虑到并发的情况，需要加锁
    public synchronized void run() {
        System.out.println("开启job任务操作");
    }
    
    public void fly(){
        System.out.println("开始飞起来了");
    }
}
