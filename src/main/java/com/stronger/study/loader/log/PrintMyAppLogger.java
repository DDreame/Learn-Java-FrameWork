package com.stronger.study.loader.log;


/**
 *
 * @author x8140
 */
public class PrintMyAppLogger  implements MyLogger{
    public int logDebug(String title, String body) {
        System.out.println("------ Log Debug ------:: "+title+"\t:"+body);
        return 0;
    }

    public int logInfo(String title, String body) {
        System.out.println("------ Log Info ------:: "+title+"\t:"+body);
        return 0;
    }

    public int logWarning(String title, String body) {
        System.out.println("------ Log Warning ------:: "+title+"\t:"+body);
        return 0;
    }

    public int logError(String title, String body) {
        System.out.println("------ Log Error ------:: "+title+"\t:"+body);
        return 0;
    }
}
