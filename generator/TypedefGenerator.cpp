//
// Created by nettal on 23-11-18.
//

#include "TypedefGenerator.h"

namespace jbindgen {
    TypedefGenerator::TypedefGenerator(const NormalTypedefDeclaration& declaration1, std::string defStrcutPackageName,
        std::string defValuePackageName, std::string defEnumPackageName, std::string defEnumDir,
        std::string defStructDir, std::string defValueDir, PFN_def_name name): declaration(declaration),
                                                                               defsStructPackageName(std::move(defStrcutPackageName)),
                                                                               defsValuePackageName(std::move(defValuePackageName)),
                                                                               defEnumDir(std::move(defEnumDir)),
                                                                               defStructDir(std::move(defStructDir)), defValueDir(std::move(defValueDir)),
                                                                               defsEnumPackageName(std::move(defEnumPackageName)), name(name) {
    }
} // jbindgen