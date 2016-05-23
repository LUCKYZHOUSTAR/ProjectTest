/**
 * 
 */
package Spring.Commonutils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

/** 
* @ClassName: FileCopyUtilsExample 
* @Description: FileCopyUtils
它提供了许多一步式的静态操作方法，能够将文件内容拷贝到一个目标 byte[]、String 甚至一个输出流或输出文件中。
* @author LUCKY
* @date 2016年5月20日 下午3:39:29 
*  
*/
public class FileCopyUtilsExample {

    @Test
    public void test1() throws Exception {
        String classPath = "./Test.txt";
        Resource res2 = new ClassPathResource(classPath);
        System.out.println(res2.getFilename());
        //        byte[] fileData = FileCopyUtils.copyToByteArray(res2.getFile());
        //        String fileStr = FileCopyUtils.copyToString(new FileReader(res2.getFile()));
//        System.out.println(fileStr);
        //拷贝到另一个文件中
        FileCopyUtils.copy("你好卡".getBytes(), new File("e:/test1.txt"));

    }

    /*
     *  static void copy(byte[] in, File out) 将 byte[] 拷贝到一个文件中
    static void copy(byte[] in, OutputStream out) 将 byte[] 拷贝到一个输出流中
    static int copy(File in, File out) 将文件拷贝到另一个文件中
    static int copy(InputStream in, OutputStream out) 将输入流拷贝到输出流中
    static int copy(Reader in, Writer out) 将 Reader 读取的内容拷贝到 Writer 指向目标输出中
    static void copy(String in, Writer out) 将字符串拷贝到一个 Writer 指向的目标中
     */
}
