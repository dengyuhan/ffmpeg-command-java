#ifdef ANDROID

#include <android/log.h>

#ifndef LOG_TAG
#define  MY_TAG   "FFMPEG_JNI"
#define  AV_TAG   "FFMPEG_JNI_AVLOG"
#endif
#define LOGE(format, ...)  __android_log_print(ANDROID_LOG_ERROR, MY_TAG, format, ##__VA_ARGS__)
#define LOGD(format, ...)  __android_log_print(ANDROID_LOG_DEBUG,  MY_TAG, format, ##__VA_ARGS__)
#define  AVLOGD(...)  __android_log_print(ANDROID_LOG_INFO,AV_TAG,__VA_ARGS__)
#define  AVLOGE(...)  __android_log_print(ANDROID_LOG_ERROR,AV_TAG,__VA_ARGS__)
#else
#define LOGE(format, ...)  printf(MY_TAG format "\n", ##__VA_ARGS__)
#define LOGD(format, ...)  printf(MY_TAG format "\n", ##__VA_ARGS__)
#define AVLOGE(format, ...)  fprintf(stdout, AV_TAG ": " format "\n", ##__VA_ARGS__)
#define AVLOGI(format, ...)  fprintf(stderr, AV_TAG ": " format "\n", ##__VA_ARGS__)
#endif