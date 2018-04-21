package de.dmeiners.mapping.impl.jexl;

import de.dmeiners.mapping.api.*;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlException;
import org.apache.commons.jexl3.JexlScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class JexlPostProcessor extends BasePostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(JexlPostProcessor.class);

    private final ConcurrentMap<String, JexlScript> scriptCache = new ConcurrentHashMap<>();

    private final JexlEngine engine;

    JexlPostProcessor(ScriptNameResolver scriptNameResolver, Map<String, Object> extensions) {

        super(scriptNameResolver, extensions);

        this.engine = new JexlBuilder()
            .cache(512)
            .strict(true)
            .silent(false)
            .sandbox(JexlSandboxFactory.create())
            .loader(new SandboxClassLoader())
            .uberspect(new HelperUberspect())
            .create();

        logger.debug("Initialized.");
    }

    JexlPostProcessor(ScriptNameResolver scriptNameResolver) {

        this(scriptNameResolver, Collections.emptyMap());
    }

    JexlPostProcessor() {
        this(new ClasspathScriptNameResolver());
    }

    @Override
    public Script compileInline(String scriptText) {

        JexlScript script = this.scriptCache.computeIfAbsent(scriptText, this::parse);

        return new de.dmeiners.mapping.impl.jexl.JexlScript(script, this.extensions);
    }

    private JexlScript parse(String scriptText) {

        String preparedScriptText = this.ensureLastExpressionIsTarget(scriptText);

        JexlScript script;

        try {

            script = engine.createScript(preparedScriptText, "target");
        } catch (JexlException e) {
            throw new ParseException(String.format("Error parsing script text: '%s'", preparedScriptText), e);
        }
        return script;
    }

    private String ensureLastExpressionIsTarget(String scriptText) {
        return String.format("%s; target;", scriptText);
    }
}
