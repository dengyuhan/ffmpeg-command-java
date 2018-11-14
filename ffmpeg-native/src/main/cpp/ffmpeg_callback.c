#include <libavutil/log.h>
#include "ffmpeg_callback.h"
#include "com_dyhdyh_ffmpegjni_FFmpegJNI.h"
#include "android_log.h"

/**
 * 日志回调
 */
void log_callback(int level, const char *message) {
    callback_java_log(level, message);
}

/**
 * 进度回调(微秒)
 */
void progress_callback(long cur_us) {
    callback_java_progress((float) (cur_us / 1000.0));
}