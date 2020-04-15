import org.rmc33.lernaJenkins.Utilities

def listChangedPackages(steps) {
    steps.echo "getChangedPackages"
    steps.sh "yarn"
    return org.rmc33.lernaJenkins.Utilities.listChangedPackagesLerna(steps)
}

def runPipeline(script, packageName) {
    script.echo "runPipeline ${packageName}"
}
return this;