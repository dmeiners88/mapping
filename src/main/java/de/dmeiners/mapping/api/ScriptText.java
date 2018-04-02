package de.dmeiners.mapping.api;

import com.google.errorprone.annotations.Immutable;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlException;
import org.apache.commons.jexl3.JexlScript;

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

    public JexlScript parse(JexlEngine engine) {

        String preparedScriptText = ensureLastExpressionIsTarget(this.text);

        JexlScript script;

        try {

            script = engine.createScript(preparedScriptText, "target");
        } catch (JexlException e) {
            throw new ScriptParseException(String.format("Error parsing script text: '%s'", preparedScriptText), e);
        }
        return script;
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

    private String ensureLastExpressionIsTarget(String scriptText) {
        return String.format("%s; target;", scriptText);
    }
}
