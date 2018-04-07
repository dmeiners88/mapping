package de.dmeiners.mapping.sample;

import de.dmeiners.mapping.api.PostProcessor;
import de.dmeiners.mapping.api.PostProcessorFactory;
import de.dmeiners.mapping.api.ScriptText;
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

        String target = "Hello";

        String result = processor.process(target, ScriptText.of("target += ' World!'"), Collections.emptyMap());
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

        String target = "Hello";

        ScriptText script = ScriptText.of("target += ` ${user.firstName} ${user.lastName}`");

        String result = processor.process(target, script, context);
        // end::contextUsage[]

        logger.debug(result);
    }
}
