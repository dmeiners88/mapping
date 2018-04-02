package de.dmeiners.mapping.api;

import com.google.errorprone.annotations.Immutable;

import java.util.Objects;

@Immutable
public class ScriptName {

    private final String name;

    private ScriptName(String name) {
        this.name = name;
    }

    public static ScriptName of(String name) {
        return new ScriptName(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScriptName that = (ScriptName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
