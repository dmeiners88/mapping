package de.dmeiners.mapping.sample;

import de.dmeiners.mapping.api.PostProcessor;
import de.dmeiners.mapping.api.PostProcessorFactory;
import de.dmeiners.mapping.api.Script;
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
    }

    private static void simpleUsage() {

        // tag::simpleUsage[]
        PostProcessor processor = PostProcessorFactory.create();

        String someObject = "Hello";

        Script script = processor.compileInline("target += ' World!'");

        String result = script.execute(someObject, Collections.emptyMap());
        // end::simpleUsage[]

        logger.debug(result);
    }

    private static void contextUsage() {

        // tag::contextUsage[]
        PostProcessor processor = PostProcessorFactory.create();

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
}
