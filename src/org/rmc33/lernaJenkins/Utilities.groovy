package org.rmc33.lernaJenkins

class Utilities {

   static def listChangedPackagesGitDiff(steps, targetBranch) {
        def changedPackages = new HashSet<String>()
        steps.echo "git diff ${targetBranch} --name-only"
        String diffFilesList = steps.sh(script: "git diff ${targetBranch} --name-only", returnStdout: true)
        List<String> files = Arrays.asList(diffFilesList.split("\\n"))
        files.each { file ->
            def allPackages = listAllPackages(steps)
            allPackages.each { packageName ->
                def matcher = file =~ /${packageName}\/.*\//
                if (matcher) {
                    changedPackages.add(packageName)
                }
            }
        }
        changedPackages
    }

    static def listChangedPackagesLerna(steps) {
        String changedPackages = steps.sh(script: "node_modules/.bin/lerna changed", returnStdout: true)
        return Arrays.asList(changedPackages.split("\\n"))
    }

    static def listAllPackages(steps) {
        String packages = steps.sh(script: "node_modules/.bin/lerna list", returnStdout: true)
        return Arrays.asList(packages.split("\\n"))
    }
}