/**
 * 
 */
package com.thread;

/**
 *  public int activeCount(); // 获得当前线程组中线程数目， 包括可运行和不可运行的 
       public int activeGroupCount()； //获得当前线程组中活动的子线程组的数目 
       public int enumerate（Thread list[]）; //列举当前线程组中的线程 
       public int enumerate（ThreadGroup list[]）； //列举当前线程组中的子线程组 
       public final int getMaxPriority（）; //获得当前线程组中最大优先级 
       public final String getName（）； //获得当前线程组的名字 
       public final ThreadGroup getParent（）; //获得当前线程组的父线程组 
       public boolean parentOf（ThreadGroup g）； //判断当前线程组是否为指定线程的父线程 
       public boolean isDaemon（）; //判断当前线程组中是否有监护线程 
       public void list（）; //列出当前线程组中所有线程和子线程名 
 * @author LUCKY
 *
 */
public class ThreadGroupTest {

    public static void main(String[] args) throws Exception {
        ThreadGroup group = new ThreadGroup("Parent");
        Thread t = new Thread(group, new task());
        t.start();
        System.out.println(group.activeCount());
        System.out.println(group.activeGroupCount());
        System.out.println(group.getMaxPriority());
        System.out.println(group.activeGroupCount());

        Thread.sleep(10000l);
        group.stop();
        System.out.println(group.getName());
        System.out.println(group.getParent());
    }
}

class task implements Runnable {

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            Thread.sleep(3000l);
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("开始打印");
    }

}