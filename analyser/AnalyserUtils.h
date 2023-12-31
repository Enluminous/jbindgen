//
// Created by nettal on 23-11-7.
//

#ifndef JBINDGEN_ANALYSERUTILS_H
#define JBINDGEN_ANALYSERUTILS_H

#include <any>
#include <clang-c/Index.h>
#include <string>
#include <ostream>

#define assertStr(cond, msg) { if (!(cond)) { fprintf(stderr, "assert fails %s:%d: %s\n", __FILE__, __LINE__, std::string(msg).c_str()); abort(); }}
#define assertAppend(cond, msg) { if (!(cond)) { fprintf(stderr, "assert fails %s:%d: %s\n", __FILE__, __LINE__, (std::string("condition: ")+#cond+" msg: "+msg).c_str()); abort(); }}

namespace jbindgen {
    constexpr bool DEBUG_LOG = true;
    constexpr bool WARNING = true;
    constexpr const char *NO_NAME = "#NO_NAME#";
    constexpr const char *NO_COMMENT = "#NO_COMMENT#";

    bool hasDeclaration(CXType c);

    std::string toString(const CXString &s);

    std::string toStringIfNullptr(const CXString &s);

    std::string toStringWithoutConst(const CXType &t);

    CXType removeCXTypeConst(const CXType &t);

    std::string getComment(CXCursor cursor);

    std::string makeUnnamedParaNamed(size_t index);

    std::string makeUnnamedMemberNamed(size_t index);

    int64_t checkResultSize(int64_t size);

    bool isValidSize(int64_t size);

    class DeclarationBasic {
    public:
        virtual ~DeclarationBasic() = default;

        [[nodiscard]] virtual std::string const getName() const;

        [[nodiscard]] virtual CXType const getCXType() const;

        virtual void addUsage(const std::string &c);

        [[nodiscard]] virtual size_t visitResult() const;
    };

    class EmptyDeclaration : public DeclarationBasic {
        [[nodiscard]] const std::string getName() const override;

        [[nodiscard]] const CXType getCXType() const override;

        void addUsage(const std::string &c) override;
    };

    class VarDeclare {
    public:
        const std::string name;
        const CXType type;
        const int64_t byteSize;
        const std::string comment;
        const CXCursor cursor;
        const std::any extra;

        VarDeclare(std::string name, CXType type, int64_t size, std::string comment, CXCursor cxCursor);

        VarDeclare(std::string name, CXType type, int64_t size, std::string comment, CXCursor cxCursor, std::any extra);

        friend std::ostream &operator<<(std::ostream &stream, const VarDeclare &typed);
    };

    struct AnalyserConfig;

    bool defaultAnalyserDeclFilter(const CXCursor &c, const AnalyserConfig &config);

    bool defaultAnalyserMacroFilter(const CXCursor &c, const AnalyserConfig &config);
}

#endif //JBINDGEN_ANALYSERUTILS_H
