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
    public static void main(String[] args) throws SchedulerException {
        // 创建一个JobDetail实例，将该实例与HelloJob Class绑定,需要指定唯一标识Identity
        JobDetail jobDetail= JobBuilder.newJob(HelloJob.class)
                .withIdentity("myJob","group1").build();
        // 创建一个Trigger实例，定义该job立即执行，并且每隔两秒钟重复执行一次，直到永远
        Trigger trigger= (Trigger) TriggerBuilder
                .newTrigger()
                .withIdentity("myTrigger","group1") // 唯一标识
                .startNow() //立即执行
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(2).repeatForever())
                .build();
        // 创建Scheduler实例,通过工厂模式创建
        SchedulerFactory factory=new StdSchedulerFactory();
        Scheduler scheduler=factory.getScheduler();
        scheduler.start();
        // 打印当前的执行时间
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("HelloScheduler 当前时间：" + sf.format(date));
        scheduler.scheduleJob(jobDetail,trigger);
    }
}
