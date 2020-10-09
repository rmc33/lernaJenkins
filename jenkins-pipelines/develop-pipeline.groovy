import org.rmc33.lernaJenkins.GitUtilities


def runBeforePackagesPipeline(script, branchConfig, config) {
    script.sh "git checkout develop"
    script.sh "yarn"
}

def runPackagePipeline(script, packageProperties, branchConfig, config) {
    script.echo "runPipeline ${packageProperties.name}"
    GitUtilities.releaseVersion(script, config.credentialsId, [gitTagVersion: false, versionTagPrefix: ""])
}

def runAfterPackagesPipeline(script, branchConfig, config) {
    script.echo "create release after develop build"
    //bump up repo version get user input for version number
    def newVersion = GitUtilities.releaseVersion(script, config.credentialsId)
    if (newVersion) {
        script.sh "git checkout -b release/${newVersion}"
        script.sh "git push origin release/${newVersion}"
    }
}

return this;