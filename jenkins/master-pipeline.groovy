
import org.rmc33.lernaJenkins.Utilities

def listChangedPackages(steps) {
    steps.echo "getChangedPackages"
    return Utilities.listChangedPackagesLerna(steps)
}

def runBeforePackagesPipeline(script) {
    script.sh "yarn"
}

def runPackagePipeline(script, packageName) {
    script.echo "runPipeline ${packageName}"
}

def runAfterPackagesPipeline(script) {

}

return this;