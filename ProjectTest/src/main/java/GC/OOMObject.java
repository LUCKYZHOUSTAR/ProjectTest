/**
 * 
 */
package GC;

import java.util.ArrayList;
import java.util.List;

/** 
* @ClassName: OOMObject 
* @Description: 
* @author LUCKY
* @date 2016年6月6日 上午9:49:12 
*  
*/
public class OOMObject {

    public byte[] placeHolder=new byte[64*1024];
    
    public static void fillHeap(int num) throws InterruptedException{
        List<OOMObject> list=new ArrayList<>();
        for(int i=0;i<num;i++){
            Thread.sleep(50);
            list.add(new OOMObject());
        }
        
    }
    
    public static void main(String[] args) throws InterruptedException {
        fillHeap(1000);
        
        System.gc();
    }
}
