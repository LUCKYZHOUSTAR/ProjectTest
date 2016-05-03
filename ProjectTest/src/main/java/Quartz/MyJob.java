package Quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;

public class MyJob implements Job {
    public void execute(JobExecutionContext context) {
        try {
            System.out.println(context.getScheduler().getSchedulerName());
            System.out.println(context.getFireInstanceId());
            System.out.println(context.getJobRunTime());
            System.out.println(context.getJobInstance());
            System.out.println(context.getJobDetail().getDescription());
            String name = context.getJobDetail().getJobDataMap().getString("name");
            System.out.println(name);
            System.err.println("the second MyJob is executing.");
        } catch (SchedulerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

}
