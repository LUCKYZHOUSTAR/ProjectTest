/**     
 * @FileName: VolatileTest2.java   
 * @Package:MQResource   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月19日 上午10:17:10   
 * @version V1.0     
 */
package MQResource;

/**  
 * @ClassName: VolatileTest2   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月19日 上午10:17:10     
 */
public class VolatileTest2 {
    static class MyObject {
        static int mycount = 0;
    }

    public static void inc() {
        MyObject.mycount=MyObject.mycount+1;
    }

    public static void main(String[] args) {

        //同时启动1000个线程，去进行i++计算，看看实际结果

        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    inc();
                }
            }).start();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

        //这里每次运行的值都有可能不同,可能为1000
        System.out.println("运行结果:Counter.count=" + MyObject.mycount);
    }
}
