/**
 * 
 */
package com.basic;

/**
 * @author LUCKY
 *
 */
public class TestSix {

    static {
        System.out.println("Test static block");
    }

    public static void main(String[] args) {
        System.out.println(child3.a);
        child3.doSomethind();
    }
}

class Parent3 {
    static int a = 3;
    static {
        System.out.println("Parent3 static block");
    }

    static void doSomethind() {
        System.out.println("do something");
    }
}

class child3 extends Parent3 {

    static {
        System.out.println("Child3 static block");
    }
}
