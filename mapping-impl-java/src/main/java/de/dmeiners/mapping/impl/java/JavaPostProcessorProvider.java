package de.dmeiners.mapping.impl.java;

import de.dmeiners.mapping.api.PostProcessor;
import de.dmeiners.mapping.api.PostProcessorProvider;
import de.dmeiners.mapping.api.ScriptNameResolver;

public class JavaPostProcessorProvider implements PostProcessorProvider {

    @Override
    public PostProcessor create(ScriptNameResolver scriptNameResolver) {
        return new JavaPostProcessor(scriptNameResolver);
    }
}
