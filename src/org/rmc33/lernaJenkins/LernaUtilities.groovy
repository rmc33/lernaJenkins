package org.rmc33.lernaJenkins

import groovy.json.JsonSlurper

class LernaUtilities {

    static def listChangedPackages(steps) {
        String changedPackages = steps.sh(script: "node_modules/.bin/lerna changed --json", returnStdout: true)
        return new JsonSlurper().parseText(jsonPackages)
    }

    static def listChangedPackagesSince(steps, fromBranch) {
        String changedPackages = steps.sh(script: "node_modules/.bin/lerna ls --since ${fromBranch} --json", returnStdout: true)
        return new JsonSlurper().parseText(jsonPackages)
    }

    static def listAllPackages(steps) {
        String jsonPackages = steps.sh(script: "node_modules/.bin/lerna list --json", returnStdout: true)
        return new JsonSlurper().parseText(jsonPackages)
    }

    private static def r
}