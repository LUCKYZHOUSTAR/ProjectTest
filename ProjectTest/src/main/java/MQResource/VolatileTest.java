/**     
 * @FileName: VolatileTest.java   
 * @Package:MQResource   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月19日 上午10:14:38   
 * @version V1.0     
 */
package MQResource;

/**  
 * @ClassName: VolatileTest   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月19日 上午10:14:38     
 */
public class VolatileTest {
    public volatile static int count = 0;

    public static void inc() {
        count=count+1;
    }

    public static void main(String[] args) throws Exception {

        //同时启动1000个线程，去进行i++计算，看看实际结果

        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    VolatileTest.inc();
                }
            }).start();
            
        }

       
        Thread.sleep(5000l);
        //这里每次运行的值都有可能不同,可能为1000
        System.out.println("运行结果:Counter.count=" + VolatileTest.count);
    }
}
