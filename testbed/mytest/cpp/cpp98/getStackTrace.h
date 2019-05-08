#include <cxxabi.h>
#include <execinfo.h>
#include <iostream>
#include <map>
#include <set>
#include <string>
#include <vector>
#include <tr1/unordered_map>
#include <tr1/unordered_set>
using namespace std;
using namespace std::tr1;

inline string GetStackTrace() {
    size_t MAX_STACK_TRACE_SIZE = 50;
    void *stackBuffer[MAX_STACK_TRACE_SIZE];
    size_t istackBufferSize = backtrace(stackBuffer, MAX_STACK_TRACE_SIZE);
    char **strings = backtrace_symbols(stackBuffer, istackBufferSize);
    if (strings == NULL) {
        return "<Get no stack trace>\n";
    }

    string result;
    for (size_t i = 0; i < istackBufferSize; ++i) {
        string mangledName = strings[i];
        string::size_type begin = mangledName.find('(');
        string::size_type end = mangledName.find('+', begin);
        if (begin == string::npos || end == string::npos) {
            result += mangledName;
            result += '\n';
            continue;
        }

        ++begin;
        int status = 0;
        char *s = abi::__cxa_demangle(
                mangledName.substr(begin, end - begin).c_str(), NULL, 0, &status);
        if (status != 0) {
            result += mangledName;
            result += '\n';
            continue;
        }

        string demangledName(s);
        free(s);
        result += mangledName.substr(0, begin);
        result += demangledName;
        result += mangledName.substr(end);
        result += '\n';
    }

    free(strings);
    return result;
}