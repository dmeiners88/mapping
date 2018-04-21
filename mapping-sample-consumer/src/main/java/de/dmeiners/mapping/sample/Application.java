package de.dmeiners.mapping.sample;

import de.dmeiners.mapping.api.PostProcessor;
import de.dmeiners.mapping.api.PostProcessorFactory;
import de.dmeiners.mapping.api.Script;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Application {

    public static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {

        logger.debug("Startup");

        simpleUsage();
        contextUsage();
        exposeLibrary();
    }

    private static void simpleUsage() {

        // tag::simpleUsage[]
        PostProcessor processor = PostProcessorFactory.builder().build();

        String someObject = "Hello";

        Script script = processor.compileInline("target += ' World!'");

        String result = script.execute(someObject, Collections.emptyMap());
        // end::simpleUsage[]

        logger.debug(result);
    }

    private static void contextUsage() {

        // tag::contextUsage[]
        PostProcessor processor = PostProcessorFactory.builder().build();

        Map<String, Object> user = new HashMap<>();
        user.put("firstName", "John");
        user.put("lastName", "Doe");

        Map<String, Object> context = new HashMap<>();
        context.put("user", user);

        String someObject = "Hello";

        String scriptText = "target += ` ${user.firstName} ${user.lastName}`";

        Script script = processor.compileInline(scriptText);

        String result = script.execute(someObject, context);
        // end::contextUsage[]

        logger.debug(result);
    }

    private static void exposeLibrary() {

        // tag::exposeLibrary[]
        PostProcessor processor = PostProcessorFactory.builder()
            .extension("StringUtils", StringUtils.class)
            .extension("Tools", Tools.class)
            .build();

        Map<String, Object> user = new HashMap<>();
        user.put("firstName", "John");
        user.put("lastName", "Doe");

        Map<String, Object> context = new HashMap<>();
        context.put("user", user);

        String someObject = "Hello";

        String scriptText = "if (StringUtils.isNotBlank(user.firstName)) { target = 'First name is not blank and reversed ' + Tools.reverse(user.firstName); }";

        Script script = processor.compileInline(scriptText);

        String result = script.execute(someObject, context);
        // end::exposeLibrary[]

        logger.debug(result);
    }
}
