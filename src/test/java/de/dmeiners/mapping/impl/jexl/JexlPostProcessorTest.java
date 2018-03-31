package de.dmeiners.mapping.impl.jexl;

import de.dmeiners.mapping.api.ResultTypeException;
import org.junit.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class JexlPostProcessorTest {

    @Test
    public void throwsExceptionIfScriptReturnsWrongType() {

        String scriptText = "return target.length()";
        String target = "Hello World!";
        JexlPostProcessor subject = new JexlPostProcessor();

        assertThatThrownBy(() -> subject.processInline(target, scriptText, Collections.emptyMap()))
            .isInstanceOf(ResultTypeException.class)
            .hasMessage("Script did not return an object of type 'java.lang.String'.");
    }
}