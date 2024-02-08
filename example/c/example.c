#include <stdio.h>
#include "example.h"

int copyStruct(Test *src, Test *dest) {
    dest->a = src->a;
    dest->b = src->b;
    return 0;
}

void readStruct(Test test, ReadCallback callback) {
    callback(test.a, test.b);
}

void readStructPtr(Test *test, ReadCallback callback) {
    callback(test->a, test->b);
}

void printStruct(Test *test) {
    printf("===printStruct===\n"
           "a: %d\n"
           "b: %f\n",
           test->a, test->b);
}
