/**
 * 
 */
package GC;

import java.util.ArrayList;
import java.util.List;

/** 
* @ClassName: GcTest 
* @Description: 
* @author LUCKY
* @date 2016年5月30日 下午7:01:50 
*  
*/
public class GcTest {

    private static final int _1MB = 1024 * 1024;

    public static void add(){
        byte[] allocation1, allocation2, allocation3, allocation4;

        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB];
        allocation4 = new byte[4 * _1MB];
        //        for (int i = 0; i < 1000; i++) {
        //            byte[] allo = new byte[4 * _1MB];
        //        }

        List list = new ArrayList();
        while (true) {
            list.add(allocation4);
        }
    }
    
    public static void main(String[] args) throws Exception {
        add();
        System.gc();

        byte[] allocation1, allocation2, allocation3, allocation4;

        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB];
        allocation4 = new byte[4 * _1MB];
        //        for (int i = 0; i < 1000; i++) {
        //            byte[] allo = new byte[4 * _1MB];
        //        }

        List list = new ArrayList();
        while (true) {
            list.add(allocation4);
            System.gc();
        }

    }

}
