package base;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Job {
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public void schedulerJob(){
        System.out.println("=========定时输出:\t"+df.format(new Date()));
    }
}
