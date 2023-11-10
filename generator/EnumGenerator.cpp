//
// Created by snownf on 23-11-9.
//

#include "EnumGenerator.h"

#include <sstream>
namespace jbindgen {

    void EnumGenerator::build(void* pUserdata) {
        std::stringstream ss;
        for (const EnumDeclaration& enumDeclaration : enumDeclarations) {
            std::stringstream enums;
            for (const auto& anEnum : enumDeclaration.members) {
                enums<<"\n        public static final int "<<anEnum.type.name<<" = "<<anEnum.declValue<<";";
            }
            ss<<"public static final class "<<rename(enumDeclaration.name,pUserdata)<<" {"<<std::endl
                <<"public static String enumToString(int e) {"<<std::endl
                 <<   "return "<<enumClassName<<".enumToString("<<rename(enumDeclaration.name,pUserdata)<<".class, e);"<<std::endl
            <<"}"<<std::endl
            <<enums.str()<<std::endl
            <<"}"<<std::endl;
        }

        std::stringstream core;
                     core<<"        package "<<enumPackageName<<";\n"
                           "\n"
                           "        import java.lang.reflect.Field;\n"
                           "\n"
                           "        public final class "<<enumClassName<<" {\n"
                           "            private static String enumToString(Class<?> klass, int e) {\n"
                           "                try {\n"
                           "                    Field[] declaredFields = klass.getFields();\n"
                           "                    for (Field declaredField : declaredFields) {\n"
                           "                        if (declaredField.getInt(null) == e) {\n"
                           "                            return declaredField.getName();\n"
                           "                        }\n"
                           "                    }\n"
                           "                } catch (IllegalAccessException ignored) {\n"
                           "                }\n"
                           "                return \"invalid enum: \" + e;\n"
                           "            }\n"
                           "\n"
                           "            "<<ss.str()<<"\n"
                           "        }";

        overwriteFile(enumDir + "/" + enumClassName + ".java", core.str());
    }
} // jbindgen