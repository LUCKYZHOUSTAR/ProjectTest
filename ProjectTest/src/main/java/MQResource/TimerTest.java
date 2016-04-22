/**     
 * @FileName: TimerTest.java   
 * @Package:MQResource   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月19日 上午8:05:30   
 * @version V1.0     
 */
package MQResource;

import java.util.Timer;
import java.util.TimerTask;

import org.junit.Test;
import org.omg.CORBA.PRIVATE_MEMBER;

/**  
 * @ClassName: TimerTest   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月19日 上午8:05:30     
 */
public class TimerTest {

    public void test() {
       
        // true 说明这个timer以daemon方式运行（优先级低，  
        // 程序结束timer也自动结束），注意，javax.swing  
        // 包中也有一个Timer类，如果import中用到swing包，  
        // 要注意名字的冲突。  
        Timer timer = new Timer(true);
    }

    @Test
    public void Test2() throws Exception {
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                System.out.println("定期执行的任务在这里");
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 1000, 2000);
        //        Thread.sleep(100000l);
    }

    public static void main(String[] args) {
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                System.out.println("定期执行的任务在这里");
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 1000, 2000);
    }
    /*
     * //以下是几种调度task的方法：     
    timer.schedule(task, time);  
    // time为Date类型：在指定时间执行一次。  
     
    timer.schedule(task, firstTime, period);  
    // firstTime为Date类型,period为long  
    // 从firstTime时刻开始，每隔period毫秒执行一次。  
     
    timer.schedule(task, delay)  
    // delay 为long类型：从现在起过delay毫秒执行一次  
     
    timer.schedule(task, delay, period)  
    // delay为long,period为long：从现在起过delay毫秒以后，每隔period  
    // 毫秒执行一次。 

     */

}
