//
// Created by snownf on 23-11-9.
//

#include <fstream>
#include <iostream>
#include <filesystem>
#include "GenUtils.h"

namespace jbindgen {
    void overwriteFile(const std::string &file, const std::string &content) {
        std::filesystem::path parentPath = std::filesystem::path(file).parent_path();
        std::filesystem::create_directories(parentPath);

        //overwrite if exists.
        std::cout << "Writing to File " << file << std::endl;
        std::ofstream outputFile(file);
        if (outputFile.is_open()) {
            outputFile << content;
            outputFile.close();
        } else {
            std::cout << "Can not open file: " << file << std::endl;
            abort();
        }
    }
} // jbindgen