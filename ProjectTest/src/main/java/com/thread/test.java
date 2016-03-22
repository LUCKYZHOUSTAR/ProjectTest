/**     
 * @FileName: test.java   
 * @Package:com.thread   
 * @Description: TODO  
 * @author: LUCKY    
 * @date:2016年3月22日 上午9:20:15   
 * @version V1.0     
 */
package com.thread;

import org.junit.Test;

/**  
 * @ClassName: test   
 * @Description: TODO  
 * @author: LUCKY  
 * @date:2016年3月22日 上午9:20:15     
 */
public class test {

     

     Condition conditionTest = new Condition();

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            
        }
    }

    @Test
    public void test() {
        
        for (int i = 0; i < 20; i++) {
            conditionTest.sub();
        }
    }
}

class Condition {
    int       count         = 20;
    public void sub() {
        count=count-1;
        System.out.println(count);
    }
}
