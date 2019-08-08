package com.baichen.quartz;

import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Program: HelloQuartz
 * @Author: baichen
 * @Description:
 */
public class HelloJob implements Job {
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // 打印当前的执行时间
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("HelloJob 当前时间：" + sf.format(date));
        System.out.println("hello job");
        // 获取任务开始执行的时间和终止时间
        /*Trigger trigger = jobExecutionContext.getTrigger();
        System.out.println("开始时间：" + sf.format(trigger.getStartTime()));
        System.out.println("终止时间：" + sf.format(trigger.getEndTime()));
        JobKey jobKey = trigger.getJobKey();
        System.out.println("jobKey相关信息：" + jobKey.getName() + " " + jobKey.getGroup());*/
        // 编写具体的业务逻辑
//        JobKey key = jobExecutionContext.getJobDetail().getKey(); // 得到JobDetail唯一标识中的key
//        System.out.println("JobDetail获取唯一标识的键和值" + key.getName() + ":" + key.getGroup());
//        TriggerKey triggerKey = jobExecutionContext.getTrigger().getKey();
//        System.out.println("Trigger获取唯一标识的键和值" + triggerKey.getName() + ":" + triggerKey.getGroup());
//        方式一：
//        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
//        JobDataMap tDataMap = jobExecutionContext.getTrigger().getJobDataMap();
//        String trMessage = tDataMap.getString("message");
//        Float floatJobValue = dataMap.getFloat("FloatJobValue");
//        String jobMessage = dataMap.getString("message");
//        Double doubleTriggerValue = tDataMap.getDouble("DoubleTriggerValue");
//        System.out.println("job:" + jobMessage + "floatJobValue:" + floatJobValue);
//        System.out.println("trigger:" + trMessage + "doubleTriggerValue:" + doubleTriggerValue);
//        方式二,这样jobDetail和Trigger等信息都在这里,但是这样如果两者key相同，Trigger会覆盖jobDetail的：
//        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
//        方式三：也可以在定义三个成员变量，这三个成员变量对应Scheduler中的三个key，然后通过setter获取
    }
}
