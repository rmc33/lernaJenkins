import org.rmc33.lernaJenkins.YarnUtilities
import org.rmc33.lernaJenkins.LernaUtilities

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
    //ask to update root version and create release branch
    withCredentials([usernamePassword(credentialsId: config.credentialsId, passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USER')]) {
        YarnUtilities.inputToCreateReleaseBranch(script, config, [gitTagVersion: false, versionTagPrefix: ""])
    }
}

def runAfterPackageBuild(script, packageProperties, branchConfig, config) {
    println "runAfterPackageBuild"
}

return this;