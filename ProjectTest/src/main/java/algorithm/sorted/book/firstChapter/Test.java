/**
 * 
 */
package algorithm.sorted.book.firstChapter;

import java.util.Scanner;

/** 
* @ClassName: Test 
* @Description: 
* @author LUCKY
* @date 2016年6月15日 下午3:04:51 
*  
*/
public class Test {

    public static void main(String[] args) {
        System.out.println((0+15)/2);
        System.out.println(2.0e-6*100000000.1);
        System.out.println(true&&false||true&&true);
        
        Scanner input=new Scanner(System.in);
        System.out.println("请输入第一个数");
        int a=input.nextInt();
        System.out.println("请输入第二个数");
        int b=input.nextInt();
        System.out.println("请输入第三个数");
        int c=input.nextInt();
        if(a==b&&b==c){
            System.out.println("三个数相等");
        }else {
            System.out.println("三个数不相等");
        }
        
        
    }
    
    
}
