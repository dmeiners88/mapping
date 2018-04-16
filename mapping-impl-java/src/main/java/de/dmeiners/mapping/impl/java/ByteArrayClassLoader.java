package de.dmeiners.mapping.impl.java;

public class ByteArrayClassLoader {

    public static <T> Class<? extends T> classFromBytes(Class<T> baseClass, String name, byte[] bytecode) {

        return new ClassLoader(ByteArrayClassLoader.class.getClassLoader()) {

            Class<? extends T> clazz = defineClass(name, bytecode, 0, bytecode.length).asSubclass(baseClass);
        }.clazz;
    }
}
