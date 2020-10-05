package org.rmc33.lernaJenkins

import groovy.json.JsonSlurper

class LernaUtilities {

    static def listChangedPackages(steps, since) {
        String changedPackages = since ? steps.sh(script: "node_modules/.bin/lerna ls --since ${since} --json", returnStdout: true)
                : steps.sh(script: "node_modules/.bin/lerna changed --json", returnStdout: true)
        def jsonSlurper = new JsonSlurper()
        def jsonObjects = jsonSlurper.parseText(changedPackages)
        return jsonObjects.collect {
            new Package(it.name, it.location)
        }
    }

    static def listAllPackages(steps) {
        String jsonPackages = steps.sh(script: "node_modules/.bin/lerna list --json", returnStdout: true)
        def jsonSlurper = new JsonSlurper()
        def jsonObjects = jsonSlurper.parseText(jsonPackages)
        return jsonObjects.collect {
            new Package(it.name, it.location)
        }
    }

}