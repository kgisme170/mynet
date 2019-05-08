#pragma once

#include <cxxabi.h>
#include <execinfo.h>
#include <string>
#include <set>
#include <map>
#include <tr1/unordered_set>
#include <tr1/unordered_map>
#include <vector>
#include <iostream>
using namespace std;
using namespace std::tr1;

inline string GetStackTrace()
{
    size_t MAX_STACK_TRACE_SIZE=50;
    void* stackBuffer[MAX_STACK_TRACE_SIZE];
    size_t istackBufferSize = backtrace(stackBuffer, MAX_STACK_TRACE_SIZE);
    char** strings = backtrace_symbols(stackBuffer, istackBufferSize);
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
        char* s = abi::__cxa_demangle(
            mangledName.substr(begin, end-begin).c_str(), NULL, 0, &status);
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

template<typename T>
inline string printVector(const vector<T>& v)
{
    string r=__FUNCTION__;
    for(typeof(v.begin()) it = v.begin(); it != v.end(); ++it)
    {
        r+=*it;
        r+=',';
    }
    return r;
}
template<typename T>
inline string printToVector(const vector<T>& v)
{
    string r=__FUNCTION__;
    for(typeof(v.begin()) it = v.begin(); it != v.end(); ++it)
    {
        r+=it->ToString();;
        r+=',';
    }
    return r;
}
template<typename T>
inline string printSet(const set<T>& s)
{
    string r=__FUNCTION__;
    for(typeof(s.begin()) it = s.begin(); it != s.end(); ++it)
    {
        r+=*it;
        r+=',';
    }
    return r;
}

template<typename K, typename V>
inline string printMap(const map<K, V>& m)
{
    string r=__FUNCTION__;
    for(typeof(m.begin()) it = m.begin(); it != m.end(); ++it)
    {
        r+='(';
        r+=it->first;
        r+=',';
        r+=it->second;
        r+="),";
    }
    return r;
}
template<typename K, typename V>
inline string printMapToString(const map<K, V>& m)
{
    string r=__FUNCTION__;
    for(typeof(m.begin()) it = m.begin(); it != m.end(); ++it)
    {
        r+='(';
        r+=it->first;
        r+=',';
        r+=it->second.ToString();
        r+="),";
    }
    return r;
}

template<template<class, class> class C, typename K, typename V>
inline string printMap(const C<K, V>& c)
{
    string r=__FUNCTION__;
    for(typeof(c.begin()) it = c.begin(); it != c.end(); ++it)
    {
        r+='(';
        r+=it->first;
        r+=',';
        r+=it->second;
        r+="),";
    }
    return r;
}
