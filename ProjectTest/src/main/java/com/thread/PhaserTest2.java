/**     
 * @FileName: PhaserTest2.java   
 * @Package:com.thread   
 * @Description: TODO  
 * @author: LUCKY    
 * @date:2016年3月22日 下午12:50:35   
 * @version V1.0     
 */
package com.thread;

import java.util.concurrent.Phaser;

/**  
 * @ClassName: PhaserTest2   
 * @Description: TODO  
 * @author: LUCKY  
 * @date:2016年3月22日 下午12:50:35     
 */
public class PhaserTest2 {

    public static void main(String[] args) {
        //共有三个线程，因此构造函数中写值为3
        Phaser phaser=new Phaser(3){

            /* (non-Javadoc)   
             * @param i
             * @param j
             * @return   
             * @see java.util.concurrent.Phaser#onAdvance(int, int)   
             */  
            @Override
            protected boolean onAdvance(int i, int j) {
                System.out.println("\n===============分割线===========");
               return j==1;
            }
            
        };
        
        
        System.out.println("程序开始执行");
        for(int i=0;i<3;i++){
            new MyThread((char)(97+i), phaser).start();  
        }
        
        phaser.register();//将主线程动态增加到Phaser中，此局执行后，公有四个线程
        while(!phaser.isTerminated()){//只要phaser不终结，主线程就循环等待
            int n=phaser.arriveAndAwaitAdvance();
        }
        
        System.out.println("程序结束");
    }
    
    
}
class MyThread extends Thread {  
    private char c;  
    private Phaser phaser;  
      
    public MyThread(char c, Phaser phaser) {  
        this.c = c;  
        this.phaser = phaser;  
    }  
  
    @Override  
    public void run() {  
        while(!phaser.isTerminated()) {  
            for(int i=0; i<10; i++) { //将当前字母打印10次  
                System.out.print(c + " ");  
            }  
            //打印完当前字母后，将其更新为其后第三个字母，例如b更新为e，用于下一阶段打印  
            c = (char) (c+3);   
            if(c>'z') {   
                //如果超出了字母z，则在phaser中动态减少一个线程，并退出循环结束本线程  
                //当3个工作线程都执行此语句后，phaser中就只剩一个主线程了  
                phaser.arriveAndDeregister();  
                break;  
            }else {  
                //反之，等待其他线程到达阶段终点，再一起进入下一个阶段  
                phaser.arriveAndAwaitAdvance();  
            }  
        }  
    }  
}  
