/**     
 * @FileName: PhaserTest.java   
 * @Package:com.thread   
 * @Description: TODO  
 * @author: LUCKY    
 * @date:2016年3月22日 下午12:43:47   
 * @version V1.0     
 */
package com.thread;

import java.util.concurrent.Phaser;

/**  
 * @ClassName: PhaserTest   
 * @Description: TODO  
 * @author: LUCKY  
 * @date:2016年3月22日 下午12:43:47     
 */
public class PhaserTest {
    public static void main(String args[]) {
        //  
        final int count = 5;
        final Phaser phaser = new Phaser(count);
        for (int i = 0; i < count; i++) {
            System.out.println("starting thread, id: " + i);
            final Thread thread = new Thread(new Task(i, phaser));
            thread.start();
        }
    }

    public static class Task implements Runnable {
        //  
        private final int    id;
        private final Phaser phaser;

        public Task(int id, Phaser phaser) {
            this.id = id;
            this.phaser = phaser;
        }

        @Override
        public void run() {
            phaser.arriveAndAwaitAdvance();
            System.out.println("in Task.run(), phase: " + phaser.getPhase() + ", id: " + this.id);
        }
    }
}
