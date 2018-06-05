package com.dyhdyh.ffmpeg;

import android.util.Log;

import com.dyhdyh.ffmpeg.listener.OnFFmpegLoggerListener;
import com.dyhdyh.ffmpeg.listener.OnFFmpegProgressListener;
import com.dyhdyh.ffmpeg.listener.OnFFmpegResultListener;
import com.dyhdyh.ffmpeg.listener.impl.SimpleFFmpegLoggerListener;
import com.dyhdyh.ffmpeg.listener.impl.SimpleFFmpegProgressListener;

/**
 * @author dengyuhan
 *         created 2018/6/1 14:36
 */
public class FFmpegJNI {
    static {
        System.loadLibrary("ffmpeg");
        System.loadLibrary("ffmpeg-jni");
    }

    public static void exec(String[] command, OnFFmpegResultListener resultListener) {
        try {
            int returnCode = nativeExec(command, new OnFFmpegProgressListener() {

                @Override
                public void onProgress(float progress) {
                    Log.d("onProgress-------->", progress + "--->");
                }
            }, new SimpleFFmpegProgressListener(new SimpleFFmpegLoggerListener(true)) {
                @Override
                protected void onProgress(boolean supportProgress, float progress) {
                    Log.d("----------->", supportProgress + " " + progress);
                }
            });
            if (resultListener != null) {
                if (returnCode == 0) {
                    resultListener.onSuccess(returnCode);
                } else {
                    resultListener.onError(returnCode);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private native static int nativeExec(String[] command, OnFFmpegProgressListener progressListener, OnFFmpegLoggerListener loggerListener);
}
