package de.dmeiners.mapping.api;

import java.util.Map;

public abstract class BasePostProcessor implements PostProcessor {

    private final ScriptNameResolver scriptNameResolver;

    protected BasePostProcessor(ScriptNameResolver scriptNameResolver) {
        this.scriptNameResolver = scriptNameResolver;
    }

    @Override
    public Script compile(String scriptName, Map<String, Object> context) {

        String scriptText = null;
        try {
            scriptText = this.scriptNameResolver.resolve(scriptName, context);
        } catch (ScriptNameResolutionException e) {
            throw new ParseException("Unable to parse because script name resolution failed.", e);
        }

        return this.compileInline(scriptText);
    }
}
