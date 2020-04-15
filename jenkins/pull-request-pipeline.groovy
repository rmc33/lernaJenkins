def listChangedPackages(utilities, steps) {
    steps.echo "getChangedPackages"
    return utilities.listChangedPackagesGitDiff(steps, 'remotes/origin/develop')
}

def runPipeline(script, packageName) {
    script.echo "runPipeline ${packageName}"
}

return this;