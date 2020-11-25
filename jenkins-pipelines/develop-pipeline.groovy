import org.rmc33.lernaJenkins.YarnUtilities


def runBeforePackagesPipeline(script, branchConfig, config) {
    script.sh "git checkout develop"
    script.sh "yarn"
}

def runPackagePipeline(script, packageProperties, branchConfig, config) {
    script.echo "runPipeline ${packageProperties.name}"
    withCredentials([usernamePassword(credentialsId: config.credentialsId, passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USER')]) {
        YarnUtilities.updateVersion(script, [gitTagVersion: false, versionTagPrefix: ""])
    }
}

def runAfterPackagesPipeline(script, branchConfig, config) {
    script.echo "create release after develop build"
    //bump up repo version get user input for version number
    withCredentials([usernamePassword(credentialsId: config.credentialsId, passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USER')]) {
        def newVersion = YarnUtilities.updateVersion(script, null)
        if (newVersion) {
            script.sh "git checkout -b release/${newVersion}"
            script.sh "git push origin release/${newVersion}"
        }
    }
}

return this;