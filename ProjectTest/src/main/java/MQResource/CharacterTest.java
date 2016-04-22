/**     
 * @FileName: CharacterTest.java   
 * @Package:BasicJava   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月18日 下午6:35:41   
 * @version V1.0     
 */
package MQResource;

import org.junit.Test;

/**  
 * @ClassName: CharacterTest   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月18日 下午6:35:41     
 */
public class CharacterTest {

    public void Test() {
        char ch = 'a';

        // Unicode for uppercase Greek omega character
        char uniChar = '\u039A';

        // 字符数组
        char[] charArray = { 'a', 'b', 'c', 'd', 'e' };

        Character character = new Character('c');
    }

    @Test
    public void transfer() {
        /*
         * 转义序列 描述
        \t  在文中该处插入一个tab键
        \b  在文中该处插入一个后退键
        \n  在文中该处换行
        \r  在文中该处插入回车
        \f  在文中该处插入换页符
        \'  在文中该处插入单引号
        \"  在文中该处插入双引号
        \\  在文中该处插入反斜杠
         */
        System.out.println("she said\t,hello\nhi");
        System.out.println("he sais\f,nihao");
    }

    @Test
    public void methodTest() {
        Character ch = new Character('A');
        System.out.println(Character.isLetter('你'));
        System.out.println(Character.isLetter('a'));
        System.out.println(Character.isWhitespace('b'));
        System.out.println(Character.isUpperCase('A'));
        System.out.println(Character.isLowerCase('c'));
        System.out.println(Character.isDigit('3'));
        System.out.println(Character.isWhitespace(4));
    }
}
