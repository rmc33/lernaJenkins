
def runBeforePackagesBuild(script, branchConfig, config) {
    steps.sh "yarn"
    steps.sh "lerna bootstrap"
}

def runPackageBuild(script, packageProperties, branchConfig, config) {
    println "runPipeline ${packageProperties.name}"
    //build package
    script.sh "yarn build"
    //scan package
    //deploy or publish package
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