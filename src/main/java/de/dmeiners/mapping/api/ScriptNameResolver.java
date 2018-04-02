package de.dmeiners.mapping.api;

import java.util.Collections;
import java.util.Map;

public interface ScriptNameResolver {

    /**
     * Resolves a given script name into a parsable script text.
     *
     * @param scriptName he script name
     * @return the resolved script text
     * @throws ScriptNameResolutionException if the script name could not be resolved into a script text
     */
    default ScriptText resolve(ScriptName scriptName) {

        return resolve(scriptName, Collections.emptyMap());
    }

    /**
     * Resolves a given script name into a parsable script text. Can optionally use the script's context to
     * do the resolution.
     *
     * @param scriptName The script name
     * @param context    The script context
     * @return the resolved script text
     * @throws ScriptNameResolutionException if the script name could not be resolved into a script text
     */
    ScriptText resolve(ScriptName scriptName, Map<String, Object> context);
}
