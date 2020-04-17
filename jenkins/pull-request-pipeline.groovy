
def listChangedPackages(steps) {
    steps.echo "getChangedPackages"
    return org.rmc33.lernaJenkins.Utilities.listChangedPackagesGitDiff(steps, 'remotes/origin/develop')
}

def runPackagePipeline(script, packageName) {
    script.echo "runPipeline ${packageName}"
}

def runAfterPackagesPipeline(script) {

}

return this;