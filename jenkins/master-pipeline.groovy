
package org.rmc33.lernaJenkins

def listChangedPackages(steps) {
    steps.echo "getChangedPackages"
    steps.sh "yarn"
    return listChangedPackagesLerna(steps)
}

def runPipeline(script, packageName) {
    script.echo "runPipeline ${packageName}"
}
return this;