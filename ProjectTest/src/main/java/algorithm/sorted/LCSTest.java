/**
 * 
 */
package algorithm.sorted;

/** 
* @ClassName: LCSTest 
* @Description: 
*   举个例子，cnblogs这个字符串中子序列有多少个呢？很显然有27个，比如其中的cb,cgs等等都是其子序列，我们可以看出

子序列不见得一定是连续的，连续的那是子串。

     我想大家已经了解了子序列的概念，那现在可以延伸到两个字符串了，那么大家能够看出：cnblogs和belong的公共子序列吗？

在你找出的公共子序列中，你能找出最长的公共子序列吗？
* @author LUCKY
* @date 2016年5月24日 下午2:07:40 
*  
*/
public class LCSTest {

    public static void main(String[] args) {
        String a = "qddTom 00H6ankys";
        String b = "0H6ankycs";
        System.out.println(compute(a, b));
    }

    /** 
    * @Title: LCS 
    * @Description:经常会遇到复杂问题不能简单地分解成几个子问题，而会分解出一系列的子问题。简单地采用把大问题分解成子问题，并综合子问题的解导出大问题的解的方法，问题求解耗时会按问题规模呈幂级数增加。
    为了节约重复求相同子问题的时间，引入一个数组，不管它们是否对最终解有用，把所有子问题的解存于该数组中，这就是动态规划法所采用的基本方法。
    * @author LUCKY
    * @date 2016年5月24日 下午2:18:12 
    */
    public static int compute(char[] str1, char[] str2) {
        int substringLength1 = str1.length;
        int substringLength2 = str2.length;

        // 构造二维数组记录子问题A[i]和B[j]的LCS的长度
        int[][] opt = new int[substringLength1 + 1][substringLength2 + 1];

        // 从后向前，动态规划计算所有子问题。也可从前到后。
        for (int i = substringLength1 - 1; i >= 0; i--) {
            for (int j = substringLength2 - 1; j >= 0; j--) {
                if (str1[i] == str2[j])
                    opt[i][j] = opt[i + 1][j + 1] + 1;// 状态转移方程
                else
                    opt[i][j] = Math.max(opt[i + 1][j], opt[i][j + 1]);// 状态转移方程
            }
        }
        System.out.println("substring1:" + new String(str1));
        System.out.println("substring2:" + new String(str2));
        System.out.print("LCS:");

        int i = 0, j = 0;
        while (i < substringLength1 && j < substringLength2) {
            if (str1[i] == str2[j]) {
                System.out.print(str1[i]);
                i++;
                j++;
            } else if (opt[i + 1][j] >= opt[i][j + 1])
                i++;
            else
                j++;
        }
        System.out.println();
        return opt[0][0];
    }

    public static int compute(String str1, String str2) {
        return compute(str1.toCharArray(), str2.toCharArray());
    }
}
