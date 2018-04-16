package de.dmeiners.mapping.impl.java

import de.dmeiners.mapping.api.ScriptNameResolver
import spock.lang.Specification

class JavaPostProcessorNoOpSpec extends Specification {

    def "processes no-op inline script on a single target"() {

        given: "a no-op script"
        def scriptText = ""
        def target = "Unchanged"
        def subject = new JavaPostProcessor()

        expect: "the script to do nothing"
        def script = subject.compileInline(scriptText)
        script.execute(target, [:]) == target
    }

    def "processes no-op inline script on multiple targets"() {

        given: "a no-op script"
        def scriptText = ""
        def target = "Unchanged"
        def subject = new JavaPostProcessor()

        expect: "the script to do nothing"
        def script = subject.compileInline(scriptText)
        script.execute([target, target], [:]) == [target, target]
    }

    def "processes no-op script on a single target"() {

        given: "a no-op script"
        def scriptName = "no-op"
        def target = "Unchanged"
        ScriptNameResolver scriptNameResolver = Mock()
        def subject = new JavaPostProcessor(scriptNameResolver)
        scriptNameResolver.resolve(scriptName, [:]) >> ""

        when:
        def script = subject.compile(scriptName, [:])
        def result = script.execute(target, [:])

        then: "the script does nothing"
        result == target
    }

    def "processes no-op script on multiple targets"() {

        given: "a no-op script"
        def scriptName = "no-op"
        def target = "Unchanged"
        ScriptNameResolver scriptNameResolver = Mock()
        def subject = new JavaPostProcessor(scriptNameResolver)
        scriptNameResolver.resolve(scriptName, [:]) >> ""

        when:
        def script = subject.compile(scriptName, [:])
        def result = script.execute([target, target], [:])

        then: "the script does nothing"
        result == [target, target]
    }
}
