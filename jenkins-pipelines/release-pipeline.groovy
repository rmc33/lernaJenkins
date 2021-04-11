
import org.rmc33.lernaJenkins.LernaUtilities
import org.rmc33.lernaJenkins.YarnUtilities

def runBeforePackagesBuild(script, branchConfig, config) {
    steps.sh "yarn"
    steps.sh "lerna bootstrap"
}

def runPackageBuild(script, packageProperties, branchConfig, config) {
    println "runPipeline ${packageProperties.name}"
    //build package
    script.sh "yarn build"
    script.sh "yarn test"

    //update package versions
    if (LernaUtilities.isIndependentVersioning(script, config)) {
        //ask to update develop version of each package for next release
        withCredentials([usernamePassword(credentialsId: config.credentialsId, passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USER')]) {
            YarnUtilities.inputToUpdateVersion(script, [gitTagVersion: true, versionTagPrefix: ""])
        }
    }
    else {
        //update all packages with root version if not independent versioning
        script.sh "yarn version --new-version ${LernaUtilities.getRootVersion(script, config)}"
    }
}

def runAfterPackagesBuild(script, branchConfig, config) {
    //merge to master
    script.input message: 'Approve Merge to maser?', ok: 'Yes'
    withCredentials([usernamePassword(credentialsId: config.credentialsId, passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USER')]) {
        script.sh "git checkout master"
        script.sh "git merge remotes/origin/${env.BRANCH_NAME}"
        script.sh 'git push origin master'
    }
}

return this;