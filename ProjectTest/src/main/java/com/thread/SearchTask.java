/**
 * 
 */
package com.thread;

/**
 * @author LUCKY
 *
 */
public class SearchTask {

    public static void main(String[] args) {
        SecurityManager sm=System.getSecurityManager();
        System.out.println(sm.getThreadGroup());
    }
    
}
