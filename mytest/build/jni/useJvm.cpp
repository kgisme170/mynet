#include<jni.h>
#include<stdio.h>
#include<stdlib.h>
class useJvm {
    static const char option[] = "-Djava.class.path=.:..";
    JavaVMOption options[1];
    JavaVM* jvm;
    JNIEnv *env;
    jclass cls;
public:
    useJvm(const char* javaClassName) {
        options[0].optionString = option;
        JavaVMInitArgs vm_args = {0};
        vm_args.version = JNI_VERSION_1_8;
        vm_args.nOptions = 1;
        vm_args.options = options;

        long status = JNI_CreateJavaVM(&jvm, (void**)&env, &vm_args);
        if (status == JNI_ERR) {
            printf("Create jvm failed\n");
            exit(1);
        }
        cls = env->FindClass("cpp2java");
        if (cls == NULL) {
            printf("find class failed\n");
        }
        if (env->ExceptionOccurred()) // check if an exception occurred
        {
            env->ExceptionDescribe(); // print the stack trace
            exit(2);
        }
    }
    ~useJvm() {
        jvm->DestroyJavaVM();
    }
};
