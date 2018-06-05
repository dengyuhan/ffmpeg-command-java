package com.dyhdyh.ffmpeg.listener.impl;

import android.util.Log;

import com.dyhdyh.ffmpeg.listener.OnFFmpegLoggerListener;

/**
 * @author dengyuhan
 *         created 2018/6/5 22:58
 */
public class SimpleFFmpegLoggerListener implements OnFFmpegLoggerListener {
    private final String TAG = "FFmpeg-JNI";

    private boolean mLoggerEnable = true;

    public SimpleFFmpegLoggerListener(boolean loggerEnable) {
        this.mLoggerEnable = loggerEnable;
    }

    @Override
    public void onPrint(int level, byte[] messageByteArray) {
        String message = new String(messageByteArray);
        onPrintMessage(level, message);
    }

    public void onPrintMessage(int level, String message) {
        if (mLoggerEnable) {
            if (level <= AV_LOG_WARNING) {
                Log.e(TAG, message);
            } else {
                Log.d(TAG, message);
            }
        }
    }

}
