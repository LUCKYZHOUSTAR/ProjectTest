/**     
 * @FileName: TestSystemproperty.java   
 * @Package:BasicJava   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月18日 下午7:15:54   
 * @version V1.0     
 */
package MQResource;

/**  
 * @ClassName: TestSystemproperty   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月18日 下午7:15:54     
 */
public class TestSystemproperty {
    
    
    
    /*
     * 网上很多使用的是getProperties。说获得系统变量，但是其实不正确。getProperties中所谓的"system properties"
     * 其实是指"java system"，而非"operation system"，
     * 概念完全不同，使用getProperties获得的其实是虚拟机的变量形如： -Djavaxxxx。
     * getenv方法才是真正的获得系统环境变量，比如Path之类。其方法命名方式有违Sun命名规范其实。
     */
    
    
    
    
    
    /*
     * 最近的程序中优先使用系统环境设置的变量，其次使用默认的路径。
     * 查找了Java中如何获取环境变量，发现System. getenv()时最实用的方法
     */
    public static void main(String[] args) {
        System.out.println("Java运行时环境版本:" + System.getProperty("java.version"));
        System.out.println("Java 运行时环境供应商:" + System.getProperty("java.vendor"));
        System.out.println("Java 供应商的URL:" + System.getProperty("java.vendor.url"));
        System.out.println("Java安装目录:" + System.getProperty("java.home"));
        System.out.println("Java 虚拟机规范版本:" + System.getProperty("java.vm.specification.version"));
        System.out.println("Java 类格式版本号:" + System.getProperty("java.class.version"));
        System.out.println("Java类路径:" + System.getProperty("java.class.path"));
        System.out.println("操作系统的名称:" + System.getProperty("os.name"));
        System.out.println("操作系统的架构:" + System.getProperty("os.arch"));
        System.out.println("操作系统的版本:" + System.getProperty("os.version"));
        System.out.println("用户的主目录:" + System.getProperty("user.home"));
        System.out.println("用户的当前工作目录:" + System.getProperty("user.dir"));
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("自定义变量getProperty CONF_LOCATION:" + System.getProperty("conf.location"));
        System.out.println("--------------------------------------------");
        System.out.println("自定义变量getenv CONF_LOCATION:" + System.getenv("conf.location"));

    }
}
