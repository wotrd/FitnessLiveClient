package com.example.wkj_pc.fitnesslive.tools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wkj on 2017/10/29.
 */
/** 线程工具类*/
public class ThreadPoolExecutorUtils {
    private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    private static ExecutorService fixedThreaPool =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2+1);
    private static ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
    private static ExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors()*2+1);
    private ThreadPoolExecutorUtils(){}//禁止实例化
    /**
     * @获取核心线程和最大线程相同的线程池
     * */
    public static ExecutorService getFixedThreadPoolExecutors(){
        return fixedThreaPool;
    }
    /**
     * @获取线程随着自身情况变化的线程池
     * */
    public static ExecutorService getCachedThreaPoolExecutors(){
        return cachedThreadPool;
    }
    /**
     * @获取单个线程的线程池，可以避免线程冲突
     * */
    public static ExecutorService getSingledThreaPoolExecutors(){
        return singleThreadPool;
    }
    /**
     * @获取单个线程的线程池，可以避免线程冲突
     * */
    public static ExecutorService getScheduledThreaPoolExecutors(){
        return scheduledThreadPool;
    }

}
