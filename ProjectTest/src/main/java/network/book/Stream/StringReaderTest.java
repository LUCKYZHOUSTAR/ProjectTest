/**     
 * @FileName: StringReaderTest.java   
 * @Package:network.book.Stream   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年3月28日 上午9:16:00   
 * @version V1.0     
 */
package network.book.Stream;

import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.Test;

/**  
 * @ClassName: StringReaderTest   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年3月28日 上午9:16:00     
 */
public class StringReaderTest {

    public static void main(String[] args) {
        String str = "你成功吗,sfsdf";
        StringReader reader = new StringReader(str);
        StringWriter writer = new StringWriter();

        try {
            int len = 0;
            char[] buffer = new char[1024];
            while ((len = reader.read(buffer)) > 0) {
                String strM = new String(buffer, 0, len).toUpperCase();
                writer.write(strM);
                System.out.println(writer.toString());
                writer.flush();
            }

            char[] buffer2 = new char[1024];

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public  void TestStringArray() {

        try {
            CharArrayWriter writer = new CharArrayWriter();
            char[] buffer = new char[1024];
            buffer[1] = 's';
            buffer[2] = 'd';
            buffer[3] = 'c';
            buffer[4] = 'r';
            char[] buffer2 = new char[1024];
            CharArrayReader reader = new CharArrayReader(buffer);
            int len = 0;
            while ((len = reader.read(buffer2)) > 0) {
                writer.write(buffer2);
                writer.flush();
            }

            System.out.println(writer.toCharArray());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
