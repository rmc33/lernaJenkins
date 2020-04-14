def listChangedPackages(utilities, steps) {
    println "getChangedPackages"
    return utilities.listChangedPackagesGitDiff(steps, 'remotes/origin/develop')
}

def runPipeline(script, packageName) {
    println "runPipeline ${packageName}"
}
