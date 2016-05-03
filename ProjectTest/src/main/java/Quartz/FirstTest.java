package Quartz;

import java.util.Date;
import static org.quartz.JobBuilder.*;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.SimpleTriggerImpl;

import static org.quartz.JobBuilder.*;
import static org.quartz.JobBuilder.*;
import static org.quartz.JobBuilder.*;
import static org.quartz.JobBuilder.*;
import static org.quartz.JobBuilder.*;
public class FirstTest {

    @org.junit.Test
    public void Test() throws Exception {
        // 通过过JobDetail封装HelloJob，同时指定Job在Scheduler中所属组及名称，这里，组名为group1，而名称为job1。  
        //        ①创建一个JobDetail实例，指定SimpleJob
        JobDetail jobDetail = new JobDetailImpl("job1_1", "jGroup1", MyJob.class);
        //②通过SimpleTrigger定义调度规则：马上启动，每2秒运行一次，共运行100次
        SimpleTriggerImpl simpleTrigger = new SimpleTriggerImpl("trigger1_1", "tgroup1");
        simpleTrigger.setStartTime(new Date());
        simpleTrigger.setRepeatInterval(2000);
        simpleTrigger.setRepeatCount(100);
        //③通过SchedulerFactory获取一个调度器实例
//        一个scheduler维护一个JobDetail和一个或者多个Trigger的注册。一旦注册，scheduler负责在job关联的Trigger触发时(当调度到达时间)执行该job。
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        //Scheduler scheduler= schedulerFactory.getScheduler("DefaultQuartzScheduler");
        Scheduler scheduler = schedulerFactory.getScheduler();
        Scheduler scheduler2 = schedulerFactory.getScheduler("DefaultQuartzScheduler");
        System.out.println(scheduler2 == null);
        scheduler.scheduleJob(jobDetail, simpleTrigger);//④ 注册并进行调度
        
        scheduler.start();//⑤调度启动
        //Thread.sleep(Integer.MAX_VALUE);
    }

    public static void main(String[] args) throws Exception {
        new FirstTest().Test();
    }
}
