package de.dmeiners.mapping.impl.java;

import de.dmeiners.mapping.api.BaseScript;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class JavaScript extends BaseScript {

    private final Object script;

    public JavaScript(Object script) {
        this.script = script;
    }

    @Override
    public <T> List<T> execute(Collection<T> targets, Map<String, Object> context) {

        return null;
    }
}
