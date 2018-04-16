package de.dmeiners.mapping.impl.java;

import java.util.Map;

public interface ScriptLambda<T> {

    T apply(T target, Map<String, Object> context);
}
