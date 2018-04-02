package de.dmeiners.mapping.impl;

import de.dmeiners.mapping.api.ScriptName;
import de.dmeiners.mapping.api.ScriptNameResolver;
import de.dmeiners.mapping.api.ScriptText;

import java.util.Map;

public class MongoScriptNameResolver implements ScriptNameResolver {

    @Override
    public ScriptText resolve(ScriptName scriptName, Map<String, Object> context) {
        throw new UnsupportedOperationException();
    }
}
