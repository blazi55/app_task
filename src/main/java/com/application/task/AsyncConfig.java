package com.application.task;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

@Configuration
public class AsyncConfig extends AsyncConfigurerSupport {

    @Autowired
    private AsyncExceptionHandler asyncExceptionHandler;

    @Override
    public Executor getAsyncExecutor() {
        return new ThreadPoolExecutor(0, 2,
                60L, TimeUnit.MILLISECONDS, new SynchronousQueue < Runnable > ());
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return asyncExceptionHandler;
    }
}
