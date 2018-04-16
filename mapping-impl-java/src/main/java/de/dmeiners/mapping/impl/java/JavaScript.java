package de.dmeiners.mapping.impl.java;

import de.dmeiners.mapping.api.BaseScript;
import de.dmeiners.mapping.api.ExecutionException;
import de.dmeiners.mapping.api.ParseException;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JavaScript extends BaseScript {

    private final String packageName;
    private final String className;
    private final String scriptText;
    private final JavaCompiler compiler;

    public JavaScript(String packageName, String className, String scriptText, JavaCompiler compiler) {

        this.packageName = packageName;
        this.className = className;
        this.scriptText = scriptText;
        this.compiler = compiler;
    }

    @Override
    public <T> List<T> execute(Collection<T> targets, Map<String, Object> context) {

        if (targets.size() == 0) {
            return Collections.emptyList();
        }

        String source = SourceCodeFactory.createSourceCode(packageName, className, scriptText, targets.iterator().next().getClass());

        JavaFileObject sourceFile = new JavaSourceFromString(className, source);

        ByteArrayJavaFileManager outputFileManager = new ByteArrayJavaFileManager(
            compiler.getStandardFileManager(null, null, null));

        CompilationTask task = compiler.getTask(null, outputFileManager,
            new Slf4jDiagnosticListener(), null, null, Collections.singletonList(sourceFile));

        if (!task.call()) {
            throw new ParseException(String.format("Error parsing script text: '%s'", scriptText));
        }

        byte[] bytecode = outputFileManager.getFileObjects().get(0).getOutputStream().toByteArray();

        Class<? extends ScriptLambda> scriptClass = ByteArrayClassLoader.classFromBytes(ScriptLambda.class, packageName + "." + className, bytecode);

        ScriptLambda<T> script = createScriptLambda(scriptClass);

        return targets.stream()
            .map(target -> script.apply(target, context))
            .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    private <T> ScriptLambda<T> createScriptLambda(Class<? extends ScriptLambda> scriptClass) {
        ScriptLambda<T> script;
        try {
            script = scriptClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ExecutionException("Error instantiating script class.", e);
        }
        return script;
    }
}
