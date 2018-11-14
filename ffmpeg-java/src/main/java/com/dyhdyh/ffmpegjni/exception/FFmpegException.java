package com.dyhdyh.ffmpegjni.exception;

/**
 * @author dengyuhan
 * created 2018/6/6 00:02
 */
public class FFmpegException extends Exception {
    private int code;

    public FFmpegException(int code) {
        super("ffmpeg error , code = " + code);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
