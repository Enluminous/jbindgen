//
// Created by snownf on 23-11-9.
//

#ifndef JAVABINDGEN_STRUCTGENERATOR_H
#define JAVABINDGEN_STRUCTGENERATOR_H

#include <utility>

#include "../analyser/StructDeclaration.h"
#include "GenUtils.h"


namespace jbindgen {

    class StructGenerator {
        const StructDeclaration declaration;
        const std::string structsDir;
        const std::string packageName;

        const FPN_structRename structRename;
        const FPN_memberRename memberRename;
        const FPN_decodeGetter decodeGetter;
        const FPN_decodeSetter decodeSetter;

    public:

        StructGenerator(StructDeclaration declaration, std::string structsDir, std::string packageName,
                        FPN_structRename structRename, FPN_structRename memberRename,
                        FPN_decodeGetter decodeGetter, FPN_decodeSetter decodeSetter)
                : declaration(std::move(declaration)), structsDir(std::move(structsDir)),
                  packageName(std::move(packageName)),
                  structRename(structRename), memberRename(memberRename),
                  decodeGetter(decodeGetter), decodeSetter(decodeSetter) {
        }

        std::string makeGetterSetter(const std::string& structName);

        void build();
    };

} // jbindgen

#endif //JAVABINDGEN_STRUCTGENERATOR_H
