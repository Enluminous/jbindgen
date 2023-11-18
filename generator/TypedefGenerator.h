//
// Created by nettal on 23-11-18.
//

#ifndef TYPEDEFGENERATOR_H
#define TYPEDEFGENERATOR_H
#include <string>
#include "../analyser/NormalTypedefDeclaration.h"

namespace jbindgen {
    typedef std::string (*PFN_def_name)(const NormalTypedefDeclaration&declaration, void* pUserdata);

    class TypedefGenerator {
        NormalTypedefDeclaration declaration;
        const std::string defsStructPackageName;
        const std::string defsEnumPackageName;
        const std::string defsValuePackageName;
        const std::string defEnumDir;
        const std::string defStructDir;
        const std::string defValueDir;
        const PFN_def_name name;

    public:
        TypedefGenerator(const NormalTypedefDeclaration&declaration1, std::string defStrcutPackageName,
                         std::string defValuePackageName, std::string defEnumPackageName,
                         std::string defEnumDir, std::string defStructDir, std::string defValueDir,
                         PFN_def_name name);

        void visit(void* nameUserData) {
            //todo
        }
    };
} // jbindgen

#endif //TYPEDEFGENERATOR_H
