package org.rmc33.lernaJenkins

import groovy.json.JsonSlurper
import java.util.stream.*

class LernaUtilities {

    static def listChangedPackages(steps) {
        String changedPackages = steps.sh(script: "node_modules/.bin/lerna changed --json", returnStdout: true)
        def jsonSlurper = new JsonSlurper()
        def jsonObjects = jsonSlurper.parseText(changedPackages)
        return jsonObjects.collect {
            new Package(it.name, it.location)
        }
    }

    static def listChangedPackagesSince(steps, fromBranch) {
        String changedPackages = steps.sh(script: "node_modules/.bin/lerna ls --since ${fromBranch} --json", returnStdout: true)
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