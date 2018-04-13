package de.dmeiners.mapping.impl.java;

import de.dmeiners.mapping.api.BasePostProcessor;
import de.dmeiners.mapping.api.Script;
import de.dmeiners.mapping.api.ScriptNameResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaPostProcessor extends BasePostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(JavaPostProcessor.class);

    public JavaPostProcessor(ScriptNameResolver scriptNameResolver) {
        super(scriptNameResolver);

        logger.debug("Initialized");
    }

    @Override
    public Script compileInline(String scriptText) {

        return null;
    }
}
