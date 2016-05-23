/**
 * 
 */
package Spring.Commonutils;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

/** 
* @ClassName: FileSourceExample 
* @Description: 访问文件资源
* 通过 FileSystemResource 以文件系统绝对路径的方式进行访问；
* 通过 ClassPathResource 以类路径的方式进行访问；
* 通过 ServletContextResource 以相对于 Web 应用根目录的方式进行访问。
* @author LUCKY
* @date 2016年5月20日 下午3:29:52 
*  
*/
public class FileSourceExample {

    @Test
    public void Test1() throws Exception {
        String filePath = "E:/Test.txt";
        // ① 使用系统文件路径方式加载文件
        Resource res1 = new FileSystemResource(filePath);
        System.out.println(res1.getFilename());
        System.out.println(res1.getInputStream());
        System.out.println(res1.getFile());//直接获取文件对象操作
        //使用类路径方式加载
        String classPath = "./Test.txt";
        Resource res2 = new ClassPathResource(classPath);
        System.out.println(res2.getFilename());

        // Web 应用中，您还可以通过 ServletContextResource 以相对于 Web 应用根目录的方式访问文件资源
        File clsFile = ResourceUtils.getFile("classpath:log4j.xml");
        //File httpFile = ResourceUtils.getFile(httpFilePath); 
        System.out.println(clsFile.getName());

    }

    public void test3() throws IOException {
        String filePath = "E:/Test.txt";
        // ① 使用系统文件路径方式加载文件
        Resource res1 = new FileSystemResource(filePath);
        // ① 指定文件资源对应的编码格式（UTF-8）
        EncodedResource encRes = new EncodedResource(res1, "UTF-8");
        // ② 这样才能正确读取文件的内容，而不会出现乱码
        String content = FileCopyUtils.copyToString(encRes.getReader());
        System.out.println(content);
    }
}
