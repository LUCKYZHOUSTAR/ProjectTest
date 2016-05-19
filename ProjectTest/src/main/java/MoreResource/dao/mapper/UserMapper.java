/**
 * 
 */
package MoreResource.dao.mapper;

import java.util.List;

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
}
