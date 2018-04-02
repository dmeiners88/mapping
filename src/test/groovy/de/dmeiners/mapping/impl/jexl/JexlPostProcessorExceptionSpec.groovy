package de.dmeiners.mapping.impl.jexl

import de.dmeiners.mapping.api.ScriptExecutionException
import de.dmeiners.mapping.api.ScriptParseException
import de.dmeiners.mapping.api.ScriptText
import spock.lang.Specification

class JexlPostProcessorExceptionSpec extends Specification {

    def "throws an exception if a script text cannot be parsed"() {

        given:
        def scriptText = ScriptText.of("target.length(")
        def target = "Hello World!"
        def subject = new JexlPostProcessor()

        when:
        subject.process(target, scriptText, [:])

        then:
        def e = thrown(ScriptParseException)
        e.message == "Error parsing script text: 'target.length(; target;'"
    }

    def "throws an exception if a script execution fails"() {

        given:
        def scriptText = ScriptText.of("target.bogus(1)")
        def target = "Hello World!"
        def subject = new JexlPostProcessor()

        when:
        subject.process(target, scriptText, [:])

        then:
        def e = thrown(ScriptExecutionException)
        e.message == "Error executing parsed script: 'target.bogus(1);\ntarget;\n'"
    }
}
