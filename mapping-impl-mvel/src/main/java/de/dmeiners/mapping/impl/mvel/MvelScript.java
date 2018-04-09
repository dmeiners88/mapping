package de.dmeiners.mapping.impl.mvel;

import de.dmeiners.mapping.api.BaseScript;
import de.dmeiners.mapping.api.ExecutionException;
import de.dmeiners.mapping.api.ResultTypeException;
import org.mvel2.CompileException;
import org.mvel2.MVEL;
import org.mvel2.integration.impl.MapVariableResolverFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MvelScript extends BaseScript {

    private final Object script;

    public MvelScript(Object script) {
        this.script = script;
    }

    @Override
    public <T> List<T> execute(Collection<T> targets, Map<String, Object> context) {

        MapVariableResolverFactory factory = new MapVariableResolverFactory(new HashMap<>(context));

        return targets.stream()
            .map(target -> executeScript(target, factory, script))
            .map(result -> castResult(targets.iterator().next(), result))
            .collect(Collectors.toList());
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
