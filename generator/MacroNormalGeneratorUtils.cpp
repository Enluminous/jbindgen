//
// Created by snownf on 23-11-29.
//

#include "MacroNormalGeneratorUtils.h"

namespace jbindgen {
    TypeWithName MacroNormalGeneratorUtils::getType(const std::string &string) {
        if (string.empty())
            return Type_Empty;
        if (string.starts_with('"') && string.ends_with('"'))
            return Type_STRING;
        if (string.contains('.')) {
            if (string.ends_with('d') || string.ends_with('D'))
                return Type_Double;
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

    TypeWithName MacroNormalGeneratorUtils::lookupType(const std::vector<NormalMacroDeclaration> *allDeclaration,
                                                       const std::string &type) {
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

    std::string MacroNormalGeneratorUtils::defaultMakeMacro(const NormalMacroDeclaration &declaration,
                                                            const std::vector<NormalMacroDeclaration> *allDeclaration) {
        auto second = declaration.normalDefines.second;
        switch (getType(second).type) {
            case T_UNKNOWN:
                return "";
            case T_EMPTY:
                return "IGNORE Empty parents definition";
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
                if (second.ends_with("F16") || second.ends_with("f16"))
                    return "IGNORE Unsupported floating-point types Float16";
                return "public static final float " + declaration.normalDefines.first + " = " +
                       "Float.parseFloat(\"" + second + "\")";
            }
            case T_DOUBLE:
                return "public static final double " + declaration.normalDefines.first + " = " +
                       "Double.parseDouble(\"" + second + "\")";
        }
        assert(0);
    }
} // jbindgen