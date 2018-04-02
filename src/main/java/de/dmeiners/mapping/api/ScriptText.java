package de.dmeiners.mapping.api;

import com.google.errorprone.annotations.Immutable;

import java.util.Objects;

@Immutable
public class ScriptText {

    private final String text;

    private ScriptText(String text) {
        this.text = text;
    }

    public static ScriptText of(String text) {
        return new ScriptText(text);
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScriptText that = (ScriptText) o;
        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {

        return Objects.hash(text);
    }

    @Override
    public String toString() {
        return this.text;
    }
}
