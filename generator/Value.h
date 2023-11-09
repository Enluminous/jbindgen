//
// Created by snownf on 23-11-9.
//

#ifndef JAVABINDGEN_VALUE_H
#define JAVABINDGEN_VALUE_H

#include <string>
#include <sstream>

namespace jbindgen {

    class Value {

#define NEXT_LINE  << std::endl

    public:
        static std::string
        makeCore(const std::string &imported, const std::string &packageName, const std::string &structName,
                 long byteSize,
                 const std::string &toString,
                 const std::string &getter_setter) {
            std::stringstream ss;

            ss << "package " << packageName << ";" << std::endl
               NEXT_LINE
               << imported << std::endl
               NEXT_LINE
               << "public final class " << structName << " implements Struct {" << std::endl
               << "    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.paddingLayout("<< byteSize << "));" << std::endl
               << "    public static final long BYTE_SIZE = " << byteSize << ";" << std::endl
               NEXT_LINE
               << "    public static NativeList<" << structName << "> list(MemorySegment ptr) {" << std::endl
               << "        return new NativeList<>(ptr, " << structName << "::new, BYTE_SIZE);" << std::endl
               << "}" << std::endl
               NEXT_LINE
               << "    public static NativeList<" << structName << "> list(MemorySegment ptr, long length) {" << std::endl
               << "        return new NativeList<>(ptr, length, " << structName << "::" << "new, BYTE_SIZE);" << std::endl
               << "    }" << std::endl
               NEXT_LINE
               << "    public static NativeList<" << structName << "> list(MemorySegment ptr, long length, Arena arena, Consumer<MemorySegment> cleanup) {" << std::endl
               << "        return new NativeList<>(ptr, length, arena, cleanup, " << structName << "::new, BYTE_SIZE);"<< std::endl
               << "    }" << std::endl
               NEXT_LINE
               << "    public static NativeList<" << structName << "> list(Arena arena, long length) {" << std::endl
               << "        return new NativeList<>(arena, length, " << structName << "::new, BYTE_SIZE);" << std::endl
               << "    }" << std::endl
               NEXT_LINE
               << "    private final MemorySegment ptr;" << std::endl
               NEXT_LINE
               << "    public " << structName << "(MemorySegment ptr) {" << std::endl
               << "        this.ptr = ptr;" << std::endl
               << "    }" << std::endl
               NEXT_LINE
               << "    public " << structName << "(MemorySegment ptr, Arena arena, Consumer <MemorySegment> cleanup) {"<< std::endl
               << "        this.ptr = ptr.reinterpret(BYTE_SIZE, arena, cleanup);" << std::endl
               << "    }" << std::endl
               NEXT_LINE
               << "    public " << structName << "(Arena arena) {" << std::endl
               << "        ptr = arena.allocate(BYTE_SIZE);" << std::endl
               << "    }" << std::endl
               NEXT_LINE
               << "    public " << structName << " fill(int value) {" << std::endl
               << "        ptr.fill((byte) value);" << std::endl
               << "        return this;" << std::endl
               << "    }" << std::endl
               NEXT_LINE
               << "    @Override" << std::endl
               << "    public MemorySegment pointer() {" << std::endl
               << "        return ptr;" << std::endl
               << "    }" << std::endl
               NEXT_LINE
               << "    public long byteSize() {" << std::endl
               << "        return ptr.byteSize();" << std::endl
               << "    }" << std::endl
               NEXT_LINE
               << getter_setter << std::endl
               NEXT_LINE
               << toString << std::endl
               << "}";
            return ss.str();
        }
    };

} // jbindgen

#endif //JAVABINDGEN_VALUE_H
