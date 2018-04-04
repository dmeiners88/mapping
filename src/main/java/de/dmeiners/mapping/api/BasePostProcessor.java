package de.dmeiners.mapping.api;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class BasePostProcessor implements PostProcessor {

    private final ScriptNameResolver scriptNameResolver;

    protected BasePostProcessor(ScriptNameResolver scriptNameResolver) {
        this.scriptNameResolver = scriptNameResolver;
    }

    @Override
    public <T> T process(T target, ScriptText scriptText, Map<String, Object> context) {

        return process(Collections.singletonList(target), scriptText, context).iterator().next();
    }

    @Override
    public <T> T process(T target, ScriptName scriptName, Map<String, Object> context) {

        ScriptText scriptText = scriptName.resolve(this.scriptNameResolver, context);
        return process(target, scriptText, context);
    }

    @Override
    public <T> List<T> process(Collection<T> targets, ScriptName scriptName, Map<String, Object> context) {

        ScriptText scriptText = scriptName.resolve(this.scriptNameResolver, context);
        return process(targets, scriptText, context);
    }
}
