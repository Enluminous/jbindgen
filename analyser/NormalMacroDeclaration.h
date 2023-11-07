//
// Created by snownf on 23-11-7.
//

#ifndef JAVABINDGEN_NORMALDEFINITIONDECLARATION_H
#define JAVABINDGEN_NORMALDEFINITIONDECLARATION_H

namespace jbindgen {

    class NormalMacroDeclaration {
        explicit NormalMacroDeclaration(std::pair<std::string, std::string> pair1);

        std::pair<std::string, std::string> normalDefines;

    public:
        static NormalMacroDeclaration visit(CXCursor param);

        friend std::ostream &operator<<(std::ostream &stream, const NormalMacroDeclaration &normal) {
            stream << "#### Normal Definition " << std::endl;
            stream << "  " << normal.normalDefines.first << "="
                   << normal.normalDefines.second << std::endl;
            return stream;
        };
    };

} // jbindgen

#endif //JAVABINDGEN_NORMALDEFINITIONDECLARATION_H
