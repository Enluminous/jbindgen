//
// Created by nettal on 23-11-7.
//

#include "StructDeclaration.h"

#include <utility>

namespace jbindgen {

    Member::Member(Typed type, long offsetOfBit) : type(std::move(type)), offsetOfBit(offsetOfBit) {
    }

    StructDeclaration::StructDeclaration(Typed structType) : structType(std::move(structType)) {
    }
}
