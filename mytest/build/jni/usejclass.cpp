#include<jni.h>
#include<stdio.h>
#include"useJvm.h"

int main() {
    useJvm u("cpp2java");
    printf("%d\n", u.CallStaticFunction("intFunc", 8));
    int buf[3];
    for (int i = 0; i < 3; ++i) {
        buf[i] = i;
    }
    u.CallStaticFunction("arrayFunc", buf, 3);

    printf("%d\n", u.CallStaticFunction("boolFunc", false));
    u.CallStaticFunction("read", "hi");
    u.CallStaticThrow("mythrow");

    jobject obj = u.CreateObject();
    printf("addone(3)=%d\n", u.CallMethod(obj, "addone", 3));

    // create object with param
    jobject obj2 = u.CreateObject(7);
    jfieldID fid = u.SetIntField(obj2, "intField", 8);

    printf("new field value=%d\n", u.GetIntField(obj2, fid));
    return 0;
}