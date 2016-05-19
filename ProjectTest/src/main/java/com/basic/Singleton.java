/**
 * 
 */
package com.basic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author LUCKY
 *
 */
public class Singleton {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        //        final User user = User.getUser();
        final User user = new User();

        for (int i = 0; i < 5; i++) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        user.fly();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }
        executor.shutdown();
    }
}

class User {

    private static User user = new User();

    public User() {
    }

    public static User getUser() {
        return user;
    }

    public  void fly() throws Exception {
        System.out.println(Thread.currentThread().getName() + "开始启动了");
        Thread.sleep(3000l);
        System.out.println(Thread.currentThread().getName() + "启动完了");
    }

}
