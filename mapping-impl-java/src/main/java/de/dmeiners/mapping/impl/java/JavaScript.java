package de.dmeiners.mapping.impl.java;

import de.dmeiners.mapping.api.BaseScript;
import de.dmeiners.mapping.api.ExecutionException;
import de.dmeiners.mapping.api.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class JavaScript extends BaseScript {

    private static final Logger logger = LoggerFactory.getLogger(JavaScript.class);

    private final String packageName;
    private final String className;
    private final String fqcn;
    private final String scriptText;
    private final JavaCompiler compiler;
    private final ConcurrentMap<String, ScriptLambda> scriptCache;

    public JavaScript(String packageName, String className, String scriptText, JavaCompiler compiler,
                      ConcurrentMap<String, ScriptLambda> scriptCache) {

        this.packageName = packageName;
        this.className = className;
        this.fqcn = packageName + "." + className;
        this.scriptText = scriptText;
        this.compiler = compiler;
        this.scriptCache = scriptCache;
    }

    @Override
    public <T> List<T> execute(Collection<T> targets, Map<String, Object> context) {

        if (targets.isEmpty()) {
            return Collections.emptyList();
        }

        ScriptLambda<T> script = getCached(targets.iterator().next().getClass());

        return targets.stream()
            .map(target -> script.apply(target, context))
            .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    private <T> ScriptLambda<T> getCached(Class<?> targetClass) {

        if (this.scriptCache.containsKey(fqcn)) {

            logger.debug("Reusing cached instance of class '{}'.", fqcn);
            return this.scriptCache.get(fqcn);
        }

        String source = SourceCodeFactory.createSourceCode(packageName, className, scriptText, targetClass);
        byte[] bytecode = compile(source);
        ScriptLambda<T> scriptLambda = instantiate(fqcn, bytecode);

        this.scriptCache.put(fqcn, scriptLambda);

        return scriptLambda;
    }

    @SuppressWarnings("unchecked")
    private <T> ScriptLambda<T> instantiate(String fqcn, byte[] bytecode) {

        Class<? extends ScriptLambda> scriptClass = ByteArrayClassLoader.classFromBytes(ScriptLambda.class, fqcn, bytecode);
        ScriptLambda<T> script;
        try {
            script = scriptClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ExecutionException("Error instantiating script class.", e);
        }
        return script;
    }

    private byte[] compile(String source) {

        JavaFileObject sourceFile = new JavaSourceFromString(className, source);

        ByteArrayJavaFileManager outputFileManager = new ByteArrayJavaFileManager(
            compiler.getStandardFileManager(null, null, null));

        CompilationTask task = compiler.getTask(null, outputFileManager,
            new Slf4jDiagnosticListener(), null, null, Collections.singletonList(sourceFile));

        if (!task.call()) {
            throw new ParseException(String.format("Error parsing script text: '%s'", scriptText));
        }

        return outputFileManager.getFileObjects().get(0).getOutputStream().toByteArray();
    }

}
