#include<jni.h>
class useJvm {
    JavaVMOption options[1];
    JavaVM* jvm;
    JNIEnv *env;
    jclass cls;
    int printStackTrace();
public:
    useJvm(const char* javaClassName);
    ~useJvm();

    int CallStaticFunction(const char* functionName, int parameter);
    bool CallStaticFunction(const char* functionName, bool parameter);
    void CallStaticFunction(const char* functionName, const char* parameter);
    void CallStaticThrow(const char* functionName);

    jobject CreateObject();
    jobject CreateObject(int parameter);
    int CallMethod(jobject& obj, const char* functionName, int parameter);
    jfieldID SetIntField(jobject& obj, const char* fieldName, int value);
    int GetIntField(jobject& obj, jfieldID fid);
};

