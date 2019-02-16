#include<stdio.h>
#include<stdlib.h>
#include"useJvm.h"

char option[] = "-Djava.class.path=.:..";
int useJvm::printStackTrace() {
    jclass cls = env->FindClass("java/lang/Exception");
    if (cls != NULL) {
        jmethodID constructor = env->GetMethodID(cls, "<init>", "()V");
        if(constructor != NULL) {
            jobject exc = env->NewObject(cls, constructor);
            if(exc != NULL) {
                jmethodID printStackTrace = env->GetMethodID(cls, "printStackTrace", "()V");
                if(printStackTrace != NULL) {
                    env->CallObjectMethod(exc, printStackTrace);
                } else { return 4; }
            } else { return 3; }
            env->DeleteLocalRef(exc);
        } else { return 2; }
    } else { return 1; }
    /* free the local ref */
    env->DeleteLocalRef(cls);
    return 0;
}

useJvm::useJvm(const char* javaClassName) {
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
    cls = env->FindClass(javaClassName);
    if (cls == NULL) {
        printf("find class failed\n");
    }
    if (env->ExceptionOccurred()) // check if an exception occurred
    {
        env->ExceptionDescribe(); // print the stack trace
        exit(2);
    }
}

useJvm::~useJvm() {
    jvm->DestroyJavaVM();
}

// javap -s -p cpp2java.class
// call static methods

int useJvm::CallStaticFunction(const char* functionName, int parameter) {
    jmethodID mid = env->GetStaticMethodID(cls, functionName, "(I)I");
    if (mid) {
        return env->CallStaticIntMethod(cls, mid, parameter);
    } else {
        printf("find statis int method failed\n");
        exit(3);
    }
}

// arrayFunction begins
// public static int[] arrayFunc(int[]);
// descriptor: ([I)[I
// arrayFunction ends
jintArray useJvm::CallStaticFunction(const char* functionName, const int* parameter, const size_t size) {
    jmethodID mid = env->GetStaticMethodID(cls, functionName, "([I)[I");
    if (mid) {
        jintArray iarr = env->NewIntArray(size);
        env->SetIntArrayRegion(iarr, 0, size, parameter);
        jintArray array = (jintArray)env->CallStaticObjectMethod(cls, mid, iarr);
        return array;
    } else {
        printf("find statis int method failed\n");
        exit(3);
    }
}

bool useJvm::CallStaticFunction(const char* functionName, bool b) {
    jmethodID mid = env->GetStaticMethodID(cls, functionName, "(Z)Z");
    if (mid) {
        return env->CallStaticIntMethod(cls, mid, b);
    } else {
        printf("find statis int method failed\n");
        exit(3);
    }
}

void useJvm::CallStaticFunction(const char* functionName, const char* parameter) {
    jmethodID mid = env->GetStaticMethodID(cls, functionName, "(Ljava/lang/String;)[B");
    if (mid) {
        jstring js = env->NewStringUTF(parameter);
        jbyteArray obj = (jbyteArray)env->CallStaticObjectMethod(cls, mid, js);
        jbyte* data=env->GetByteArrayElements(obj,0);
        printf("%c%c\n",data[0],data[1]);
        env->ReleaseByteArrayElements(obj, data, 0);
    } else {
        printf("find static byte[] read(String) failed\n");
        exit(3);
    }
}

void useJvm::CallStaticThrow(const char* functionName) {
    jmethodID mid = env->GetStaticMethodID(cls, functionName, "()V");
    if (mid) {
        env->CallStaticVoidMethod(cls, mid);
        jthrowable t = env->ExceptionOccurred();
        if (t) {
            printf("------Got exception object\n");
            printStackTrace();
            env->ExceptionClear();
            env->DeleteLocalRef(t);
        } else {
            printf("exception not found\n");
        }
        jboolean flag = env->ExceptionCheck();
        if (flag) {
            env->ExceptionDescribe();
            printf("-----Got exception\n");
        }
    } else {
        printf("find static mythrow failed\n");
        exit(3);
    }
}

jobject useJvm::CreateObject() {
    jmethodID mid = env->GetMethodID(cls, "<init>", "()V");
    jobject obj;
    if (mid) {
        return env->NewObject(cls, mid);
    } else {
        printf("cannot find ctor\n");
        exit(1);
    }
    if (obj == NULL) {
        printf("object creation failed\n");
        exit(2);
    }
}

jobject useJvm::CreateObject(int parameter) {
    jmethodID mid = env->GetMethodID(cls, "<init>", "(I)V");
    jobject obj;
    if (mid) {
        return env->NewObject(cls, mid, parameter);
    } else {
        printf("cannot find ctor(int)\n");
        exit(1);
    }
    if (obj == NULL) {
        printf("object creation failed\n");
        exit(2);
    }
}

int useJvm::CallMethod(jobject& obj, const char* functionName, int parameter) {
    jmethodID mid = env->GetMethodID(cls, "addone", "(I)I");
    if (mid) {
        return env->CallIntMethod(obj, mid, parameter);
    } else {
        printf("cannot find object method\n");
        exit(3);
    }
}

jfieldID useJvm::SetIntField(jobject& obj, const char* fieldName, int value) {
    jfieldID fid = env->GetFieldID(cls, fieldName, "I");
    if (fid == NULL) {
        printf("get field id failed\n");
        exit(4);
    }
    env->SetIntField(obj, fid, value);
    return fid;
}

int useJvm::GetIntField(jobject& obj, jfieldID fid) {
    return env->GetIntField(obj, fid);
}

