/**
 * 
 */
package MoreResource.dao.Test;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alipay.zdal.client.ThreadLocalString;
import com.alipay.zdal.client.jdbc.ZdalDataSource;
import com.alipay.zdal.client.util.ThreadLocalMap;
import com.alipay.zdal.client.util.condition.DBSelectorIDRouteCondition;

import MoreResource.dao.domain.User;
import MoreResource.dao.mapper.UserMapper;

/** 
* @ClassName: UserTest 
* @Description: 
* @author LUCKY
* @date 2016年5月19日 下午1:33:28 
*  
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-dao.xml")
public class UserTest {

    @Autowired
    private UserMapper     userMapper;
    @Autowired
    private ZdalDataSource dataSource;

    @Test
    public void queryUserList() {
        DBSelectorIDRouteCondition dbSelectorIDRouteCondition = new DBSelectorIDRouteCondition(
            "user", "dd_1", "user", "user_0000");
        ThreadLocalMap.put(ThreadLocalString.ROUTE_CONDITION, dbSelectorIDRouteCondition);
        List<User> users = userMapper.queryUserList();
        System.out.println(users.size());
        User user = userMapper.queryUserByUserNo("0000");
        System.out.println(user.getPassword());
        User user2 = new User();
        user2.setPassword("90345345");
        user2.setUserName("009764434");
        userMapper.insertUser(user2);
    }

    @Test
    public void test() {
        //{master_0=master_0}
        //        {master_2=master_2, master_1=master_1, master_0=master_0}
        //        Map<String, String> logicPhysicsDsNames = dataSource.getZdalConfig()
        //            .getLogicPhysicsDsNames();
        //        System.out.println(logicPhysicsDsNames.toString());
        //通过这样可以来指定，查询那个数据库下面的那张表的操作，第一个参数没有意义
        /*
         * master_0:代表的是一个数据源的操作,后面是需要匹配的是该数据源下的那张表
         */
        /*
         * 如果有多张表的话，顺序的遍历，找到规则的哪一个操作
         */
        /*
         * 如果查询的条件里面没有关键字的话，就需要指定 
         */
        DBSelectorIDRouteCondition dbSelectorIDRouteCondition = new DBSelectorIDRouteCondition(
            "user", "master_0", "user", "user_0000");
        ThreadLocalMap.put(ThreadLocalString.ROUTE_CONDITION, dbSelectorIDRouteCondition);
        //        List<User> users = userMapper.queryUserList();
        //        User user = userMapper.queryUserByUserNo("55346456");
        //        System.out.println(user.getPassword());
        List<User> users = userMapper.queryUserList();
        System.out.println(users.size());
        //        System.out.println(users.size());
        System.out.println("");
    }

    /*
     * 上面哪种方式是指定，如果不指定的话，需要匹配路由的规则信息
     */
    @Test
    public void test2() {
        DBSelectorIDRouteCondition dbSelectorIDRouteCondition = new DBSelectorIDRouteCondition(
            "user", "master_0", "user", "user_0000", "user_0001");
        ThreadLocalMap.put(ThreadLocalString.ROUTE_CONDITION, dbSelectorIDRouteCondition);
        User user = userMapper.queryUserByUserNo("2345");
        User user2 = new User();
        user2.setPassword("67");
        user2.setUserName("67");
        userMapper.insertUser(user2);
        System.out.println(user.getPassword());

        //        List<User> users = userMapper.queryUserList();
        //        System.out.println(users.size());
    }

    /*
     * 不能多张表插入
     */
    @Test
    public void insetTest() {
        DBSelectorIDRouteCondition dbSelectorIDRouteCondition = new DBSelectorIDRouteCondition(
            "user", "master_0", "user", "user_0000");
        ThreadLocalMap.put(ThreadLocalString.ROUTE_CONDITION, dbSelectorIDRouteCondition);
        User user2 = new User();
        user2.setPassword("67");
        user2.setUserName("67");
        userMapper.insertUser(user2);
    }
}
