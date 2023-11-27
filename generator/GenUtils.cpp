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
        std::cout << "Writing to File " << file << "...";
        std::ofstream outputFile(file);
        if (outputFile.is_open()) {
            outputFile << content;
            outputFile.close();
            std::cout << "Done" << std::endl;
        } else {
            std::cout << "Can not open file: " << file << std::endl;
            abort();
        }
    }

    std::string generateFakeValueLayout(int64_t byteSize) {
        assert(byteSize % 4 == 0);
        assert(byteSize > 0);//currently is signed
        std::string layout;
        return "MemoryLayout.sequenceLayout(" + std::to_string(byteSize / 4) + "," +
               value::jbasic::Integer.value_layout() + ")";
    }

    std::string toStringWithCXCursorMap(CXCursor &cxCursor, const CXCursorMap &map) {
        assert(0);
    }
} // jbindgen