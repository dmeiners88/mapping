package de.dmeiners.mapping.impl;

import de.dmeiners.mapping.api.ScriptNameResolver;

import java.util.Map;

public class MongoScriptNameResolver implements ScriptNameResolver {

    @Override
    public String resolve(String scriptName, Map<String, Object> context) {
        throw new UnsupportedOperationException();
    }
}
