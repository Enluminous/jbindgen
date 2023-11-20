//
// Created by snownf on 23-11-15.
//

#ifndef JAVABINDGEN_UTILS_H
#define JAVABINDGEN_UTILS_H

#include <string>

bool string_contains(const std::string &string, const std::string &key) {
    return string.find(key) != std::string::npos;
}

bool string_startsWith(const std::string &string, const std::string &prefix) {
    if (string.length() < prefix.length()) {
        return false;
    }

    for (size_t i = 0; i < prefix.length(); i++) {
        if (string[i] != prefix[i]) {
            return false;
        }
    }

    return true;
}

std::string string_cut_first(const std::string &str, const std::string &cut) {
    std::string result = str;

    size_t pos = result.find(cut);
    if (pos != std::string::npos)
        result = result.substr(pos + cut.length());

    return result;
}

#endif //JAVABINDGEN_UTILS_H
