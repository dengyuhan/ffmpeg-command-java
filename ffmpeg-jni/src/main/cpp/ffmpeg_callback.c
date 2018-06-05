#include <string.h>
#include "ffmpeg_callback.h"
#include "android_log.h"

JNIEnv *env;
jobject jcallback;

void set_log_callback(JNIEnv *jniEnv, jobject obj) {
    jcallback = obj;
    env = jniEnv;
}

void log_callback(int level, const char *message) {
    jclass clazz = (*env)->GetObjectClass(env, jcallback);
    if (clazz) {
        jmethodID onPrintID = (*env)->GetMethodID(env, clazz, "onPrint", "(I[B)V");
        if (onPrintID) {
            int len = strlen(message);
            if (len > 0) {
                jbyteArray message_bytes = (*env)->NewByteArray(env, len);
                (*env)->SetByteArrayRegion(env, message_bytes, 0, len, (jbyte *) message);
                (*env)->CallVoidMethod(env, jcallback, onPrintID, (jint) level, message_bytes);
                (*env)->DeleteLocalRef(env, message_bytes);
            }
        }
        (*env)->DeleteLocalRef(env, clazz);
    }

}


void test(const char *text) {
    int len = strlen(text);
    if (len > 0) {
        jbyteArray text_bytes = (*env)->NewByteArray(env, len);
        (*env)->SetByteArrayRegion(env, text_bytes, 0, len, (jbyte *) text);
        (*env)->CallVoidMethod(env, jcallback, text_bytes);
        (*env)->DeleteLocalRef(env, text_bytes);
    }
}

