package com.baizhi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component
//@Async
//hei
public class TimerDownload {
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    public void run() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("123123");
            }
        };
        threadPoolTaskScheduler.schedule(runnable, new CronTrigger("0/5 * * * * *"));
    }

    public void shutdown() {
        threadPoolTaskScheduler.shutdown();
    }


}
