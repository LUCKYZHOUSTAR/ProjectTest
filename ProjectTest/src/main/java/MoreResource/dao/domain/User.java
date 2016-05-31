/**
 * 
 */
package MoreResource.dao.domain;

import java.io.Serializable;

/** 
* @ClassName: User 
* @Description: 
* @author LUCKY
* @date 2016年5月18日 下午8:02:55 
*  
*/
public class User implements Serializable{

    /** 
    * @Fields serialVersionUID : 
    */ 
    private static final long serialVersionUID = -539664971399939409L;
    private String id;
    private String userName;
    private String password;
   
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
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
    
}
