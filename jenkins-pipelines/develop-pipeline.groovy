import org.rmc33.lernaJenkins.YarnUtilities
import org.rmc33.lernaJenkins.LernaUtilities

def runBeforePackagesBuild(script, branchConfig, config) {
    script.sh "yarn"
    script.sh "lerna bootstrap"
}

def runPackageBuild(script, packageProperties, branchConfig, config) {
    println "runPipeline ${packageProperties.name}"
    script.sh "yarn build"
    //ask to update develop version of package
    if (LernaUtilities.isIndependentVersioning(script, config)) {
        withCredentials([usernamePassword(credentialsId: config.credentialsId, passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USER')]) {
            YarnUtilities.inputToUpdateVersion(script, [gitTagVersion: false, versionTagPrefix: ""])
        }
    }
}

def runAfterPackagesBuild(script, branchConfig, config) {
    println "runAfterPackagesBuild"
    //update root version and create release branch
    withCredentials([usernamePassword(credentialsId: config.credentialsId, passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USER')]) {
        YarnUtilities.inputToCreateReleaseBranch(script, config, [gitTagVersion: false, versionTagPrefix: ""], "")
    }
}

return this;