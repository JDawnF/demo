package com.baichen.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Program: HelloScheduler
 * @Author: baichen
 * @Description:
 */
public class HelloScheduler {
    public static void main(String[] args) throws SchedulerException, InterruptedException {
        // 打印当前的执行时间
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("HelloScheduler 当前时间：" + sf.format(date));
        // 创建一个JobDetail实例，将该实例与HelloJob Class绑定,需要指定唯一标识Identity
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
                .withIdentity("myJob")
//                .usingJobData("message", "hello job1")  // 传入自定义参数,在Job中获取
//                .usingJobData("FloatJobValue", 3.14F)
                .build();
        // 获取距离当前时间3秒后的时间,先获取当前时间戳，单位是毫秒，然后加3000
//        date.setTime(date.getTime() + 3000);
        // 获取距离当前时间6秒后的时间,先获取当前时间戳，单位是毫秒，然后加6000
//        Date endDate = new Date();
//        endDate.setTime(date.getTime() + 6000);


        /*System.out.println("jobDetail's name:"+jobDetail.getKey().getName());
        System.out.println("jobDetail's group:"+jobDetail.getKey().getGroup());
        System.out.println("jobDetail's jobClass:"+jobDetail.getJobClass().getName());*/

        // 创建一个Trigger实例(实际上是SimpleTrigger)，定义该job立即执行，并且每隔两秒钟重复执行一次，直到永远
//        Trigger trigger = (Trigger) TriggerBuilder
//                .newTrigger()
//                .withIdentity("myTrigger", "group1") // 唯一标识
////                .usingJobData("message","hello myTrigger")
////                .usingJobData("DoubleTriggerValue",2.0D)
////                .startNow() //立即执行
////                .startAt(date)        // 开始执行时间
////                .endAt(endDate)     // 最后执行时间
//                .withSchedule(
//                        SimpleScheduleBuilder.simpleSchedule()
//                                .withIntervalInSeconds(2).repeatForever())
//                .build();
//        date.setTime(date.getTime() + 4000);
        // 距离当前时间4秒钟后执行并且只执行一次任务
//        SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger()
//                .withIdentity("mySimpleTrigger", "group1")
//                .startAt(date)      // 时间优先于次数
//                .withSchedule(SimpleScheduleBuilder
//                        .simpleSchedule().withIntervalInSeconds(2)      // 每隔2秒
//                        .withRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY))    // 执行无数次,可以修改为具体的次数
//                .build();

        CronTrigger trigger = (CronTrigger) TriggerBuilder
                .newTrigger()
                .withIdentity("myTrigger", "group1")
                .withSchedule(CronScheduleBuilder
                        .cronSchedule("* * * * * ? *"))     // * * * * * ? *:使用CronTrigger每秒钟执行一次
                .build();
        //1.2018年内每天13点30分触发一次: 0 30 13 ? * * 2019
        //2.每天的14点整至14点59分55秒，以及18点59分55秒，每5秒钟触发一次: 0/5 * 14,18 * * ?

        // 创建Scheduler实例,通过工厂模式创建
        SchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        scheduler.start();
        // 绑定到scheduler，调度任务
//        scheduler.scheduleJob(jobDetail, trigger);
        System.out.println("scheduled时间是："+sf.format(scheduler.scheduleJob(jobDetail, trigger)));
        //  scheduled执行2秒后挂起
        Thread.sleep(2000L);
        scheduler.standby();    // 可以重新开启
        // 可以传入true/false，当传入参数为true时，表示等待所有正在执行的job执行完毕后，再关闭scheduler;传入false时表示直接关闭scheduler
        scheduler.shutdown();
        // scheduled挂起3秒后继续执行
        Thread.sleep(2000L);
        scheduler.start();
    }
}
