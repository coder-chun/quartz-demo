package base;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Job extends QuartzJobBean {

    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public void schedulerJob(){
        System.out.println("=========定时输出:\t"+df.format(new Date()));
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        schedulerJob();
    }
}
