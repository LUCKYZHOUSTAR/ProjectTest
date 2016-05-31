/**
 * 
 */
package MoreResource.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import MoreResource.dao.domain.User;

/** 
* @ClassName: UserMapper 
* @Description: 
* @author LUCKY
* @date 2016年5月19日 下午1:29:32 
*  
*/
public interface UserMapper {

    List<User> queryUserList();

    List<User> queryUserByUserNo(@Param("userName") String userName);
    List<User> queryUserByUserName(@Param("userName") String userName, @Param("id") String id);
    int insertUser(User user);

    int updateUser(User user);

}
