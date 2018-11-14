package com.dyhdyh.ffmpegjni.listener.impl;

import android.util.Log;

import com.dyhdyh.ffmpegjni.listener.OnFFmpegLoggerListener;

/**
 * @author dengyuhan
 * created 2018/6/5 22:58
 */
public class SimpleFFmpegLoggerListener implements OnFFmpegLoggerListener {
    private final String TAG = "FFmpegJNI";

    private boolean mLoggerEnable;

    public SimpleFFmpegLoggerListener(boolean loggerEnable) {
        this.mLoggerEnable = loggerEnable;
    }

    @Override
    public void onPrint(int level, byte[] messageByteArray) {
        if (mLoggerEnable) {
            String message = new String(messageByteArray);
            onPrintMessage(level, message);
        }
    }

    public void onPrintMessage(int level, String message) {
        String tag = String.format("%s - %s", TAG, Thread.currentThread().getName());
        if (level <= AV_LOG_WARNING) {
            Log.e(tag, message);
        } else {
            Log.d(tag, message);
        }
    }


}
