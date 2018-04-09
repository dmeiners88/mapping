package de.dmeiners.mapping.impl.mvel;

import de.dmeiners.mapping.api.BasePostProcessor;
import de.dmeiners.mapping.api.ParseException;
import de.dmeiners.mapping.api.Script;
import de.dmeiners.mapping.api.ScriptNameResolver;
import org.mvel2.CompileException;
import org.mvel2.MVEL;
import org.mvel2.ParserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collections;

public class MvelPostProcessor extends BasePostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(MvelPostProcessor.class);

    private final ParserContext parserContext;

    public MvelPostProcessor(ScriptNameResolver scriptNameResolver) {
        super(scriptNameResolver);

        this.parserContext = new ParserContext();
        this.parserContext.setInterceptors(Collections.singletonMap("classInterceptor", new ClassInterceptor()));

        logger.debug("Initialized");
    }

    @Override
    public Script compileInline(String scriptText) {

        Serializable script = this.parse(scriptText);

        return new MvelScript(script);
    }

    private Serializable parse(String scriptText) {

        String preparedScriptText = this.ensureLastExpressionIsTarget(scriptText);

        Serializable script;
        try {
            script = MVEL.compileExpression(preparedScriptText, this.parserContext);
        } catch (CompileException e) {
            throw new ParseException(String.format("Error parsing script text: '%s'", preparedScriptText), e);
        }


        return script;
    }

    private String ensureLastExpressionIsTarget(String scriptText) {
        return String.format("%s; target;", scriptText);
    }
}
