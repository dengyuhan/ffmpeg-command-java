package com.dyhdyh.ffmpegjni.example;

import com.dyhdyh.ffmpegjni.listener.impl.SimpleFFmpegLoggerListener;

/**
 * @author dengyuhan
 *         created 2018/6/5 23:14
 */
public class CustomLoggerListener extends SimpleFFmpegLoggerListener{
    public CustomLoggerListener(boolean loggerEnable) {
        super(loggerEnable);
    }

    @Override
    public void onPrintMessage(int level, String message) {
        //自定义要做的事情
    }
}
