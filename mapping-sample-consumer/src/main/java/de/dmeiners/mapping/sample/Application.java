package de.dmeiners.mapping.sample;

import de.dmeiners.mapping.api.PostProcessor;
import de.dmeiners.mapping.api.PostProcessorFactory;
import de.dmeiners.mapping.api.ScriptText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

public class Application {

    public static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {

        logger.debug("Startup");

        PostProcessor processor = PostProcessorFactory.create();

        String target = "Hello";

        String result = processor.process(target, ScriptText.of("target += ' World!'"), Collections.emptyMap());

        logger.debug(result);
    }
}
