#include <jni.h>

void set_log_callback(JNIEnv *jniEnv, jobject jcallback);

void log_callback(int level, const char *message);


