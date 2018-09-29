package com.dyhdyh.ffmpegjni;

import com.dyhdyh.ffmpegjni.exception.FFmpegException;
import com.dyhdyh.ffmpegjni.listener.OnFFmpegLoggerListener;
import com.dyhdyh.ffmpegjni.listener.OnFFmpegResultListener;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * @author dengyuhan
 *         created 2018/6/1 14:36
 */
public class FFmpegJNI {
    static {
        System.loadLibrary("ffmpeg");
        System.loadLibrary("ffmpeg-jni");
    }

    private static FFmpegJNI mInstance;

    private FFmpegJNI() {
    }

    public static FFmpegJNI getInstance() {
        synchronized (FFmpegJNI.class) {
            if (mInstance == null) {
                mInstance = new FFmpegJNI();
            }
        }
        return mInstance;
    }

    private boolean mDebug;
    private OnFFmpegLoggerListener mLoggerListener;
    private OnFFmpegResultListener mResultListener;

    public void setLoggerListener(OnFFmpegLoggerListener listener) {
        this.mLoggerListener = listener;
    }

    public void setResultListener(OnFFmpegResultListener listener) {
        this.mResultListener = listener;
    }


    public void exec(String... command) {
        try {
            int returnCode = nativeExec(command, mLoggerListener);
            if (mResultListener != null) {
                if (returnCode == 0) {
                    mResultListener.onSuccess(returnCode);
                } else {
                    mResultListener.onError(returnCode);
                }
            }
            mLoggerListener = null;
            mResultListener = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Observable<Integer> execObservable(final String... command) {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(final ObservableEmitter<Integer> emitter) throws Exception {
                setResultListener(new OnFFmpegResultListener() {
                    @Override
                    public void onSuccess(int code) {
                        emitter.onNext(code);
                    }

                    @Override
                    public void onError(int code) {
                        emitter.onError(new FFmpegException(String.valueOf(code)));
                    }
                });
                exec(command);
                emitter.onComplete();
            }
        });
    }

    private native static int nativeExec(String[] command, OnFFmpegLoggerListener loggerListener);
}
