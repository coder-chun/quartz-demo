package base;

import org.quartz.*;
import org.quartz.impl.triggers.CronTriggerImpl;
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
public class QuartzService implements ServletContextAware {
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private ServletContext context;

    @Override
    public void setServletContext(ServletContext context) {
        this.context = context;
    }

    public void schedulerJob() {
        System.out.println("=========定时输出:\t" + df.format(new Date()));
    }

    /**
     * 修改定时任务执行时间
     * @param expression
     */
    public void resetJob(String expression) {
        ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
        Scheduler scheduler = (Scheduler) applicationContext.getBean("testScheduler");
        Trigger trigger = null;
        try {
            TriggerKey triggerKeys = TriggerKey.triggerKey("testJobTrigger", Scheduler.DEFAULT_GROUP);
            trigger = scheduler.getTrigger(triggerKeys);
            CronTriggerImpl cronTrigger = (CronTriggerImpl)trigger;

            cronTrigger.setCronExpression(expression);
            scheduler.rescheduleJob(triggerKeys, trigger);
        } catch (ParseException | SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止定时任务
     */
    public void stopJob() {
        ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
        Scheduler scheduler = (Scheduler) applicationContext.getBean("testScheduler");
        SchedulerFactoryBean schedulerFactoryBean = (SchedulerFactoryBean) applicationContext.getBean("testScheduler");
        Scheduler scheduler1 = schedulerFactoryBean.getScheduler();
        Trigger trigger = null;
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey("testJobTrigger", Scheduler.DEFAULT_GROUP);
            Trigger trigger1 = scheduler1.getTrigger(triggerKey);
            System.out.println(trigger1);
            scheduler.pauseTrigger(triggerKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始执行定时任务
     */
    public void startJob() {
        ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
        Scheduler scheduler = (Scheduler) applicationContext.getBean("testScheduler");
        Trigger trigger = null;
        try {
            TriggerKey triggerKeys = TriggerKey.triggerKey("testJobTrigger", Scheduler.DEFAULT_GROUP);
            trigger = scheduler.getTrigger(triggerKeys);
            CronTriggerImpl cronTrigger = (CronTriggerImpl)trigger;
            JobKey jobKey = cronTrigger.getJobKey();
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            System.out.println(jobDetail);
            trigger.getTriggerBuilder().startNow();
            scheduler.rescheduleJob(triggerKeys, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}