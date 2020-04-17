
def listChangedPackages(steps) {
    steps.echo "getChangedPackages"
    steps.sh "yarn"
    return org.rmc33.lernaJenkins.Utilities.listChangedPackagesLerna(steps)
}

def runPackagePipeline(script, packageName) {
    script.echo "runPipeline ${packageName}"
}

def runAfterPackagesPipeline(script) {

}
return this;