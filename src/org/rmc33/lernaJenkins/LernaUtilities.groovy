package org.rmc33.lernaJenkins

import groovy.json.JsonSlurper

class LernaUtilities {

    static def listChangedPackages(script, branchConfig) {

        if (branchConfig.listAll) {
            return LernaUtilities.listAllPackages(script)
        }

        def since = branchConfig.since

        String changedPackages = since ? script.sh(script: "node_modules/.bin/lerna ls --since ${since} --json", returnStdout: true)
                : script.sh(script: "node_modules/.bin/lerna changed --json", returnStdout: true)
        def jsonSlurper = new JsonSlurper()
        def jsonObjects = jsonSlurper.parseText(changedPackages)
        return jsonObjects.collect {
            new Package(it.name, it.location)
        }
    }

    static def listAllPackages(script) {
        String jsonPackages = script.sh(script: "node_modules/.bin/lerna list --json", returnStdout: true)
        def jsonSlurper = new JsonSlurper()
        def jsonObjects = jsonSlurper.parseText(jsonPackages)
        return jsonObjects.collect {
            new Package(it.name, it.location)
        }
    }

    static def isIndependentVersioning(script, config) {
        script.dir(config.rootPath) {
            def version = script.sh (script: "node -p -e \"require('./lerna.json').version\"", returnStdout: true)
            if (version == 'independent') {
                return true
            }
        }
        return false
    }

    static def getRootVersion(script, config) {
        script.dir(config.rootPath) {
            def version = script.sh (script: "node -p -e \"require('./package.json').version\"", returnStdout: true)
            return version
        }
    }
}