package de.dmeiners.mapping.impl.groovy;

import de.dmeiners.mapping.api.PostProcessor;
import de.dmeiners.mapping.api.PostProcessorProvider;
import de.dmeiners.mapping.api.ScriptNameResolver;

public class GroovyPostProcessorProvider implements PostProcessorProvider {

    @Override
    public PostProcessor create(ScriptNameResolver scriptNameResolver) {
        return new GroovyPostProcessor(scriptNameResolver);
    }
}
