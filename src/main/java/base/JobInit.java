package base;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
public class JobInit {

    private Scheduler scheduler;



    public void init() {
        Trigger trigger = null;
        try {
            TriggerKey triggerKeys = TriggerKey.triggerKey("testJobTrigger", Scheduler.DEFAULT_GROUP);
            trigger = scheduler.getTrigger(triggerKeys);
            CronTriggerImpl cronTrigger = (CronTriggerImpl)trigger;
            String expression = "* * * * * ?";
            cronTrigger.setCronExpression(expression);
            scheduler.rescheduleJob(triggerKeys, trigger);
        } catch (ParseException | SchedulerException e) {
            e.printStackTrace();
        }
    }


    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
}
