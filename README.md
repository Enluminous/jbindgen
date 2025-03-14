jbindgen
====

### A project for generating Java bindings from c header

Help
----

```shell
java src/Main.java --help
```

Example
----
See [EXAMPLE.md](EXAMPLE.md)

Note
----

- The generation uses [JEP 454: Foreign Function & Memory API](https://openjdk.org/jeps/454) to operate with native
- Need **JDK 22** or above to build the generation

Dependencies
----

- `libclang` 17
- openjdk-22 or above

```shell
sudo apt install libclang-17-dev 
```

Limitation
----

- not guarantee bitfields accessors
- not support function-like macros