package de.dmeiners.mapping.api;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface PostProcessor {

    /**
     * @throws ParseException     if the script can not be parsed
     * @throws ExecutionException if an error occurs during script execution
     * @throws ResultTypeException      if the script returns another type than the target object has
     */
    <T> T process(T target, ScriptName scriptName, Map<String, Object> context);

    /**
     * @throws ParseException     if the script can not be parsed
     * @throws ExecutionException if an error occurs during script execution
     * @throws ResultTypeException      if the script returns another type than the target object has
     */
    <T> T process(T target, ScriptText scriptText, Map<String, Object> context);

    /**
     * @throws ParseException     if the script can not be parsed
     * @throws ExecutionException if an error occurs during script execution
     * @throws ResultTypeException      if the script returns another type than the target object has
     */
    <T> List<T> process(Collection<T> targets, ScriptName scriptName, Map<String, Object> context);

    /**
     * @throws ParseException     if the script can not be parsed
     * @throws ExecutionException if an error occurs during script execution
     * @throws ResultTypeException      if the script returns another type than the target object has
     */
    <T> List<T> process(Collection<T> targets, ScriptText scriptText, Map<String, Object> context);
}
