/**     
 * @FileName: EnumerationTest.java   
 * @Package:com.test   
 * @Description: TODO  
 * @author: LUCKY    
 * @date:2016年1月27日 上午9:15:11   
 * @version V1.0     
 */
package com.test;

import java.util.Enumeration;

import com.test.EnumerationTest.DataStrut;

/**  
 * @ClassName: EnumerationTest   
 * @Description: TODO  
 * @author: LUCKY  
 * @date:2016年1月27日 上午9:15:11     
 */
public class EnumerationTest implements Enumeration<DataStrut> {

	
	
	class DataStrut{
		
	}

	/* (non-Javadoc)   
	 * @return   
	 * @see java.util.Enumeration#hasMoreElements()   
	 */  
	@Override
	public boolean hasMoreElements() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)   
	 * @return   
	 * @see java.util.Enumeration#nextElement()   
	 */  
	@Override
	public DataStrut nextElement() {
		// TODO Auto-generated method stub
		return null;
	}
}
