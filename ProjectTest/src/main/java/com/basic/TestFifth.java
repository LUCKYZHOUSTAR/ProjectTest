/**
 * 
 */
package com.basic;

/**
 * @author LUCKY
 *
 */
public class TestFifth {

    static{
        System.out.println("Test static block");
    }
    public static void main(String[] args) {
        Parent2 parent2;
        System.out.println("------------");
        parent2=new Parent2();
        System.out.println(Parent2.a);
        System.out.println(child2.b);//访问静态变量也会造成类的初始化
    }
}

class Parent2{
    static int a=3;
    static{
        System.out.println("Parent2 static block");
    }
}

class child2 extends Parent2{
    static  int b=4;
    static{
        System.out.println("Child2 static block");
    }
}
