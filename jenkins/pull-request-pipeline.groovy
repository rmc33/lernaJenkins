
def listChangedPackages(steps) {
    steps.echo "getChangedPackages"
    return org.rmc33.lernaJenkins.Utilities.listChangedPackagesGitDiff(steps, 'remotes/origin/develop')
}

def runPipeline(script, packageName) {
    script.echo "runPipeline ${packageName}"
}

return this;