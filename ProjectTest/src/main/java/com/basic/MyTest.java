/**
 * 
 */
package com.basic;

/**
 * @author LUCKY
 *
 */
class ExampleOne {

    
    private int dd;
    //    private static ExampleOne exampleOne = new ExampleOne();
    private static ExampleOne exampleOne = new ExampleOne();
    public static int         counter1;
    public static int         counter2   = 0;

    private ExampleOne() {
        counter1++;
        counter2++;
    }

    public static ExampleOne GetInstance() {
        return exampleOne;
    }
}

public class MyTest {
    static {
        System.out.println("你好吗");
    }
    private static int a = 9;

    public static void main(String[] args) {
        ExampleOne exampleOne = ExampleOne.GetInstance();
        System.out.println("Counter1==" + exampleOne.counter1);
        System.out.println("Counter2==" + exampleOne.counter2);
    }
}