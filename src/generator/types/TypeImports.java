package generator.types;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TypeImports {
    private final Set<TypeAttr.GenerationType> imports = new HashSet<>();

    TypeImports(Set<TypeAttr.GenerationType> imports) {
        this.imports.addAll(imports);
    }

    TypeImports(TypeAttr.GenerationType imports) {
        this.imports.add(imports);
    }

    public TypeImports() {
    }

    public TypeImports addImport(TypeImports imports) {
        this.imports.addAll(imports.imports);
        return this;
    }

    TypeImports removeImport(TypeAttr.GenerationType type) {
        imports.remove(type);
        return this;
    }

    public TypeImports addDefineImports(TypeAttr.TypeRefer type) {
        addImport(type.getDefineImportTypes());
        return this;
    }

    public TypeImports addDefineImports(Collection<TypeAttr.TypeRefer> type) {
        type.forEach(this::addDefineImports);
        return this;
    }

    public TypeImports addUseImports(TypeAttr.TypeRefer type) {
        addImport(type.getUseImportTypes());
        return this;
    }

    public TypeImports addUseImports(Collection<TypeAttr.TypeRefer> type) {
        type.forEach(this::addUseImports);
        return this;
    }

    public Set<TypeAttr.GenerationType> getImports() {
        return Collections.unmodifiableSet(imports);
    }
}
