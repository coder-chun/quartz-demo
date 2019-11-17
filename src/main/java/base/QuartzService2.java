package base;

import org.quartz.*;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class QuartzService2  {


    @Autowired
    private Scheduler testScheduler;

    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public void schedulerJob() {
        System.out.println("=========定时输出:\t" + df.format(new Date()));
    }

    /**
     * 修改定时任务执行时间
     * @param expression
     */
    public void resetJob(String expression) {
        Trigger trigger = null;
        try {
            TriggerKey triggerKeys = TriggerKey.triggerKey("testJobTrigger", Scheduler.DEFAULT_GROUP);
            trigger = testScheduler.getTrigger(triggerKeys);
            CronTriggerImpl cronTrigger = (CronTriggerImpl)trigger;

            cronTrigger.setCronExpression(expression);
            testScheduler.rescheduleJob(triggerKeys, trigger);
        } catch (ParseException | SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止定时任务
     */
    public void stopJob() {
        Trigger trigger = null;
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey("testJobTrigger", Scheduler.DEFAULT_GROUP);
            testScheduler.pauseTrigger(triggerKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始执行定时任务
     */
    public void startJob() {
        Trigger trigger = null;
        try {
            TriggerKey triggerKeys = TriggerKey.triggerKey("testJobTrigger", Scheduler.DEFAULT_GROUP);
            trigger = testScheduler.getTrigger(triggerKeys);
            CronTriggerImpl cronTrigger = (CronTriggerImpl)trigger;
            JobKey jobKey = cronTrigger.getJobKey();
            JobDetail jobDetail = testScheduler.getJobDetail(jobKey);
            System.out.println(jobDetail);
            trigger.getTriggerBuilder().startNow();
            testScheduler.rescheduleJob(triggerKeys, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}