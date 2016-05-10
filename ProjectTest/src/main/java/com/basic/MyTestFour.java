/**
 * 
 */
package com.basic;

/**
 * @author LUCKY
 *
 */
public class MyTestFour {

    static{
        System.out.println("Test static block");
    }
    public static void main(String[] args) {
        System.out.println(child.b);
    }
}

class Parent{
    static int a=3;
    static{
        System.out.println("Parent static block");
    }
}

class child extends Parent{
    static  int b=4;
    static{
        System.out.println("Child static block");
    }
}
