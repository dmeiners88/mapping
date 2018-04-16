package de.dmeiners.mapping.impl.java;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.util.Map;

public class SourceCodeFactory {

    public static String createSourceCode(String packageName, String className, String scriptText, Class<?> targetClass) {

        ParameterizedTypeName contextTypeName = ParameterizedTypeName.get(Map.class, String.class, Object.class);

        MethodSpec apply = MethodSpec.methodBuilder("apply")
            .addAnnotation(Override.class)
            .addModifiers(Modifier.PUBLIC)
            .returns(targetClass)
            .addParameter(targetClass, "target")
            .addParameter(contextTypeName, "context")
            .addCode(scriptText)
            .addStatement("return target")
            .build();

        ParameterizedTypeName scriptLambda = ParameterizedTypeName.get(ClassName.get(ScriptLambda.class),
            TypeName.get(targetClass));

        TypeSpec type = TypeSpec.classBuilder(className)
            .addModifiers(Modifier.PUBLIC)
            .addSuperinterface(scriptLambda)
            .addMethod(apply)
            .build();

        JavaFile javaFile = JavaFile.builder(packageName, type)
            .build();

        return javaFile.toString();
    }
}
