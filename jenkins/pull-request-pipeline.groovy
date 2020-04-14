@Library('lernaJenkins') _
import org.rmc33.lernaJenkins.Utilities

def listChangedPackages(steps) {
    println "getChangedPackages"
    return listChangedPackagesGitDiff(steps, 'remotes/origin/develop')
}

def runPipeline(script, packageName) {
    println "runPipeline ${packageName}"
}
