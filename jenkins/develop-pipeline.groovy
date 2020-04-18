import org.rmc33.lernaJenkins.Utilities

def listChangedPackages(steps) {
    steps.echo "getChangedPackages"
    def releaseVersion = steps.sh script: "node -p -e \"require('./package.json').version\""
    return Utilities.listChangedPackagesGitDiff(steps, "remotes/origin/release/${releaseVersion}")
}

def runPackagePipeline(script, packageName) {
    script.echo "runPipeline ${packageName}"
}

def runAfterPackagesPipeline(script) {

}

return this;