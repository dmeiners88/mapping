package de.dmeiners.mapping.impl.jexl;

import de.dmeiners.mapping.api.PostProcessor;
import de.dmeiners.mapping.api.PostProcessorProvider;
import de.dmeiners.mapping.api.ScriptNameResolver;

import java.util.Collections;
import java.util.Map;

public class JexlPostProcessorProvider implements PostProcessorProvider {

    @Override
    public PostProcessor create(ScriptNameResolver scriptNameResolver) {
        return create(scriptNameResolver, Collections.emptyMap());
    }

    @Override
    public PostProcessor create(ScriptNameResolver scriptNameResolver, Map<String, Object> extensions) {
        return new JexlPostProcessor(scriptNameResolver, extensions);
    }
}
