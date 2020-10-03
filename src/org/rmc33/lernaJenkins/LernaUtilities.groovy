package org.rmc33.lernaJenkins

import groovy.json.JsonSlurper

class LernaUtilities {

    static def listChangedPackages(steps) {
        String changedPackages = steps.sh(script: "node_modules/.bin/lerna changed --json", returnStdout: true)
        def jsonSlurper = new JsonSlurper()
        return jsonSlurper.parseText(changedPackages)
    }

    static def listChangedPackagesSince(steps, fromBranch) {
        String changedPackages = steps.sh(script: "node_modules/.bin/lerna ls --since ${fromBranch} --json", returnStdout: true)
        def jsonSlurper = new JsonSlurper()
        return jsonSlurper.parseText(changedPackages)
    }

    static def listAllPackages(steps) {
        String jsonPackages = steps.sh(script: "node_modules/.bin/lerna list --json", returnStdout: true)
        def jsonSlurper = new JsonSlurper()
        return jsonSlurper.parseText(jsonPackages)
    }

    private static def r
}