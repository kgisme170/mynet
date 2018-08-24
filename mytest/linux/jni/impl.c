#include<jni.h>
#include<cpp2java.h>
#include<stdio.h>
JNIEXPORT void JNICALL Java_cpp2java_print
(JNIEnv *env , jclass cls){
    printf("hello\n");
}
