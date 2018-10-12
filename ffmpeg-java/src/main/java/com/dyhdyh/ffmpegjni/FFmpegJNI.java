package com.dyhdyh.ffmpegjni;

import android.util.Log;

import com.dyhdyh.ffmpegjni.exception.FFmpegException;
import com.dyhdyh.ffmpegjni.listener.OnFFmpegLoggerListener;
import com.dyhdyh.ffmpegjni.listener.OnFFmpegResultListener;

import java.util.List;

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
    private final String TAG = getClass().getSimpleName();
    private OnFFmpegLoggerListener mLoggerListener;
    private OnFFmpegResultListener mResultListener;

    public void setDebug(boolean debug) {
        this.mDebug = debug;
    }

    @Deprecated
    private void setLoggerListener(OnFFmpegLoggerListener listener) {
        this.mLoggerListener = listener;
    }

    public void setResultListener(OnFFmpegResultListener listener) {
        this.mResultListener = listener;
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

            int returnCode = nativeExec(command, mDebug);

            if (mDebug) {
                Log.d(TAG, "耗时：" + (System.currentTimeMillis() - startMillis) + "ms");
            }

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

    public Observable<Integer> execObservable(FFmpegStringBuilder builder) {
        return execObservable(builder == null ? new String[0] : builder.toArray());
    }

    public Observable<Integer> execObservable(List<String> command) {
        return execObservable(command == null ? new String[0] : command.toArray(new String[0]));
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

    private native static int nativeExec(String[] command, boolean debug);

    public native static String avcodecInfo();

    public native static String avformatInfo();

    public native static String avfilterInfo();

    public native static String configurationInfo();
}
