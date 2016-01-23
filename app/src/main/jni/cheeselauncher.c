//
// Created by cheesy on 1/8/16.
//

#include <string.h>
#include <jni.h>
#include <android/log.h>

#define LOG_TAG "CheeseLauncherNative"
#define LOG(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
static JavaVM *java_vm;

jint JNI_OnLoad(JavaVM* vm, void* what) {
    LOG("Ello! We're riding on the chocolate bar!");
    LOG(" *** CheeseLauncherNative has been started. ***");

    // Get the global java VM
    java_vm = vm;

    return JNI_VERSION_1_6;
}