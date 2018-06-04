
#include <jni.h>

class LoggerListener {
public:
    JNIEnv *env;
    jobject callback;

    LoggerListener::LoggerListener(JNIEnv *env, jobject callback) {
        this->env = env;
        this->callback = callback;
    }

    void log_callback(int level, char *message) {
        jclass clazz = env->GetObjectClass(callback);
        if (!clazz) {
            jmethodID onPrintID = env->GetMethodID(clazz, "onPrint", "(ILjava/lang/String)V");
            if (!onPrintID) {
                jstring jmessage = env->NewStringUTF(message);
                env->CallVoidMethod(callback, onPrintID, (jint) level, jmessage);
            }
        }
    }
};