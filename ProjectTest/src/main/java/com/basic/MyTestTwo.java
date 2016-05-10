/**
 * 
 */
package com.basic;

/**
 * @author LUCKY
 *
 */
public class MyTestTwo {
    public static void main(String[] args) {
        System.out.println(FinalTest.x);
    }

}

class FinalTest {

    //编译的时候，就直接替换为常量了,final类型的常量直接在编译的时候就赋值了
    public static final int x = 6 / 3;
    static {
        System.out.println("FinalTest static Block");
    }
}
