package de.dmeiners.mapping.impl.jexl;

import de.dmeiners.mapping.api.BasePostProcessor;
import de.dmeiners.mapping.api.ExecutionException;
import de.dmeiners.mapping.api.ParseException;
import de.dmeiners.mapping.api.ResultTypeException;
import de.dmeiners.mapping.api.ScriptNameResolver;
import de.dmeiners.mapping.api.ScriptText;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlException;
import org.apache.commons.jexl3.JexlScript;
import org.apache.commons.jexl3.MapContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public <T> List<T> process(Collection<T> targets, ScriptText scriptText, Map<String, Object> context) {

        JexlScript script = this.parse(scriptText);

        return targets.stream()
            .map(target -> executeScript(target, context, script))
            .map(result -> castResult(targets.iterator().next(), result))
            .collect(Collectors.toList());
    }

    private JexlScript parse(ScriptText scriptText) {

        ScriptText preparedScriptText = this.ensureLastExpressionIsTarget(scriptText);

        JexlScript script;

        try {

            script = engine.createScript(preparedScriptText.getText(), "target");
        } catch (JexlException e) {
            throw new ParseException(String.format("Error parsing script text: '%s'", preparedScriptText), e);
        }
        return script;
    }

    private ScriptText ensureLastExpressionIsTarget(ScriptText scriptText) {
        return ScriptText.of(String.format("%s; target;", scriptText.getText()));
    }

    private <T> Object executeScript(T target, Map<String, Object> context, JexlScript script) {

        Object result;

        try {
            result = script.execute(new MapContext(context), target);
        } catch (JexlException e) {
            throw new ExecutionException(String.format("Error executing parsed script: '%s'",
                script.getParsedText()), e);
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
