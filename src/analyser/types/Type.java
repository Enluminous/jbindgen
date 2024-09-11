package analyser.types;

public sealed interface Type permits AbstractType {
    String getTypeName();
}
