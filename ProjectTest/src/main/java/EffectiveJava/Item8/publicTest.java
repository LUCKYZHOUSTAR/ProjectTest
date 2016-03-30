/**     
 * @FileName: publicTest.java   
 * @Package:EffectiveJava.Item8   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年3月29日 下午6:41:53   
 * @version V1.0     
 */
package EffectiveJava.Item8;

/**  
 * @ClassName: publicTest   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年3月29日 下午6:41:53     
 */
public class publicTest {

    
    //类似于这样的代码是危险的，外界是可以被修改的
    public static final String[] param={"1","2"};
    private static final String[] params={"1","2"};
    
    //应该设为一个变量，返回一个备份
    public static String[] values(){
        return params.clone();
    }
}
