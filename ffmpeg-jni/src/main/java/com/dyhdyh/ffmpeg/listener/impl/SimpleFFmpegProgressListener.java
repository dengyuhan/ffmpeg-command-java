package com.dyhdyh.ffmpeg.listener.impl;

import com.dyhdyh.ffmpeg.listener.OnFFmpegLoggerListener;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author dengyuhan
 *         created 2018/6/5 23:19
 */
public abstract class SimpleFFmpegProgressListener implements OnFFmpegLoggerListener {

    private Pattern pattern = Pattern.compile("time=([\\d\\w:]{8}[\\w.][\\d]+)");

    //输入视频的时长
    private long mSourceDuration;
    //日志回调
    private OnFFmpegLoggerListener mLoggerListener;

    public SimpleFFmpegProgressListener() {
    }

    public SimpleFFmpegProgressListener(long sourceDuration) {
        this.mSourceDuration = sourceDuration;
    }

    public SimpleFFmpegProgressListener(OnFFmpegLoggerListener loggerListener) {
        this.mLoggerListener = loggerListener;
    }

    @Override
    public void onPrint(int level, byte[] messageByteArray) {
        if (mLoggerListener != null) {
            mLoggerListener.onPrint(level, messageByteArray);
        }
        if (mSourceDuration > 0) {
            String message = new String(messageByteArray);
            Float progress = getProgress(message, mSourceDuration);
            if (progress == null) {
                onProgress(false, progress);
            } else {
                onProgress(true, progress);
            }
        }
    }

    /**
     * @param supportProgress 是否支持获取进度
     * @param progress        进度,当supportProgress为true的时候,才有值
     */
    protected abstract void onProgress(boolean supportProgress, float progress);

    private Float getProgress(String message, long duration) {
        try {
            if (message.contains("speed")) {
                Matcher matcher = pattern.matcher(message);
                if (matcher.find()) {
                    String tempTime = String.valueOf(matcher.group(1));
                    String[] arrayTime = tempTime.split("[:|.]");
                    long currentTime =
                            TimeUnit.HOURS.toMillis(Long.parseLong(arrayTime[0]))
                                    + TimeUnit.MINUTES.toMillis(Long.parseLong(arrayTime[1]))
                                    + TimeUnit.SECONDS.toMillis(Long.parseLong(arrayTime[2]))
                                    + Long.parseLong(arrayTime[3]);
                    float percent = (float) currentTime / duration;
                    return percent;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
