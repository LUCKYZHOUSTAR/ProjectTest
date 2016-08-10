/**
 * 
 */
package test;

/**
 * @author LUCKY
 *
 */
public class dd {
    static int a = 10;
    static {
        a += 5;
    }

    public static void main(String[] args) {

        System.out.println(a);
    }

    static {
        a = a / 3;
    }
}
