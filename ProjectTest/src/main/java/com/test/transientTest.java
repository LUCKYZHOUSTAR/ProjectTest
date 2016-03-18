/**     
 * @FileName: transientTest.java   
 * @Package:com.test   
 * @Description: TODO  
 * @author: LUCKY    
 * @date:2016年1月26日 下午7:37:27   
 * @version V1.0     
 */
package com.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @ClassName: transientTest
 * @Description: TODO
 * @author: LUCKY
 * @date:2016年1月26日 下午7:37:27
 */
/*
 * 1）一旦变量被transient修饰，变量将不再是对象持久化的一部分，该变量内容在序列化后无法获得访问。

2）transient关键字只能修饰变量，而不能修饰方法和类。注意，本地变量是不能被transient关键字修饰的。变量如果是用户自定义类变量，则该类需要实现Serializable接口。

3）被transient关键字修饰的变量不再能被序列化，一个静态变量不管是否被transient修饰，均不能被序列化。

 */
public class transientTest {

	public static void main(String[] args) {
		LoggingInfo logInfo = new LoggingInfo("MIKE", "MECHANICS");
		LoggingInfo dd=null;
		System.out.println(logInfo.toString());
		try {
			ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(
					"logInfo.out"));
			o.writeObject(logInfo);
			o.close();
		} catch (Exception e) {// deal with exception}
			// To read the object back, we can write
			try {
				ObjectInputStream in = new ObjectInputStream(
						new FileInputStream("logInfo.out"));
				 dd = (LoggingInfo) in.readObject();
				System.out.println(logInfo.toString());
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}

	}
}
