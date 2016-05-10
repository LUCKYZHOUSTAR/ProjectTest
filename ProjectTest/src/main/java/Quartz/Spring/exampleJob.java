/**
 * 
 */
package Quartz.Spring;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author LUCKY
 *
 */
public class exampleJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println("运行起来了");
        System.out.println("运行时的参数" + context.getJobDetail().getJobDataMap().get("name"));
    }

}
