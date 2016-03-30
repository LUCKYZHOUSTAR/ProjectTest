/**     
 * @FileName: singletonTest.java   
 * @Package:EffectiveJava.Item8   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年3月29日 下午4:50:28   
 * @version V1.0     
 */
package EffectiveJava.Item8;

/**  
 * @ClassName: singletonTest   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年3月29日 下午4:50:28     
 */
public class singletonTest {

    public static final singletonTest test=new singletonTest();
    
    private singletonTest(){}
    
    public static singletonTest getInstance(){
        return test;
    }
}
