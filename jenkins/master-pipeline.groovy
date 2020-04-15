
def listChangedPackages(utilities, steps) {
    steps.echo "getChangedPackages"
    steps.sh "npm install yarn"
    steps.sh "yarn"
    return utilities.listChangedPackagesLerna(steps)
}

def runPipeline(script, packageName) {
    script.echo "runPipeline ${packageName}"
}
return this;