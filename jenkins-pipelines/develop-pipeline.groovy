import org.rmc33.lernaJenkins.YarnUtilities


def runBeforePackagesBuild(script, branchConfig, config) {
    script.sh "yarn"
    script.sh "lerna bootstrap"
}

def runPackageBuild(script, packageProperties, branchConfig, config) {
    script.echo "runPipeline ${packageProperties.name}"
    script.sh "yarn build"
    //ask to update develop version of package
    if (LernaUtilities.isIndependentVersioning(script, '../../')) {
        withCredentials([usernamePassword(credentialsId: config.credentialsId, passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USER')]) {
        YarnUtilities.inputToUpdateVersion(script, [gitTagVersion: false, versionTagPrefix: ""])
    }
}

def runAfterPackagesBuild(script, branchConfig, config) {
    script.echo "create release after develop build"
    //ask to create new release branch for all new package changes (uses root package.jon)
    withCredentials([usernamePassword(credentialsId: config.credentialsId, passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USER')]) {
        YarnUtilities.inputToUpdateVersion(script, [gitTagVersion: false, versionTagPrefix: ""])
        YarnUtilities.inputToCreateReleaseBranch(script)
    }
}

return this;