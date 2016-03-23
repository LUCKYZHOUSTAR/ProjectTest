/**     
 * @FileName: CallAbleTest.java   
 * @Package:com.executortest   
 * @Description: TODO  
 * @author: LUCKY    
 * @date:2016年3月23日 上午9:49:48   
 * @version V1.0     
 */
package com.executortest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**  
 * @ClassName: CallAbleTest   
 * @Description: TODO  
 * @author: LUCKY  
 * @date:2016年3月23日 上午9:49:48     
 */
public class CallAbleTest {

    public static void main(String[] args) throws ExecutionException {
//        ExecutorService executor = Executors.newCachedThreadPool();
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Executors.newScheduledThreadPool(3);
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            userList.add(new User(String.valueOf(i)));
        }

        try {
            //使用invokeAny()方法，当任意一个线程找到结果之后，立刻终结所有线程  
//            String result = executor.invokeAny(userList);
            // 调用该方法的线程会阻塞,直到tasks全部执行完成(正常完成/异常退出)
//            List<Future<String>> resultList = executor.invokeAll(userList);

            List<Future<String>> resultList = executor.invokeAll(userList,1,TimeUnit.SECONDS);
           
          
            /**
             * 可以通过Future.isCanceled()判断任务是被取消,还是完成(正常/异常)<br>
             * Future.isDone()总是返回true,对于invokeAll()的调用者来说,没有啥用
             */
            for (Future<String> f : resultList)
            {
              // isCanceled=false,isDone=true
              System.out.println("isCanceled=" + f.isCancelled() + ",isDone="
                  + f.isDone());

              // ExceptionCallable任务会报ExecutionException,如果线程没有执行结果的话
//              System.out.println("task result=" + f.get());
            }
            executor.shutdown();
            
            // 任务列表中所有任务执行完毕,才能执行该语句
//            System.out.println("返回结果了可以结束线程了"+result);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
    }

}

class User implements Callable<String> {

    /* (non-Javadoc)   
     * @return
     * @throws Exception   
     * @see java.util.concurrent.Callable#call()   
     */
    private String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public String call() throws Exception {
        // TODO Auto-generated method stub
        System.out.println("我的名字是"+this.name);
        Thread.sleep(200);
        return this.name;
    }

}
