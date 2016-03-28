/**     
 * @FileName: FileStreamTest.java   
 * @Package:network.book.Stream   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年3月27日 下午9:01:13   
 * @version V1.0     
 */
package network.book.Stream;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import org.junit.Test;

/**  
 * @ClassName: FileStreamTest   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年3月27日 下午9:01:13     
 */
public class FileStreamTest {

    public static void main(String[] args) {
        InputStream in = null;
        OutputStream out = null;
        //        System.out.println(System.getProperty("user.dir")+File.separator+"1.txt");

        //        File file=new File(System.getProperty("user.dir")+File.separator+"1.txt");
        try {
            in = new FileInputStream(new File(System.getProperty("user.dir") + File.separator
                                              + "1.txt"));
            out = new FileOutputStream(new File(System.getProperty("user.dir") + File.separator
                                                + "2.txt"));
            //可读取的最大的字节数，也就是文件的字节数
            System.out.println(in.available());
            int len = 0;
            //             InputStreamReader
            //缓冲流可以一行一行的读取
            //            BufferedInputStream bufferedInputStream=new Buffer
            //字节与对象之间的转换
            //            ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
            //            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream);
            //            objectOutputStream.writeObject(new Object());
            //            byte[] byteobj = byteOutputStream.toByteArray();

            //输出到控制器
            //            PrintStream printStream=new PrintStream("");
            //            printStream.print("323");
            Writer writer = new StringWriter();
            writer.write("nhaodfas");
            //            Reader reader = new StringReader();
            //            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteobj);
            //            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            //            objectInputStream.readObject();
            byte[] buffer = new byte[in.available()];
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer);
                out.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                ;
            }

            if (out != null) {
                try {
                    out.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        }
    }

    @Test
    public void add() {
        String src = "从明天起，做一个幸福的人，\n喂马，劈材，周游世界，\n从明天起，关心粮食和蔬菜，\n我有一所房子，面朝大海，春暖花开，\n从明天起，和每一个人通信，告诉他们我的幸福\n";

        char[] buffer = new char[32];
        int hasRead = 0;

        try (StringReader sr = new StringReader(src);

        ) {
            //采用循环读取的方式，读取字符串  
            while ((hasRead = sr.read(buffer)) > 0) {
                //  
                System.out.println(new String(buffer, 0, hasRead));
            }
        } catch (IOException ioe) {

            ioe.printStackTrace();
        }

        try (

        //  
        StringWriter sw = new StringWriter();) {

            CharArrayWriter writer = new CharArrayWriter();
            char[] charbuffer = new char[23];
            char[] charbuffer3 = new char[23];
            //传一个字符的数组，然后通过write些回去操作
            CharArrayReader reader = new CharArrayReader(charbuffer);
            int len=0;
            while((len=reader.read())>0){
                
            }
            
            //调用方法执行输出  
            sw.write("有一个美丽的新世界\n");
            sw.write("有一个美丽的新世界\n");
            sw.write("有一个美丽的新世界\n");
            sw.write("有一个美丽的新世界\n");
            sw.write("有一个美丽的新世界\n");

            System.out.println(sw.toString());

        } catch (IOException ioe) {

            ioe.printStackTrace();

        }
    }
}
