package com.stronger.study.loader.log;

/**
 *
 * Logger Test
 * @author x8140
 */
public interface MyLogger {


    /**
     * Debug级别的Log
     * @param title
     * @param body
     * @return
     */
    int logDebug(String title,String body);
    /**
     * Info级别的Log
     * @param title
     * @param body
     * @return
     */
    int logInfo(String title,String body);
    /**
     * Warning级别的Log
     * @param title
     * @param body
     * @return
     */
    int logWarning(String title,String body);
    /**
     * Error级别的Log
     * @param title
     * @param body
     * @return
     */
    int logError(String title,String body);




}
