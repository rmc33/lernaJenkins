package org.rmc33.lernaJenkins

class Utilities {

   static def listChangedPackagesGitDiff(steps, targetBranch) {
        def changedPackages = new HashSet<String>()
        String diffFilesList = steps.sh(script: "git diff ${targetBranch} --name-only", returnStdout: true)
        List<String> files = Arrays.asList(diffFilesList.split("\\r?\\n"))
        files.each { file ->
            def matcher = file =~ /packages\/(.*?)\//
            if (matcher) {
                changedPackages.add(matcher[0][1])
            }
        }
        changedPackages
    }

    static def listChangedPackagesLerna(steps) {
        String changedPackages = steps.sh(script: "lerna changed", returnStdout: true)
        return Arrays.asList(changedPackages.split("\\r?\\n"))
    }

    static def listAllPackages(steps) {
        String packages = steps.sh(script: "lerna list", returnStdout: true)
        return Arrays.asList(packages.split("\\r?\\n"))
    }
}