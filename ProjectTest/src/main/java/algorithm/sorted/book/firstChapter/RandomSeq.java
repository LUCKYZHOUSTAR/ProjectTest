/**
 * 
 */
package algorithm.sorted.book.firstChapter;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/** 
* @ClassName: RandomSeq 
* @Description: 
* @author LUCKY
* @date 2016年6月15日 下午3:18:14 
*  
*/
public class RandomSeq {

    public static void main(String[] args) {
        Random rd = new Random();
        rd.setSeed(1);
        System.out.println(rd.nextInt());
        System.out.println(Math.random() * 1);
        System.out.println('b' + 'c');

        System.out.println(binaryString(34));
        System.out.println(Integer.toBinaryString(34));
    }

    public static String binaryString(int N) {
        String s = "";
        for (int n = N; n > 0; n /= 2)
            s = (n % 2) + s;

        return s;
    }
    
    
    
    public static void print(int a[][]){
        int count=0;
        for(int j=0;j<a.length;j++){
            for(int i=0;i<a[0].length;i++){
                System.out.println(a[j][i]+"\t");
            }
        }
    }
}
