package de.dmeiners.mapping.impl.jexl

import spock.lang.Specification

class JexlPostProcessorSimpleScriptSpec extends Specification {

    def "processes a simple inline script on a single target"() {

        given:
        def scriptText = "target += ' World!'"
        def target = "Hello"
        def subject = new JexlPostProcessor()

        expect:
        def script = subject.compileInline(scriptText)
        script.execute(target, [:]) == "Hello World!"
    }

    def "processes a simple inline script on multiple targets"() {

        given:
        def scriptText = "target += ' World!'"
        def targets = ["Hello", "Goodbye"]
        def subject = new JexlPostProcessor()

        expect:
        def script = subject.compileInline(scriptText)
        script.execute(targets, [:]) == ["Hello World!", "Goodbye World!"]
    }
}
