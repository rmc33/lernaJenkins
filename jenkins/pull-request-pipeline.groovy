import org.rmc33.lernaJenkins.Utilities

def listChangedPackages(steps) {
    steps.echo "getChangedPackages"
    return listChangedPackagesGitDiff(steps, 'remotes/origin/develop')
}

def runPipeline(script, packageName) {
    script.echo "runPipeline ${packageName}"
}

return this;