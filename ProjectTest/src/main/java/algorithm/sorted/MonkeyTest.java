/**
 * 
 */
package algorithm.sorted;

import com.sun.org.apache.regexp.internal.recompile;

/** 
* @ClassName: MonkeyTest 
* @Description:   猴子第一天摘下若干个桃子，当即吃了一半，还不过瘾就多吃了一个。第二天早上又将剩下的桃子吃了一半，还是不过瘾又多

吃了一个。以后每天都吃前一天剩下的一半再加一个。到第10天刚好剩一个。问猴子第一天摘了多少个桃子？
* @author LUCKY
* @date 2016年5月24日 下午1:28:05 
*  
*/
public class MonkeyTest {

    /** 
    * @Title: main 
    * @Description: 我们找到递推公式，问题就迎刃而解了。

               令S10=1，容易看出 S9=2(S10+1)， 简化一下 

                 S9=2S10+2

                 S8=2S9+2

                     .....

                 Sn=2Sn+1+2

    
    * @author LUCKY
    * @date 2016年5月24日 下午1:28:21 
    */
    public static void main(String[] args) {

        int sum = SumPeach(1);
        System.out.println("第一天摘得桃子后" + sum);
        System.out.println("优化后----------");

        int sumPeach = SumPeachTail(1, 1);
        System.out.println("第一天摘得桃子后" + sum);
        System.out.println("优化后----------");
        System.out.println("第一天摘得桃子后" + forTest());

    }

    public static int SumPeach(int day) {
        if (day == 10)
            return 1;

        return 2 * SumPeach(day + 1) + 2;
    }

    public static int SumPeachTail(int day, int total) {
        if (day == 10)
            return total;

        //将当前的值计算出传递给下一层
        return SumPeachTail(day + 1, 2 * total + 2);
    }

    public static int forTest() {
        int total = 1;
        for (int i = 10; i > 1; i--) {
            total = total * 2 + 2;
        }
        return total;
    }
}
