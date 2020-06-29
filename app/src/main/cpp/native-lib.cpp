#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_content_demo_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "wechatwebserver/";
    return env->NewStringUTF(hello.c_str());
}
