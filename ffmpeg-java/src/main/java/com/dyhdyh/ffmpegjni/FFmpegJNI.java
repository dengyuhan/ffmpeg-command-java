package com.dyhdyh.ffmpegjni;

import android.os.Handler;
import android.util.Log;

import com.dyhdyh.ffmpegjni.exception.FFmpegException;
import com.dyhdyh.ffmpegjni.listener.OnFFmpegLoggerListener;
import com.dyhdyh.ffmpegjni.listener.OnFFmpegProgressListener;
import com.dyhdyh.ffmpegjni.listener.OnFFmpegResultListener;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * @author dengyuhan
 * created 2018/6/1 14:36
 */
public class FFmpegJNI {

    static {
        System.loadLibrary("ffmpeg");
        System.loadLibrary("ffmpeg-jni");
    }


    private FFmpegJNI() {
        mMainHandler = new Handler();
    }

    public static FFmpegJNI getInstance() {
        synchronized (FFmpegJNI.class) {
            if (mInstance == null) {
                mInstance = new FFmpegJNI();
            }
        }
        return mInstance;
    }

    private static FFmpegJNI mInstance;

    private boolean mDebug;
    private final String TAG = "FFmpegJNI";
    private Handler mMainHandler;
    private OnFFmpegProgressListener mProgressListener;
    private OnFFmpegLoggerListener mLoggerListener;
    private OnFFmpegResultListener mResultListener;

    public void setDebug(boolean debug) {
        this.mDebug = debug;
    }

    public FFmpegJNI setLoggerListener(OnFFmpegLoggerListener listener) {
        this.mLoggerListener = listener;
        nativeSetLoggerListener(mLoggerListener);
        return this;
    }

    public FFmpegJNI setResultListener(OnFFmpegResultListener listener) {
        this.mResultListener = listener;
        return this;
    }

    public FFmpegJNI setOnProgressListener(OnFFmpegProgressListener listener) {
        this.mProgressListener = listener;
        nativeSetProgressListener(new OnFFmpegProgressListener() {
            @Override
            public void onProgress(float progressMillisecond) {
                mMainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mProgressListener.onProgress(progressMillisecond);
                    }
                });
            }
        });
        return this;
    }

    public void exec(FFmpegStringBuilder builder) {
        if (builder != null) {
            exec(builder.toArray());
        }
    }

    public void exec(List<String> command) {
        if (command != null) {
            exec(command.toArray(new String[0]));
        }
    }

    public void exec(String... command) {
        try {
            if (command == null || command.length <= 0) {
                return;
            }

            final long startMillis = System.currentTimeMillis();
            if (mDebug) {
                StringBuilder sb = new StringBuilder();
                for (String item : command) {
                    sb.append(item);
                    sb.append(" ");
                }
                Log.d(TAG, sb.toString());
            }

            int returnCode = nativeExec(command);

            if (mDebug) {
                Log.d(TAG, "耗时：" + (System.currentTimeMillis() - startMillis) + "ms");
            }

            if (mResultListener != null) {
                if (returnCode == 0) {
                    mResultListener.onSuccess(returnCode);
                } else {
                    mResultListener.onError(new FFmpegException(returnCode));
                }
            }

            release();
        } catch (Exception e) {
            if (mResultListener != null) {
                mResultListener.onError(e);
            }
        }
    }

    public Observable<Integer> execObservable(FFmpegStringBuilder builder) {
        return execObservable(builder == null ? new String[0] : builder.toArray());
    }

    public Observable<Integer> execObservable(List<String> command) {
        return execObservable(command == null ? new String[0] : command.toArray(new String[0]));
    }

    public Observable<Integer> execObservable(final String... command) {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(final ObservableEmitter<Integer> emitter) {
                setResultListener(new OnFFmpegResultListener() {
                    @Override
                    public void onSuccess(int code) {
                        emitter.onNext(code);
                    }

                    @Override
                    public void onError(Exception e) {
                        emitter.onError(e);
                    }
                }).exec(command);
                emitter.onComplete();
            }
        });
    }

    protected void release() {
        mLoggerListener = null;
        mProgressListener = null;
        mResultListener = null;
        nativeRelease();
    }


    /**
     * 命令行执行
     *
     * @param command
     * @return
     */
    private native static int nativeExec(String[] command);

    /**
     * 日志监听
     *
     * @param listener
     */
    private native static void nativeSetLoggerListener(OnFFmpegLoggerListener listener);

    /**
     * 进度监听
     *
     * @param listener
     */
    private native static void nativeSetProgressListener(OnFFmpegProgressListener listener);

    /**
     * 释放
     */
    private native static void nativeRelease();

    public native static String avcodecInfo();

    public native static String avformatInfo();

    public native static String avfilterInfo();

    public native static String configurationInfo();
}
