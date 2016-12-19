package com.infotree.qliktest.admin.schedular;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
 
public class QuartzJob extends QuartzJobBean {
    private MyTask myTask;
 
    public void setMyTask(MyTask myTask) {
        this.myTask = myTask;
    }
 
    protected void executeInternal(JobExecutionContext context)
        throws JobExecutionException {
        myTask.sayHello();
    }
}
