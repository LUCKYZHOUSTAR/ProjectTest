/**     
 * @FileName: InetAddress.java   
 * @Package:network.InteNet   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年3月28日 上午9:46:08   
 * @version V1.0     
 */
package network.InteNet;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.NetworkInterface;
import java.util.Enumeration;

import org.junit.Test;

/**  
 * @ClassName: InetAddress   
 * @Description: 一个HostName可以有多个IP地址
 * 同样一个IP地址也可以有多个HostName
 * @author: LUCKY  
 * @date:2016年3月28日 上午9:46:08     
 */
public class InetAddressTest {

    public static void main(String[] args) throws Exception {
        InetAddress address = InetAddress.getByName("www.baidu.com");
        //DNS服务器也会进行本地缓存
        System.out.println(address);
        System.out.println(address.getHostAddress());
        System.out.println(address.getHostName());

    }

    /**   
     * @Title: getAllName   
     * @Description: 一个域名下面可能会有多个机器信息
     * @param @throws Exception  
     * @return void  
     * @throws   
     */
    @Test
    public void getAllName() throws Exception {
        InetAddress[] inetAddresses = InetAddress.getAllByName("www.baidu.com");
        for (InetAddress inetAddress : inetAddresses) {
            System.out.println(inetAddress.getHostName());
            //得到执行本端代码的主机名称
            System.out.println(inetAddress.getLocalHost());
            System.out.println(inetAddress.isReachable(6000));
        }
    }

    /**   
     * @Title: NetWorkInterfaceTest   
     * @Description: 表示本地的IP地址信息,用来列举所有的网络端口
     * @param   可以枚举所有的本地地址，并由他们创建InetAddress对象，然后这些对象可以用于创建socket，服务器socket等
     * @return void  
     * @throws   
     */
    @Test
    public void NetWorkInterfaceTest() {

        try {
            InetAddress address = InetAddress.getByName("www.baidu.com");

            //            NetworkInterface ni=NetworkInterface.getByName("CE31");
            NetworkInterface co = NetworkInterface.getByInetAddress(address);
            System.out.println(co == null ? "出错了" : "对传递");

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @Test
    public void NetWorkInterfaceTest2() {

        try {

            //            NetworkInterface ni=NetworkInterface.getByName("CE31");
            //获取本地所有的网络端口
            Enumeration<NetworkInterface> co = NetworkInterface.getNetworkInterfaces();

            while (co.hasMoreElements()) {
                System.out.println(co.nextElement());
                Enumeration<InetAddress> addresses = co.nextElement().getInetAddresses();
                //获取该端口下面绑定的多有的 ip地址
                while(addresses.hasMoreElements()){
                    System.out.println(addresses.nextElement().getHostAddress());
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
