/**     
 * @FileName: Test.java   
 * @Package:BasicJava   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月19日 上午7:20:47   
 * @version V1.0     
 */
package MQResource;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**  
 * @ClassName: Test   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月19日 上午7:20:47     
 */
public class RuntimeMXBeanTest {

    @org.junit.Test
    public void test1() {
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        String name = runtime.getName(); // format: "pid@hostname"
        try {

            int b = Integer.parseInt(name.substring(0, name.indexOf('@')));
            System.out.println(b);
            System.out.println(runtime.getLibraryPath());
            System.out.println(runtime.getBootClassPath());
            System.out.println(runtime.getStartTime());

            System.out.println(runtime.getSystemProperties());
        } catch (Exception e) {
        }
    }

    public String buildMQClientId() {
        StringBuilder sb = new StringBuilder();
//        sb.append(this.getClientIP());
//
//        sb.append("@");
//        sb.append(this.getInstanceName());

        return sb.toString();
    }
}
