#include <jni.h>
#include <string>

extern "C"
jstring
Java_edu_bjtu_lab1_1iot_SensorReader_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
