package de.dmeiners.mapping.api;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface PostProcessor {

    /**
     * @throws ScriptParseException     if the script can not be parsed
     * @throws ScriptExecutionException if an error occurs during script execution
     * @throws ResultTypeException      if the script returns another type than the target object has
     */
    <T> T process(T target, String scriptName, Map<String, Object> context);

    /**
     * @throws ScriptParseException     if the script can not be parsed
     * @throws ScriptExecutionException if an error occurs during script execution
     * @throws ResultTypeException      if the script returns another type than the target object has
     */
    <T> T processInline(T target, String scriptText, Map<String, Object> context);

    /**
     * @throws ScriptParseException     if the script can not be parsed
     * @throws ScriptExecutionException if an error occurs during script execution
     * @throws ResultTypeException      if the script returns another type than the target object has
     */
    <T> List<T> process(Collection<T> targets, String scriptName, Map<String, Object> context);

    /**
     * @throws ScriptParseException     if the script can not be parsed
     * @throws ScriptExecutionException if an error occurs during script execution
     * @throws ResultTypeException      if the script returns another type than the target object has
     */
    <T> List<T> processInline(Collection<T> targets, String scriptText, Map<String, Object> context);
}
