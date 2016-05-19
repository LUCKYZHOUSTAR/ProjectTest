/**
 * 
 */
package com.Utils;

import java.text.MessageFormat;

/** 
* @ClassName: MessageFormateTest 
* @Description: 类似于string.formate,但是比它好用
* @author LUCKY
* @date 2016年5月16日 下午4:53:02 
*  
*/
public class MessageFormateTest {

    public static void main(String[] args) {
        //两个单引号才表示一个单引号，仅写一个单引号将被忽略。
        System.out.println(MessageFormat.format(" ''{0}{1}", 1, 2));
        //单引号会使其后面的占位符均失效，导致直接输出占位符。
        MessageFormat.format("'{0}{1}", 1, 2); // 结果{0}{1}
        MessageFormat.format("'{0}'{1}", 1, 2); // 结果{0}
    }
}
