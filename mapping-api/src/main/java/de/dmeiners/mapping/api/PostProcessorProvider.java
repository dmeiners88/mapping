package de.dmeiners.mapping.api;

public interface PostProcessorProvider {

    PostProcessor create(ScriptNameResolver scriptNameResolver);
}
