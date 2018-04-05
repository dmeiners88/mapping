package de.dmeiners.mapping.impl.jexl;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SandboxClassLoader extends ClassLoader {

    private final Set<String> whitelist;
    private final Set<String> blacklist;

    public SandboxClassLoader() {

        this.whitelist = Collections.emptySet();
        this.blacklist = new HashSet<>(Arrays.asList("java.lang.System", "java.lang.Runtime"));
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {

        return loadClass(name, false);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {

        if (isWhitelisted(name) && isNotBlacklisted(name)) {
            return super.loadClass(name, resolve);
        }

        throw new ClassNotFoundException();
    }

    private boolean isNotBlacklisted(String name) {
        return !this.blacklist.contains(name);
    }

    private boolean isWhitelisted(String name) {
        return this.whitelist.isEmpty() || this.whitelist.contains(name);
    }
}
