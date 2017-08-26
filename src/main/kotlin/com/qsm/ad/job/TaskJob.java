package com.qsm.ad.job;

import com.qsm.ad.pageprocessor.QsmADPageProcessor;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import java.text.ParseException;

/**
 * Created by think on 2017/8/20.
 */
public class TaskJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Spider.create(new QsmADPageProcessor()).addUrl().addPipeline((it, i)->{

        }).run();
        System.out.printf("job test");
    }
}
