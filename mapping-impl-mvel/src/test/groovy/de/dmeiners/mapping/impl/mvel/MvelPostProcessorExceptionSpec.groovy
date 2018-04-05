package de.dmeiners.mapping.impl.mvel

import de.dmeiners.mapping.api.ExecutionException
import de.dmeiners.mapping.api.ParseException
import de.dmeiners.mapping.api.ScriptText
import spock.lang.Specification

class MvelPostProcessorExceptionSpec extends Specification {

    def "throws an exception if a script text cannot be parsed"() {

        given:
        def scriptText = ScriptText.of("target.length(")
        def target = "Hello World!"
        def subject = new MvelPostProcessor()

        when:
        subject.process(target, scriptText, [:])

        then:
        def e = thrown(ParseException)
        e.message == "Error parsing script text: 'target.length(; target;'"
    }

    def "throws an exception if a script execution fails"() {

        given:
        def scriptText = ScriptText.of("target.bogus(1)")
        def target = "Hello World!"
        def subject = new MvelPostProcessor()

        when:
        subject.process(target, scriptText, [:])

        then:
        def e = thrown(ExecutionException)
        e.message == "Error executing parsed script: 'null;\n<<END_OF_STATEMENT>>;\ntarget;\n<<END_OF_STATEMENT>>;\n'";
    }
}
