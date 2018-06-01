package com.dyhdyh.ffmpeg;

/**
 * @author dengyuhan
 *         created 2018/6/1 14:36
 */
public class FFmpegJNI {
    static {
        System.loadLibrary("ffmpeg");
        System.loadLibrary("ffmpeg-jni");
    }

    public static int exec(String... command) {
        return nativeExec(command);
    }

    private native static int nativeExec(String... command);
}
