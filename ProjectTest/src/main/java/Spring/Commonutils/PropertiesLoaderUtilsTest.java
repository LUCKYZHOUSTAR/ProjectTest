/**
 * 
 */
package Spring.Commonutils;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;

/** 
* @ClassName: PropertiesLoaderUtils 
* @Description: 
* @author LUCKY
* @date 2016年5月20日 下午3:47:53 
*  
*/
public class PropertiesLoaderUtilsTest {

    public void Test() throws IOException {
        // ① jdbc.properties 是位于类路径下的文件 
        Properties props = PropertiesLoaderUtils.loadAllProperties("jdbc.properties");
        System.out.println(props.getProperty("jdbc.driverClassName"));
    }
}
