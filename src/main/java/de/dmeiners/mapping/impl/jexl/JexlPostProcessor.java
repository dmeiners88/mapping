package de.dmeiners.mapping.impl.jexl;

import de.dmeiners.mapping.api.PostProcessor;
import de.dmeiners.mapping.api.ResultTypeException;
import de.dmeiners.mapping.api.ScriptExecutionException;
import de.dmeiners.mapping.api.ScriptName;
import de.dmeiners.mapping.api.ScriptNameResolver;
import de.dmeiners.mapping.api.ScriptText;
import de.dmeiners.mapping.impl.ClasspathScriptNameResolver;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlException;
import org.apache.commons.jexl3.JexlScript;
import org.apache.commons.jexl3.MapContext;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JexlPostProcessor implements PostProcessor {

    private final JexlEngine engine;
    private final ScriptNameResolver scriptNameResolver;

    JexlPostProcessor() {

        this(new ClasspathScriptNameResolver());
    }

    JexlPostProcessor(ScriptNameResolver scriptNameResolver) {
        this.engine = new JexlBuilder()
            .cache(512)
            .strict(true)
            .silent(false)
            .create();

        this.scriptNameResolver = scriptNameResolver;
    }

    @Override
    public <T> T process(T target, ScriptName scriptName, Map<String, Object> context) {

        ScriptText scriptText = scriptName.resolve(this.scriptNameResolver, context);
        return process(target, scriptText, context);
    }

    @Override
    public <T> T process(T target, ScriptText scriptText, Map<String, Object> context) {

        return process(Collections.singletonList(target), scriptText, context).iterator().next();
    }

    @Override
    public <T> List<T> process(Collection<T> targets, ScriptName scriptName, Map<String, Object> context) {

        ScriptText scriptText = scriptName.resolve(this.scriptNameResolver, context);
        return process(targets, scriptText, context);
    }

    @Override
    public <T> List<T> process(Collection<T> targets, ScriptText scriptText, Map<String, Object> context) {

        JexlScript script = scriptText.parse(this.engine);

        return targets.stream()
            .map(it -> execute(it, context, script))
            .map(it -> cast(targets.iterator().next(), it))
            .collect(Collectors.toList());
    }

    private <T> Object execute(T target, Map<String, Object> context, JexlScript script) {

        Object result;

        try {
            result = script.execute(new MapContext(context), target);
        } catch (JexlException e) {
            throw new ScriptExecutionException(String.format("Error executing parsed script: '%s'",
                script.getParsedText()), e);
        }
        return result;
    }

    private <T> T cast(T target, Object result) {

        if (!target.getClass().isInstance(result)) {
            throw new ResultTypeException(String.format("Script did not return an object of type '%s'.",
                target.getClass().getName()));
        }

        // The above check should let this "cast" never fail. At runtime we are dealing with objects anyway.
        return (T) target.getClass().cast(result);
    }

}
