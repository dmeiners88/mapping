package de.dmeiners.mapping.impl.jexl;

import de.dmeiners.mapping.api.BasePostProcessor;
import de.dmeiners.mapping.api.ParseException;
import de.dmeiners.mapping.api.Script;
import de.dmeiners.mapping.api.ScriptNameResolver;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlException;
import org.apache.commons.jexl3.JexlScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JexlPostProcessor extends BasePostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(JexlPostProcessor.class);

    private final JexlEngine engine;

    public JexlPostProcessor(ScriptNameResolver scriptNameResolver) {

        super(scriptNameResolver);

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


    @Override
    public Script compileInline(String scriptText) {

        JexlScript script = this.parse(scriptText);

        return new de.dmeiners.mapping.impl.jexl.JexlScript(script);
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
