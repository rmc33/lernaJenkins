
import static org.rmc33.lernaJenkins.Utilities

def listChangedPackages(steps) {
    steps.echo "getChangedPackages"
    steps.sh "yarn"
    return listChangedPackagesLerna(steps)
}

def runPackagePipeline(script, packageName) {
    script.echo "runPipeline ${packageName}"
}

def runAfterPackagesPipeline(script) {

}

return this;