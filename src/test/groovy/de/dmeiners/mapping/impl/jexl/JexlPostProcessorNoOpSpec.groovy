package de.dmeiners.mapping.impl.jexl

import de.dmeiners.mapping.api.ScriptName
import de.dmeiners.mapping.api.ScriptNameResolver
import de.dmeiners.mapping.api.ScriptText
import spock.lang.Specification

class JexlPostProcessorNoOpSpec extends Specification {

    def "processes no-op inline script on a single target"() {

        given: "a no-op script"
        def scriptText = ScriptText.of("")
        def target = "Unchanged"
        def subject = new JexlPostProcessor()

        expect: "the script to do nothing"
        subject.process(target, scriptText, [:]) == target
    }

    def "processes no-op inline script on multiple targets"() {

        given: "a no-op script"
        def scriptText = ScriptText.of("")
        def target = "Unchanged"
        def subject = new JexlPostProcessor()

        expect: "the script to do nothing"
        subject.process([target, target], scriptText, [:]) == [target, target]
    }

    def "processes no-op script on a single target"() {

        given: "a no-op script"
        def scriptName = ScriptName.of("no-op")
        def target = "Unchanged"
        ScriptNameResolver scriptNameResolver = Mock()
        def subject = new JexlPostProcessor(scriptNameResolver)
        scriptNameResolver.resolve(scriptName, [:]) >> ScriptText.of("")

        when:
        def result = subject.process(target, scriptName, [:])

        then: "the script does nothing"
        result == target
    }

    def "processes no-op script on multiple targets"() {

        given: "a no-op script"
        def scriptName = ScriptName.of("no-op")
        def target = "Unchanged"
        ScriptNameResolver scriptNameResolver = Mock()
        def subject = new JexlPostProcessor(scriptNameResolver)
        scriptNameResolver.resolve(scriptName, [:]) >> ScriptText.of("")

        when:
        def result = subject.process([target, target], scriptName, [:])

        then: "the script does nothing"
        result == [target, target]
    }
}
