/**     
 * @FileName: test.java   
 * @Package:EffectiveJava.Item8   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年3月29日 下午4:32:39   
 * @version V1.0     
 */
package EffectiveJava.Item8;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

/**  
 * @ClassName: test   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年3月29日 下午4:32:39     
 */
public class test {

    /*
     * 普通情况下使用for...each比while好，但是
     * 如果需要对集合进行删除过滤的话，for...each实现不了
     * 还是需要用Iterator来实现
     * */
    public void test1() {

        List<User> users = new ArrayList<>();

        //for比while好，因为for循环只用的是局部变量，出错率比较的底下
        for (User user : users) {

        }

        Iterator<User> i = users.iterator();
        while (i.hasNext()) {

        }

    }

    /*
     * 一般情况下选择基本类型
     * 基本类型OR装箱类型
     * 集合中一定要防止装箱类型，可以校验参数合法性
     * 
     * */
    public void test2() {

    }

    /*
     * 如果其他类型合适，则避免使用字符串
     * */
    public void test3() {

    }

    @Test
    public void Test44() {
        publicTest.param[1] = "sdf";
        for (String a : publicTest.param) {
            System.out.println(a);
        }
    }
}

class User {
    private String UserName;
    private String pwd;

    /**   
     * @return userName   
     */
    public String getUserName() {
        return UserName;
    }

    /**     
     * @param userName the userName to set     
     */
    public void setUserName(String userName) {
        UserName = userName;
    }

    /**   
     * @return pwd   
     */
    public String getPwd() {
        return pwd;
    }

    /**     
     * @param pwd the pwd to set     
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

}
