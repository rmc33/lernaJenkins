import org.rmc33.lernaJenkins.YarnUtilities

def runBeforePackagesBuild(script, branchConfig, config) {
    script.sh "yarn"
    script.sh "lerna bootstrap"
}

def runPackageBuild(script, packageProperties, branchConfig, config) {
    println "runPipeline ${packageProperties.name}"
    script.sh "yarn build"
}

def runAfterPackagesBuild(script, branchConfig, config) {
    println "runAfterPackagesBuild"
    //ask to create release branch and update next develop version
    withCredentials([usernamePassword(credentialsId: config.credentialsId, passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USER')]) {
        def createdRelease = YarnUtilities.inputToCreateReleaseBranch(script, config, [gitTagVersion: false, versionTagPrefix: ""])
        if (createdRelease) {
            if (LernaUtilities.isIndependentVersioning()) {
                script.sh "lerna version -y --conventional-commit"
            }
            else {
                script.sh "lerna version -y ${newDevelopVersion}"
            }
        }
    }
}

return this;