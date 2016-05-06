/**
 * 
 */
package Apache;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author LUCKY
 *
 */
public class ToStringBuilderTest {

    
    private String userName;
    private String password;
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    
    public static void main(String[] args) {
        ToStringBuilderTest test=new ToStringBuilderTest();
        test.setPassword("aa");
        test.setUserName("你哈了吗");
        System.out.println(ToStringBuilder.reflectionToString(test,
                ToStringStyle.SHORT_PREFIX_STYLE));
    }
}
