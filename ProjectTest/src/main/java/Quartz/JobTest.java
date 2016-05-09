package Quartz;


public class JobTest {

    public static void main(String[] args) {
//        JobDetail job = newJob(MyJob.class).withIdentity("myJob", "group1") // name "myJob", group "group1"
//            .build();
//
//        // Trigger the job to run now, and then every 40 seconds
//        Trigger trigger = newTrigger().withIdentity("myTrigger", "group1").startNow()
//            .withSchedule(simpleSchedule().withIntervalInSeconds(40).repeatForever()).build();
//
//        // Tell quartz to schedule the job using our trigger
//        sched.scheduleJob(job, trigger);
    }

    public void test() {
        // define the job and tie it to our HelloJob class
//        JobDetail job = newJob(MyJob.class).withIdentity("myJob", "group1").usingJobData("aa","dd") // name "myJob", group "group1"
//            .build();
////        JobFactory
//        // Trigger the job to run now, and then every 40 seconds
//        Trigger trigger = newTrigger().withIdentity("myTrigger", "group1").startNow()
//            .withSchedule(simpleSchedule().withIntervalInSeconds(40).repeatForever()).build();

        // Tell quartz to schedule the job using our trigger
//        sched.scheduleJob(job, trigger);
    }
}
