package com.db.portforward.utils;

import java.util.concurrent.*;

/**
 * @author Maxim Galushka
 * @since 08.12.2010
 */
public class ThreadUtils {

    private ExecutorService pool = null;
    private static final ThreadUtils instance = new ThreadUtils();

    private ThreadUtils(){
        // TODO: take number of thread from config
        //this.pool = Executors.newFixedThreadPool(100);
        this.pool = Executors.newScheduledThreadPool(100);
    }

    public static ThreadUtils getInstance(){
        return instance;
    }

    public void scheduleThread(Runnable r){
        pool.execute(r);
    }

    public ScheduledFuture scheduleAtFixedRate(Runnable r, int period, TimeUnit timeUnit){
        return ((ScheduledExecutorService)pool).scheduleAtFixedRate(r, 0, period, timeUnit);
    }
}
