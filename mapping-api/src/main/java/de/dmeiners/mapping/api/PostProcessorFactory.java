package de.dmeiners.mapping.api;

import java.util.Iterator;
import java.util.ServiceLoader;

public class PostProcessorFactory {

    private static final ServiceLoader<PostProcessorProvider> loader = ServiceLoader.load(PostProcessorProvider.class);

    private PostProcessorFactory() {
    }

    public static PostProcessor create() {

        return create(new ClasspathScriptNameResolver());
    }

    public static PostProcessor create(ScriptNameResolver resolver) {

        Iterator<PostProcessorProvider> providers = loader.iterator();

        if (providers.hasNext()) {

            PostProcessorProvider provider = providers.next();

            return provider.create(resolver);
        }

        return null;
    }
}
