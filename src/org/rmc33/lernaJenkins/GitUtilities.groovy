package org.rmc33.lernaJenkins

import groovy.json.JsonSlurper

class GitUtilities {

   static def listChangedPackages(steps, targetBranch) {
        def changedPackages = new HashSet<String>()
        targetBranch = targetBranch.trim()
        steps.echo "target branch=[${targetBranch}]"
        String diffFilesList = steps.sh(script: "git diff ${targetBranch} --name-only", returnStdout: true)
        List<String> files = Arrays.asList(diffFilesList.split("\\n"))
        for(String file: files) {
            def allPackages = listAllPackagesLerna(steps)
            allPackages.each { p ->
                def matcher = file =~ /${p.name}\/.*\//
                if (matcher) {
                    changedPackages.add(p.name)
                }
            }
        }
        changedPackages
    }

//    static def bumpVersion(steps, packageName) {
//
//    }
}