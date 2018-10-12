#include <libavutil/log.h>
#include "ffmpeg_callback.h"
#include "android_log.h"

int isDebug;

void set_log_callback(int debug) {
    isDebug = debug;
}

void log_callback(int level, const char *message) {
    if (isDebug != 0) {
        if (level <= AV_LOG_WARNING) {
            LOGE("%s", message);
        } else {
            LOGD("%s", message);
        }
    }

}