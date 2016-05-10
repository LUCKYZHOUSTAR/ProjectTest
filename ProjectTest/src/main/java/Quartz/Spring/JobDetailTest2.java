/**
 * 
 */
package Quartz.Spring;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author LUCKY
 *
 */
public class JobDetailTest2 extends QuartzJobBean {
    private static int counter = 0;  
    //考虑到并发的情况，需要加锁
    public synchronized void run() {
        System.out.println("开启job任务操作");
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println();
        long ms = System.currentTimeMillis();
        System.out.println("\t\t" + new Date(ms));
        System.out.println(ms);
        System.out.println("(" + counter++ + ")");
        String s = (String) context.getMergedJobDataMap().get("service");
        System.out.println(s);
        System.out.println();
    }
}
