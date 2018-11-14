package com.dyhdyh.ffmpegjni.listener.impl;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通过日志匹配进度的回调
 *
 * @author dengyuhan
 * created 2018/6/5 23:19
 */
public abstract class SimpleLoggerProgressListener extends SimpleFFmpegLoggerListener {

    private Pattern pattern = Pattern.compile("time=([\\d\\w:]{8}[\\w.][\\d]+)");

    //输入视频的时长
    private long mSourceDuration;
    private boolean mLoggerEnable;

    public SimpleLoggerProgressListener(boolean loggerEnable) {
        this(0, loggerEnable);
    }

    public SimpleLoggerProgressListener(long sourceDuration, boolean loggerEnable) {
        super(true);
        this.mSourceDuration = sourceDuration;
        this.mLoggerEnable = loggerEnable;
    }

    @Override
    public void onPrintMessage(int level, String message) {
        if (mLoggerEnable) {
            super.onPrintMessage(level, message);
        } else {
            if (mSourceDuration > 0) {
                Float progress = getProgress(message, mSourceDuration);
                if (progress != null) {
                    onProgress(progress);
                }
            }
        }
    }

    /**
     * @param progress 进度,当supportProgress为true的时候,才有值
     */
    protected abstract void onProgress(float progress);

    private Float getProgress(String message, long duration) {
        try {
            Matcher matcher = pattern.matcher(message);
            if (matcher.find()) {
                String tempTime = String.valueOf(matcher.group(1));
                String[] arrayTime = tempTime.split("[:|.]");

                long arrayTimeNumber = Long.parseLong(arrayTime[3]);
                long supplement = arrayTimeNumber * (3 - arrayTime[3].length()) * 10;//如果位数不足 手动补全
                long millisecond = arrayTime[3].length() == 3 ? arrayTimeNumber : supplement;

                long currentTime =
                        TimeUnit.HOURS.toMillis(Long.parseLong(arrayTime[0]))
                                + TimeUnit.MINUTES.toMillis(Long.parseLong(arrayTime[1]))
                                + TimeUnit.SECONDS.toMillis(Long.parseLong(arrayTime[2]))
                                + millisecond;
                float percent = (float) currentTime / duration;
                return percent;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
