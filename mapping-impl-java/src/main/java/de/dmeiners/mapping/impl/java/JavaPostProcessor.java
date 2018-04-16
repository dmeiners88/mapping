package de.dmeiners.mapping.impl.java;

import de.dmeiners.mapping.api.BasePostProcessor;
import de.dmeiners.mapping.api.Script;
import de.dmeiners.mapping.api.ScriptNameResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.util.concurrent.atomic.AtomicLong;

public class JavaPostProcessor extends BasePostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(JavaPostProcessor.class);

    private static final AtomicLong classCounter = new AtomicLong();

    private final JavaCompiler compiler;

    public JavaPostProcessor(ScriptNameResolver scriptNameResolver) {

        super(scriptNameResolver);

        this.compiler = ToolProvider.getSystemJavaCompiler();

        logger.debug("Initialized");
    }

    @Override
    public Script compileInline(String scriptText) {

        String className = "JavaPostProcessorCompiledClass" + classCounter.getAndIncrement();

        logger.trace("Generated class name: '{}'", className);

        return new JavaScript(JavaPostProcessor.class.getPackage().getName(), className, scriptText, compiler);
    }

}
