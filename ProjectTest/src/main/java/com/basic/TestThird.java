/**
 * 
 */
package com.basic;

import java.util.Random;

/**
 * @author LUCKY
 *
 */
public class TestThird {
    public static void main(String[] args) {
        System.out.println(FinalTest2.x);
    }

}

class FinalTest2 {

    public static final int x = new Random().nextInt(100);
    static {
        System.out.println("FinalTest static Block");
    }
}
