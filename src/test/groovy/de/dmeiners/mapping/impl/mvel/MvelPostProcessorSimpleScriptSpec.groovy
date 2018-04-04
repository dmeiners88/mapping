package de.dmeiners.mapping.impl.mvel

import de.dmeiners.mapping.api.ScriptText
import spock.lang.Specification

class MvelPostProcessorSimpleScriptSpec extends Specification {

    def "processes a simple inline script on a single target"() {

        given:
        def scriptText = ScriptText.of("target += ' World!'")
        def target = "Hello"
        def subject = new MvelPostProcessor()

        expect:
        subject.process(target, scriptText, [:]) == "Hello World!"
    }

    def "processes a simple inline script on multiple targets"() {

        given:
        def scriptText = ScriptText.of("target += ' World!'")
        def targets = ["Hello", "Goodbye"]
        def subject = new MvelPostProcessor()

        expect:
        subject.process(targets, scriptText, [:]) == ["Hello World!", "Goodbye World!"]
    }
}
