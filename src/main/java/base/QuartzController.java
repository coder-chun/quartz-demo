package base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 定时任务管理控制器
 */
@Controller
public class QuartzController {

    @Autowired
    private QuartzService2 quartzService2;

    @RequestMapping("testScheduler")
    public String testScheduler(){
        return "testScheduler";
    }

    /**
     * 修改定时任务执行时间
     * @param expression
     * @return
     */
    @RequestMapping("/changeScheduler")
    @ResponseBody
    public String changeScheduler(@RequestBody  String expression){
        System.out.println("执行时间被修改为:\t"+expression);
        quartzService2.resetJob(expression);
        return "SUCCESS";
    }

    /**
     * 停止定时任务
     * @return
     */
    @RequestMapping("/stopJob")
    @ResponseBody
    public String stopJob(){
        quartzService2.stopJob();
        return "SUCCESS";
    }

    /**
     * 开始定时任务
     * @return
     */
    @RequestMapping("/startJob")
    @ResponseBody
    public String startJob(){
        quartzService2.startJob();
        return "SUCCESS";
    }
}
