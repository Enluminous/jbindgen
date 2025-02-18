package generator.types;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public record MemoryLayouts(Set<TypeAttr.TypeRefer> types, String memoryLayout) {
    public static MemoryLayouts structLayout(MemoryLayouts... inner) {
        HashSet<TypeAttr.TypeRefer> t = new HashSet<>();
        ArrayList<String> memoryLayouts = new ArrayList<>();
        for (MemoryLayouts memoryLayout : inner) {
            t.addAll(memoryLayout.types());
            memoryLayouts.add(memoryLayout.memoryLayout);
        }
        t.add(CommonTypes.FFMTypes.MEMORY_LAYOUT);
        return new MemoryLayouts(t, "MemoryLayout.structLayout(%s)".formatted(String.join(", ", memoryLayouts)));
    }

    public static MemoryLayouts sequenceLayout(MemoryLayouts inner, long len) {
        HashSet<TypeAttr.TypeRefer> t = new HashSet<>(inner.types());
        t.add(CommonTypes.FFMTypes.MEMORY_LAYOUT);
        return new MemoryLayouts(t, "MemoryLayout.sequenceLayout(%s, %s)".formatted(len, inner.memoryLayout));
    }

    @Override
    public String toString() {
        return "MemoryLayout{" +
               "types=" + types +
               ", memoryLayout='" + memoryLayout + '\'' +
               '}';
    }
}
