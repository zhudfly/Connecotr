package com.zhudfly.utilsconnector.utils;

import android.util.Log;

/**
 * 打印Log相关的辅助类，主要是控制Debug/Release下是否打印
 * Created by zhudf on 2018/5/9.
 */

public class LogUtil {
    
    private static boolean isDebug = false;
    private static final String TAG = "Utils Connector";

    public static void printStackTrace(String TAG, Exception e) {
        if (isDebug) {
            LogUtil.e(e);
        } else {
            logException(TAG, e);
        }
    }

    public static void printStackTrace(Exception e) {
        if (isDebug) {
            LogUtil.e(e);
        } else {
            logException(TAG, e);
        }
    }

    /**
     * 记录错误日志 (预留，不同的项目需求不同)
     * @param TAG tag
     * @param ex error
     */
    private static void logException(String TAG, Throwable ex) {

    }

    public static void d(String tag, String msg) {
        if (isDebug) {
            Log.d(tag, msg);
        }
    }

    public static void d(String msg) {
        Log.d(TAG, msg);
    }

    /**
     * Send a {@link #} log message and log the exception.
     *
     * @param tag Used to identify the source of a log message. It usually
     * identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static void d(String tag, String msg, Throwable tr) {
        if (isDebug) {
            Log.d(tag, msg, tr);
        }
    }

    public static void e(Throwable tr) {
        if (isDebug) {
            Log.e(TAG, "", tr);
        }
    }

    public static void i(String msg) {
        if (isDebug) {
            Log.i(TAG, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (isDebug) {
            Log.i(tag, msg);
        }
    }

    /**
     * Send a {@link #} log message and log the exception.
     *
     * @param tag Used to identify the source of a log message. It usually
     * identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static void i(String tag, String msg, Throwable tr) {
        if (isDebug) {
            Log.i(tag, msg, tr);
        }
    }

    /**
     * Send an {@link #} log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     * identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.e(tag, msg);
        }
    }

    public static void e(String msg) {
        if (isDebug) {
            Log.e(TAG, msg);
        }
    }

    /**
     * Send a {@link #} log message and log the exception.
     *
     * @param tag Used to identify the source of a log message. It usually
     * identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static void e(String tag, String msg, Throwable tr) {
        if (isDebug) {
            Log.e(tag, msg, tr);
        }
    }

    public static void e(String msg, Throwable tr) {
        if (isDebug) {
            Log.e(TAG, msg, tr);
        }
    }

    public static void setIsDebug(boolean isDebug) {
        LogUtil.isDebug = isDebug;
    }
}
