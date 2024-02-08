//
// Created by snownf on 24-2-8.
//

#ifndef LEARNC_TEST_H
#define LEARNC_TEST_H

#if defined(_WIN32)
#define EXPORT extern __declspec(dllexport) __stdcall
#else
#define EXPORT extern __attribute__((visibility("default")))
#endif

#define DEF_CONST_1 1

typedef struct test {
    int a;
    float b;
} Test;

typedef void(*ReadCallback)(int a, float b);

EXPORT int copyStruct(Test *src, Test *dest);

EXPORT void readStruct(Test test, ReadCallback callback);

EXPORT void readStructPtr(Test *test, ReadCallback callback);

EXPORT void printStruct(Test *test);

#endif //LEARNC_TEST_H
