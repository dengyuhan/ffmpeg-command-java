package com.dyhdyh.ffmpeg.exception;

/**
 * @author dengyuhan
 *         created 2018/6/6 00:02
 */
public class FFmpegException extends Exception{
    public FFmpegException() {
    }

    public FFmpegException(String message) {
        super(message);
    }

    public FFmpegException(String message, Throwable cause) {
        super(message, cause);
    }

    public FFmpegException(Throwable cause) {
        super(cause);
    }

}
