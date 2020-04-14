@Library('lernaJenkins') _
import org.rmc33.lernaJenkins.Utilities

def listChangedPackages(steps) {
    steps.echo "getChangedPackages"
    steps.sh "yarn"
    return listChangedPackagesLerna(steps)
}

def runPipeline(script, packageName) {
    script.echo "runPipeline ${packageName}"
}
