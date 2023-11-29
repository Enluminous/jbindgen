cmake_minimum_required(VERSION 3.27)
project(jbindgen)

set(CMAKE_CXX_STANDARD 23)

# Find CMake file for Clang
find_package(Clang REQUIRED)

include_directories(${CLANG_INCLUDE_DIRS})

add_executable(jbindgen main.cpp
        analyser/Analyser.cpp
        analyser/AnalyserUtils.cpp
        analyser/EnumDeclaration.cpp
        analyser/FunctionLikeMacroDeclaration.cpp
        analyser/FunctionSymbolDeclaration.cpp
        analyser/FunctionTypeDefDeclaration.cpp
        analyser/NormalMacroDeclaration.cpp
        analyser/NormalTypedefDeclaration.cpp
        analyser/StructDeclaration.cpp
        analyser/UnionDeclaration.cpp
        analyser/VarDeclaration.cpp

        generator/EnumGenerator.cpp
        generator/FunctionGeneratorUtils.cpp
        generator/FunctionProtoTypeGenerator.cpp
        generator/FunctionSymbolGenerator.cpp
        generator/Generator.cpp
        generator/GenUtils.cpp
        generator/MacroFunctionLikeGenerator.cpp
        generator/MacroFunctionLikeGenerator.h
        generator/MacroNormalGenerator.cpp
        generator/StructGenerator.cpp
        generator/StructGeneratorUtils.cpp
        generator/TypedefGenerator.cpp
        generator/TypedefGeneratorUtils.cpp
        generator/Value.cpp
        generator/VarGenerator.cpp
        generator/VarGenerator.h
        shared/CXCursorMap.cpp
        shared/CXCursorMap.h
        generator/MacroNormalGeneratorUtils.cpp
        generator/MacroNormalGeneratorUtils.h
        shared/CXCursorMap.cpp)

#Treat Warnings as Errors
set_property(TARGET jbindgen PROPERTY COMPILE_WARNING_AS_ERROR ON)

target_link_libraries(jbindgen libclang)
