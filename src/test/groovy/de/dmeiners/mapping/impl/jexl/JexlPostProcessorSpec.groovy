package de.dmeiners.mapping.impl.jexl

import de.dmeiners.mapping.api.ScriptExecutionException
import de.dmeiners.mapping.api.ScriptNameResolver
import de.dmeiners.mapping.api.ScriptParseException
import spock.lang.Specification

class JexlPostProcessorSpec extends Specification {

    def "processes no-op inline script on a single target"() {

        given: "a no-op script"
        def scriptText = ""
        def target = "Unchanged"
        def subject = new JexlPostProcessor()

        expect: "the script to do nothing"
        subject.processInline(target, scriptText, [:]) == target
    }

    def "processes no-op inline script on multiple targets"() {

        given: "a no-op script"
        def scriptText = ""
        def target = "Unchanged"
        def subject = new JexlPostProcessor()

        expect: "the script to do nothing"
        subject.processInline([target, target], scriptText, [:]) == [target, target]
    }

    def "processes no-op script on a single target"() {

        given: "a no-op script"
        def scriptName = "no-op"
        def target = "Unchanged"
        ScriptNameResolver scriptNameResolver = Mock()
        def subject = new JexlPostProcessor(scriptNameResolver)
        scriptNameResolver.resolve(scriptName) >> ""

        when:
        def result = subject.process(target, scriptName, [:])

        then: "the script does nothing"
        result == target
    }

    def "processes no-op script on multiple targets"() {

        given: "a no-op script"
        def scriptName = "no-op"
        def target = "Unchanged"
        ScriptNameResolver scriptNameResolver = Mock()
        def subject = new JexlPostProcessor(scriptNameResolver)
        scriptNameResolver.resolve(scriptName) >> ""

        when:
        def result = subject.process([target, target], scriptName, [:])

        then: "the script does nothing"
        result == [target, target]
    }

    def "throws an exception if a script text cannot be parsed"() {

        given:
        def scriptText = "target.length("
        def target = "Hello World!"
        def subject = new JexlPostProcessor()

        when:
        subject.processInline(target, scriptText, [:])

        then:
        def e = thrown(ScriptParseException)
        e.message == "Error parsing script text: 'target.length(; target;'"
    }

    def "throws an exception if a script execution fails"() {

        given:
        def scriptText = "target.subtract(1)"
        def target = "Hello World!"
        def subject = new JexlPostProcessor()

        when:
        subject.processInline(target, scriptText, [:])

        then:
        def e = thrown(ScriptExecutionException)
        e.message == "Error executing parsed script: 'target.subtract(1);\ntarget;\n'"
    }
}
