import org.rmc33.lernaJenkins.LernaUtilities

def listChangedPackages(steps, config) {
    steps.echo "getChangedPackages"
    return LernaUtilities.listChangedPackagesSince(steps, 'remotes/origin/develop')
}

def runBeforePackagesPipeline(script, config) {
    steps.sh "yarn"
}

def runPackagePipeline(script, packageName, config) {
    script.echo "runPipeline ${packageName}"
    //scan package
    //test package
}

def runAfterPackagesPipeline(script) {
    //functional test all packages
}

return this;