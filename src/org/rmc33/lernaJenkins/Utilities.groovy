package org.rmc33.lernaJenkins

import groovy.json.JsonSlurper

class Utilities {

   static def listChangedPackagesGitDiff(steps, targetBranch) {
        def changedPackages = new HashSet<String>()
        targetBranch = targetBranch.trim()
        steps.echo "target branch=[${targetBranch}]"
        String diffFilesList = steps.sh(script: "git diff ${targetBranch} --name-only", returnStdout: true)
        List<String> files = Arrays.asList(diffFilesList.split("\\n"))
        for(String file: files) {
            List<String> allPackages = listAllPackagesLerna(steps)
            for (String package: allPackages) {
                def matcher = file =~ /${package.name}\/.*\//
                if (matcher) {
                    changedPackages.add(package.name)
                }
            }
        }
        changedPackages
    }

    static def listChangedPackagesLerna(steps) {
        String changedPackages = steps.sh(script: "node_modules/.bin/lerna changed", returnStdout: true)
        return Arrays.asList(changedPackages.split("\\n"))
    }

    static def listAllPackagesLerna(steps) {
        String jsonPackages = steps.sh(script: "node_modules/.bin/lerna list --json", returnStdout: true)
        def jsonSlurper = new JsonSlurper()
        def packages = jsonSlurper.parseText(jsonPackages)
        return packages
    }
}