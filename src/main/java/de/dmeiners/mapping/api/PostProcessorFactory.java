package de.dmeiners.mapping.api;

import de.dmeiners.mapping.impl.ClasspathScriptNameResolver;

import java.util.ServiceLoader;

public class PostProcessorFactory {

    private static final ServiceLoader<PostProcessor> loader = ServiceLoader.load(PostProcessor.class);

    private PostProcessorFactory() {
    }

    public static PostProcessor create(String engineType) {

        return create(engineType, new ClasspathScriptNameResolver());
    }

    public static PostProcessor create(String engineType, ScriptNameResolver resolver) {

        for (PostProcessor processor : loader) {

            if (processor.getEngineType().equals(engineType)) {
                return processor;
            }
        }

        return null;
    }
}
