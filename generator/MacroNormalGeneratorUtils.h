//
// Created by snownf on 23-11-29.
//

#ifndef JAVABINDGEN_MACRONORMALGENERATORUTILS_H
#define JAVABINDGEN_MACRONORMALGENERATORUTILS_H

#include <string>
#include <vector>
#include <cstring>
#include "../analyser/NormalMacroDeclaration.h"


namespace jbindgen {
    enum TYPE {
        T_UNKNOWN,
        T_IGNORE,
        T_STRING,
        T_INT,
        T_LONG,
        T_FLOAT,
        T_DOUBLE,
    };

    class TypeWithName {
    public:
        TYPE type;
        std::string jType;

        constexpr TypeWithName(TYPE type, const char *string) : type(type), jType(string) {}
    };

    constexpr TypeWithName Type_UNKNOWN{T_UNKNOWN, "UNKNOWN"};
    constexpr TypeWithName Type_STRING{T_STRING, "String"};
    constexpr TypeWithName Type_INT{T_INT, "int"};
    constexpr TypeWithName Type_Long{T_LONG, "long"};
    constexpr TypeWithName Type_Float{T_FLOAT, "float"};
    constexpr TypeWithName Type_Double{T_DOUBLE, "double"};
    constexpr TypeWithName Type_Ignore{T_IGNORE, "ignore"};

    class MacroNormalGeneratorUtils {
    public:
        static TypeWithName getType(const std::string &string) {
            if (string.empty())
                return Type_Ignore;
            if (string.starts_with('"') && string.ends_with('"'))
                return Type_STRING;
            if (string.contains('.')) {
                try {
                    std::stof(string);
                } catch (std::invalid_argument &a) {
                    return Type_UNKNOWN;
                } catch (std::out_of_range &e) {
                    return Type_Double;
                }
                return Type_Float;
            }
            // 1234L
            // avoid the 'L'
            if ((string.ends_with('L') || string.ends_with('l')) && (isdigit(string[string.length() - 2])))
                return Type_Long;
            // 1234LL
            // avoid the "LL"
            if ((string.ends_with("LL") || string.ends_with("ll")) && (isdigit(string[string.length() - 3])))
                return Type_Long;

            try {
                std::stoi(string);
            } catch (std::invalid_argument &a) {
                return Type_UNKNOWN;
            } catch (std::out_of_range &e) {
                return Type_Long;
            }
            return Type_INT;
        }

        static TypeWithName
        lookupType(const std::vector<NormalMacroDeclaration> *allDeclaration, const std::string &type) {
            for (const auto &item: *allDeclaration) {
                if (strcmp(item.normalDefines.first.c_str(), type.c_str()) == 0) {
                    auto t = getType(item.normalDefines.second);
                    if (t.type == T_UNKNOWN)
                        return lookupType(allDeclaration, item.normalDefines.second);
                    else
                        return t;
                }
            }
            return Type_UNKNOWN;
        }

        static std::string defaultMakeMacro(const jbindgen::NormalMacroDeclaration &declaration, void *pUserdata,
                                            const std::vector<NormalMacroDeclaration> *allDeclaration) {
            auto second = declaration.normalDefines.second;
            switch (getType(second).type) {
                case T_UNKNOWN:
                    break;
                case T_IGNORE:
                    return "IGNORE";
                case T_STRING:
                    return "public static final String " + declaration.normalDefines.first + " = " + second;
                case T_INT:
                    return "public static final int " + declaration.normalDefines.first + " = " + second;
                case T_LONG: {
                    std::string sec = second;
                    if (sec.ends_with("UL")) {
                        sec = sec.substr(0, sec.length() - 2);
                        return "public static final long " + declaration.normalDefines.first + " = " +
                               "Long.parseUnsignedLong(\"" + sec + "\")";
                    } else {
                        if (sec.ends_with('U') || sec.ends_with('u')) {
                            sec = sec.substr(0, sec.length() - 1);
                            return "public static final long " + declaration.normalDefines.first + " = " +
                                   "Long.parseUnsignedLong(\"" + sec + "\")";
                        } else if (sec.ends_with("LL") || sec.ends_with("ll")) {
                            sec = sec.substr(0, sec.length() - 2);
                            return "public static final long " + declaration.normalDefines.first + " = " +
                                   "Long.parseLong(\"" + sec + "\")";
                        } else if (sec.ends_with('L') || sec.ends_with("l")) {
                            sec = sec.substr(0, sec.length() - 1);
                            return "public static final long " + declaration.normalDefines.first + " = " +
                                   "Long.parseLong(\"" + sec + "\")";
                        }
                        assert(0);
                    }
                }
                case T_FLOAT: {
                    return "public static final float " + declaration.normalDefines.first + " = " +
                           "Float.parseFloat(\"" + second + "\")";
                }
                case T_DOUBLE:
                    return "public static final double " + declaration.normalDefines.first + " = " +
                           "Double.parseDouble(\"" + second + "\")";
                default:
                    assert(0);
            }
            return "";
        }
    };

} // jbindgen

#endif //JAVABINDGEN_MACRONORMALGENERATORUTILS_H
