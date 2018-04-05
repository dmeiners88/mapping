package de.dmeiners.mapping.impl.mvel;

import de.dmeiners.mapping.api.PostProcessor;
import de.dmeiners.mapping.api.PostProcessorProvider;
import de.dmeiners.mapping.api.ScriptNameResolver;

public class MvelPostProcessorProvider implements PostProcessorProvider {

    @Override
    public PostProcessor create(ScriptNameResolver scriptNameResolver) {
        return new MvelPostProcessor(scriptNameResolver);
    }
}
