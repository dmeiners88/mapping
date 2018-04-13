package de.dmeiners.mapping.impl.groovy;

import de.dmeiners.mapping.api.BaseScript;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class GroovyScript extends BaseScript {

    private final Object script;

    public GroovyScript(Object script) {
        this.script = script;
    }

    @Override
    public <T> List<T> execute(Collection<T> targets, Map<String, Object> context) {

        return null;
    }
}
