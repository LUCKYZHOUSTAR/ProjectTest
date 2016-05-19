/**
 * 
 */
package MoreResource.dao.Test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
    private UserMapper userMapper;
    
    
    @Test
    public void queryUserList(){
        
       List<User>  users=userMapper.queryUserList();
       System.out.println(users.size());
    }
}
