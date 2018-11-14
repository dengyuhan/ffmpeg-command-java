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
    public void onPrint(int level, byte[] messageByteArray) {
        super.onPrint(level, messageByteArray);
    }

}
