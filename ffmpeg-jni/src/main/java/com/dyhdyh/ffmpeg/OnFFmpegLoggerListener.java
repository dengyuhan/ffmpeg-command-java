package com.dyhdyh.ffmpeg;

/**
 * @author dengyuhan
 *         created 2018/6/4 18:20
 */
public interface OnFFmpegLoggerListener {

    void onPrint(int level, byte[] messageByteArray);
}
