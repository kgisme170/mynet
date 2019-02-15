#include<jni.h>
#include<stdio.h>
int printStackTrace(JNIEnv *env) {
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
char option[] = "-Djava.class.path=.:..";
int main(){
    JavaVMOption options[1];
    options[0].optionString = option;
    JavaVMInitArgs vm_args = {0};
    vm_args.version = JNI_VERSION_1_8;
    vm_args.nOptions = 1;
    vm_args.options = options;

    JavaVM* jvm;
    JNIEnv *env;
    long status = JNI_CreateJavaVM(&jvm, (void**)&env, &vm_args);
    if (status == JNI_ERR) {
        printf("Create jvm failed\n");
        return -1;
    }

    jclass cls = env->FindClass("cpp2java");
    if (cls == NULL) {
        printf("find class failed\n");
    }
    if (env->ExceptionOccurred()) // check if an exception occurred
    {
        env->ExceptionDescribe(); // print the stack trace
        return -2;
    }
    // javap -s -p cpp2java.class
    // call static methods
    jmethodID mid = env->GetStaticMethodID(cls, "intFunc", "(I)I");
    if (mid) {
        jint i = env->CallStaticIntMethod(cls, mid, 8);
        printf("return %d\n", i);
    } else {
        printf("find statis int method failed\n");
    }
    mid = env->GetStaticMethodID(cls, "boolFunc", "(Z)Z");
    if (mid) {
        jboolean b = env->CallStaticBooleanMethod(cls, mid, false);
        printf("return %d\n", b);      
    } else {
        printf("find statis bool method failed\n");
    }
    printf("read function\n");
    mid = env->GetStaticMethodID(cls, "read", "(Ljava/lang/String;)[B");
    if (mid) {
        jstring js = env->NewStringUTF("hi");
        jbyteArray obj = (jbyteArray)env->CallStaticObjectMethod(cls, mid, js);
        jbyte* data=env->GetByteArrayElements(obj,0);
        printf("%c%c\n",data[0],data[1]);
        env->ReleaseByteArrayElements(obj, data, 0);
    } else {
        printf("find static byte[] read(String) failed\n");
    }

    mid = env->GetStaticMethodID(cls, "mythrow", "()V");
    if (mid) {
        env->CallStaticVoidMethod(cls, mid);
        jthrowable t = env->ExceptionOccurred();
        if (t) {
            printf("------Got exception object\n");
            printStackTrace(env);
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
    }

    // create object
    mid = env->GetMethodID(cls, "<init>", "()V");
    jobject obj;
    if (mid) {
        obj = env->NewObject(cls, mid);
    } else {
        printf("cannot find ctor\n");
        return 1;
    }
    if (obj == NULL) {
        printf("object creation failed\n");
        return 2;
    }
    mid = env->GetMethodID(cls, "addone", "(I)I");
    if (mid) {
        jint ret = env->CallIntMethod(obj, mid, 3);
        printf("addone(3)=%d\n", ret);
    } else {
        printf("cannot find object method\n");
    }

    // create object with param
    jobject obj2;
    mid = env->GetMethodID(cls, "<init>", "(I)V");
    if (mid) {
        obj2 = env->NewObject(cls, mid, 7);
    } else {
        printf("cannot find ctor(int)\n");
    }
    if (obj2 == NULL) {
        printf("object2 creation failed\n");
        return 3;
    }
    jfieldID fid = env->GetFieldID(cls, "intField", "I");
    if (fid == NULL) {
        printf("get field id failed\n");
        return 4;
    }
    env->SetIntField(obj2, fid, 8);
    int f = env->GetIntField(obj2, fid);
    printf("new field value=%d\n", f);
    //env->ReleaseEle
    jvm->DestroyJavaVM();
    return 0;
}
