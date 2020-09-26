package org.rmc33.lernaJenkins

import groovy.json.JsonSlurper

class LernaUtilities {

    static def listChangedPackages(steps) {
        String changedPackages = steps.sh(script: "node_modules/.bin/lerna changed", returnStdout: true)
        return Arrays.asList(changedPackages.split("\\n"))
    }

    static def listChangedPackagesSince(steps, fromBranch) {
        String changedPackages = steps.sh(script: "node_modules/.bin/lerna ls --since ${fromBranch}", returnStdout: true)
        return Arrays.asList(changedPackages.split("\\n"))
    }

    static def listAllPackages(steps) {
        String jsonPackages = steps.sh(script: "node_modules/.bin/lerna list --json", returnStdout: true)
        def jsonSlurper = new JsonSlurper()
        def packages = jsonSlurper.parseText(jsonPackages)
        return packages
    }
}