package de.dmeiners.mapping.impl.jexl;

import de.dmeiners.mapping.api.BaseScript;
import de.dmeiners.mapping.api.ExecutionException;
import de.dmeiners.mapping.api.ResultTypeException;
import org.apache.commons.jexl3.JexlException;
import org.apache.commons.jexl3.MapContext;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JexlScript extends BaseScript {

    private final org.apache.commons.jexl3.JexlScript script;

    JexlScript(org.apache.commons.jexl3.JexlScript script) {
        this.script = script;
    }

    @Override
    public <T> List<T> execute(Collection<T> targets, Map<String, Object> context) {

        return targets.stream()
            .map(target -> executeScript(target, context))
            .map(result -> castResult(targets.iterator().next(), result))
            .collect(Collectors.toList());
    }

    private <T> Object executeScript(T target, Map<String, Object> context) {

        Object result;

        try {
            result = this.script.execute(new MapContext(context), target);
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
