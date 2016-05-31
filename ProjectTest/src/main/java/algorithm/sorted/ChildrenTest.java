/**
 * 
 */
package algorithm.sorted;

/** 
* @ClassName: ChildrenTest 
* @Description: 公鸡5文钱一只，母鸡3文钱一只，小鸡3只一文钱，
用100文钱买一百只鸡,其中公鸡，母鸡，小鸡都必须要有，问公鸡，母鸡，小鸡要买多少只刚好凑足100文钱。
* @author LUCKY
* @date 2016年5月24日 下午1:11:29 
*  
*/
public class ChildrenTest {

    /** 
    * @Title: main 
    * @Description:
    * 只不过我们用计算机来推算，我们可以设公鸡为x，母鸡为y，小鸡为z，那么我们

         可以得出如下的不定方程，

         x+y+z=100,

         5x+3y+z/3=100，

        下面再看看x，y，z的取值范围。

        由于只有100文钱，则5x<100 => 0<x<20, 同理  0<y<33,那么z=100-x-y，

        好，我们已经分析清楚了，下面就可以编码了。
    * @author LUCKY
    * @date 2016年5月24日 下午1:14:44 
    */
    public static void main(String[] args) {
        for (int x = 1; x < 20; x++) {
            for (int y = 1; y < 33; y++) {
                //剩余小鸡
                int z = 100 - x - y;
                if ((z % 3 == 0) && (x * 5 + y * 3 + z / 3 == 100)) {
                    System.out.println("公鸡:" + x + "只，母鸡:" + y + "只,小鸡:" + z + "只");
                }
            }
        }
        
        System.out.println("优化后-----------------------");
        youhua();
    }

    /** 
    * @Title: youhua 
    * @Description:来，肯定需要我们推算一下这个不定方程。

    x+y+z=100          ①

    5x+3y+z/3=100    ②

    令②x3-① 可得

    7x+4y=100

    =>y=25-(7/4)x          ③

    又因为0<y<100的自然数，则可令

     x=4k                    ④

    将④代入③可得

    => y=25-7k               ⑤

    将④⑤代入①可知

    => z=75+3k               ⑥
    * @author LUCKY
    * 经过优化后，让上面的式子都跟一个变量有关的话，就可以减少程序的时间复杂度了
    * @date 2016年5月24日 下午1:16:27 
    */
    public static void youhua() {
        int x, y, z;
        for (int k = 1; k <= 3; k++) {
            x = 4 * k;
            y = 25 - 7 * k;
            z = 75 + 3 * k;
            System.out.println("公鸡:" + x + "只，母鸡:" + y + "只,小鸡:" + z + "只");

        }
    }
}
