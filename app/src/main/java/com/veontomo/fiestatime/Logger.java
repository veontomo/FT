package com.veontomo.fiestatime;

import android.util.Log;

/**
 * Application logger
 *
 *
 */
public abstract class Logger {

    /**
     * log no messages
     */
    private static final int LOG_LEVEL_NONE = 0;

    /**
     * log all messages
     */
    private static final int LOG_LEVEL_ALL = 1;


    private static final int LOG_LEVEL = LOG_LEVEL_ALL;

    public static final void log(String message){
        if (LOG_LEVEL == LOG_LEVEL_ALL){
            Log.i(Config.APP_NAME, message);
        }
    }
}
