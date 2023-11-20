cmake_minimum_required(VERSION 3.27)
project(javabindgen)

set(CMAKE_CXX_STANDARD 23)

# Find CMake file for Clang
find_package(Clang REQUIRED)

include_directories(${CLANG_INCLUDE_DIRS})

add_executable(javabindgen main.cpp
        analyser/Analyser.cpp
        analyser/Analyser.h
        analyser/StructDeclaration.cpp
        analyser/StructDeclaration.h
        analyser/AnalyserUtils.cpp
        analyser/AnalyserUtils.h
        analyser/UnionDeclaration.cpp
        analyser/UnionDeclaration.h
        analyser/EnumDeclaration.cpp
        analyser/NormalMacroDeclaration.cpp
        analyser/NormalMacroDeclaration.h
        analyser/FunctionLikeMacroDeclaration.cpp
        analyser/FunctionLikeMacroDeclaration.h
        analyser/NormalTypedefDeclaration.cpp
        analyser/NormalTypedefDeclaration.h
        analyser/FunctionProtoTypeDeclaration.cpp
        analyser/FunctionProtoTypeDeclaration.h
        analyser/FunctionSymbolDeclaration.cpp
        analyser/FunctionSymbolDeclaration.h
        generator/Value.cpp
        generator/Value.h
        generator/StructGeneratorUtils.cpp
        generator/StructGeneratorUtils.h
        generator/StructGenerator.cpp
        generator/StructGenerator.h
        generator/GenUtils.cpp
        generator/GenUtils.h
        generator/EnumGenerator.cpp
        generator/EnumGenerator.h
        generator/Generator.cpp
        generator/Generator.h
        generator/FunctionSymbolGenerator.cpp
        generator/FunctionSymbolGenerator.h
        generator/FunctionProtoTypeGenerator.cpp
        generator/FunctionProtoTypeGenerator.h
        generator/MacroFunctionLikeGenerator.cpp
        generator/MacroFunctionLikeGenerator.h
        generator/MacroNormalGenerator.cpp
        generator/MacroNormalGenerator.h
        Utils.h
        generator/FunctionSymbolGeneratorUtils.cpp
        generator/FunctionSymbolGeneratorUtils.h
        generator/TypedefGenerator.cpp
        generator/TypedefGenerator.h
        generator/TypedefGeneratorUtils.cpp
        generator/TypedefGeneratorUtils.h
)

#Treat Warnings as Errors
set_property(TARGET javabindgen PROPERTY COMPILE_WARNING_AS_ERROR ON)

target_link_libraries(javabindgen libclang)
