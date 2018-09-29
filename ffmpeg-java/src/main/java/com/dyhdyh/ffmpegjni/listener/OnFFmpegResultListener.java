package com.dyhdyh.ffmpegjni.listener;

/**
 * @author dengyuhan
 *         created 2018/6/5 23:32
 */
public interface OnFFmpegResultListener {
    void onSuccess(int code);

    void onError(int code);
}
