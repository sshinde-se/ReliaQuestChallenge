package com.reliaquest.api.util;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomLogger {
    private Logger logger;
    public CustomLogger(Class<?> clazz){
        logger = LoggerFactory.getLogger(clazz);
    }
    public void warning(String msg){
        logger.warn(msg);
    }
    public void warning(Exception e){
        logger.warn(e.getMessage(), e);
    }
    public void error(String msg){
        logger.error(msg);
    }
    public void error(Exception e){
        logger.error(e.getMessage(), e);
    }
    public void error(String msg, Exception e){
        logger.error(msg,e);
    }
    public void trace(Exception e){
        logger.trace(e.getMessage(),e);
    }
    public void trace(String msg){
        logger.trace(msg);
    }
    public void trace(String msg, Exception e){
        logger.trace(msg,e);
    }
    public void info(String msg){
        logger.info(msg);
    }
    public void debug(String msg){
        logger.debug(msg);
    }
    public void debug(Exception e){
        logger.debug(e.getMessage(), e);
    }
    public StopWatch startStopWatch(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        return stopWatch;
    }
    public long logEndTime(StopWatch stopWatch,String message){
        stopWatch.stop();
        final long time = stopWatch.getTime();
        String timeString = stopWatch.toString();
        info(message + ": "+ time +"=>"+ timeString);
        return time;
    }
}
