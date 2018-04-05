package de.dmeiners.mapping.impl.mvel;

import de.dmeiners.mapping.api.BasePostProcessor;
import de.dmeiners.mapping.api.ExecutionException;
import de.dmeiners.mapping.api.ParseException;
import de.dmeiners.mapping.api.ResultTypeException;
import de.dmeiners.mapping.api.ScriptNameResolver;
import de.dmeiners.mapping.api.ScriptText;
import org.mvel2.CompileException;
import org.mvel2.MVEL;
import org.mvel2.ParserContext;
import org.mvel2.integration.impl.MapVariableResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public <T> List<T> process(Collection<T> targets, ScriptText scriptText, Map<String, Object> context) {

        Object script = parse(scriptText);
        MapVariableResolverFactory factory = new MapVariableResolverFactory(new HashMap<>(context));

        return targets.stream()
            .map(target -> executeScript(target, factory, script))
            .map(result -> castResult(targets.iterator().next(), result))
            .collect(Collectors.toList());
    }

    private Serializable parse(ScriptText scriptText) {

        ScriptText preparedScriptText = this.ensureLastExpressionIsTarget(scriptText);

        Serializable script;
        try {
            script = MVEL.compileExpression(preparedScriptText.getText(), this.parserContext);
        } catch (CompileException e) {
            throw new ParseException(String.format("Error parsing script text: '%s'", preparedScriptText), e);
        }


        return script;
    }

    private ScriptText ensureLastExpressionIsTarget(ScriptText scriptText) {
        return ScriptText.of(String.format("%s; target;", scriptText.getText()));
    }

    private <T> Object executeScript(T target, MapVariableResolverFactory factory, Object script) {

        Object result;

        try {
            factory.createVariable("target", target);
            result = MVEL.executeExpression(script, factory);
        } catch (CompileException e) {
            throw new ExecutionException(String.format("Error executing parsed script: '%s'",
                script.toString()), e);
        }
        return result;
    }

    private <T> T castResult(T target, Object result) {

        if (!target.getClass().isInstance(result)) {
            throw new ResultTypeException(String.format("Script did not return an object of type '%s'.",
                target.getClass().getName()));
        }

        // The above check should let this "cast" never fail. At runtime we are dealing with objects anyway.
        return (T) result;
    }
}
