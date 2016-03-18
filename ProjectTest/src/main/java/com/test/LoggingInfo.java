/**     
 * @FileName: LoggingInfo.java   
 * @Package:com.test   
 * @Description: TODO  
 * @author: LUCKY    
 * @date:2016年1月26日 下午7:33:23   
 * @version V1.0     
 */
package com.test;

import java.util.Date;

/**
 * @ClassName: LoggingInfo
 * @Description: TODO
 * @author: LUCKY
 * @date:2016年1月26日 下午7:33:23
 */
public class LoggingInfo {
	private Date loggingDate = new Date();
	private String uid;
	private transient String pwd;
	private volatile int count;

	LoggingInfo(String user, String password) {
		uid = user;
		pwd = password;
	}

	/**  
	 *    
	 */
	public LoggingInfo() {
		// TODO Auto-generated constructor stub
	}
	public String toString() {
		String password = null;
		if (pwd == null) {
			password = "NOT SET";
		} else {
			password = pwd;
		}
		return "logon info: \n   " + "user: " + uid + "\n   logging date : "
				+ loggingDate.toString() + "\n   password: " + password;
	}

	
}
