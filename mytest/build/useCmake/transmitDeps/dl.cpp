#include <stdio.h>
#include <stdlib.h>
#include <dlfcn.h>

int main(){
    void * handle = dlopen("libmya.so", RTLD_LAZY);
    if (!handle) {
        fprintf(stderr, "%s\n", dlerror());
        exit(EXIT_FAILURE);
    }
    int (*f)(int) = (int(*)(int)) dlsym(handle, "_Z1ai");
    char* error = dlerror();
    if (error != NULL) {
        fprintf(stderr, "%s\n", error);
        exit(EXIT_FAILURE);
    }

    printf("%d\n", f(3));
    dlclose(handle);
    exit(EXIT_SUCCESS);
    return 0;
}
