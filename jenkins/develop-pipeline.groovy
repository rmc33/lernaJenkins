import org.rmc33.lernaJenkins.Utilities

def listChangedPackages(steps) {
    steps.echo "getChangedPackages"
    def releaseVersion = steps.sh (script: "node -p -e \"require('./package.json').version\"", returnStdout: true)
    return Utilities.listChangedPackagesGitDiff(steps, "remotes/origin/master") //release/${releaseVersion}")
}

def runBeforePackagesPipeline(script) {
    script.sh "git checkout develop"
    script.sh "yarn"
}

def runPackagePipeline(script, packageName) {
    script.echo "runPipeline ${packageName}"
    script.dir("packages/${packageName}") {
        //bump up package version (can also ask for user input on version number)
        script.sh "npm version patch"
        script.sh "git add ."
        script.sh "git commit -m 'updating version'  &> /dev/null"
        script.sh "git push"
    }
}

def runAfterPackagesPipeline(script) {
    script.echo "create release after develop build"
    //bump up repo version (can also ask for user input on version number)
    def newVersion = script.sh (script: "npm version patch", returnStdout: true)
    script.sh "git add ."
    script.sh "git commit -m 'updating version' &> /dev/null"
    script.sh "git push"
    script.sh "git checkout -b release/${newVersion}"
    script.sh "git push origin release/${newVersion}"
}

return this;