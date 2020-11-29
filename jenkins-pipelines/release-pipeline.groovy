
def runBeforePackagesBuild(script, branchConiig, config) {
    steps.sh "yarn"
}

def runPackageBuild(script, packageProperties, branchConfig, config) {
    script.echo "runPipeline ${packageProperties.name}"
    //scan package
    //deploy or publish RC package
}

def runAfterPackagesBuild(script, branchConfig, config) {
    //merge to master
    script.input message: 'Approve Merge to maser?', ok: 'Yes'
    withCredentials([usernamePassword(credentialsId: config.credentialsId, passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USER')]) {
        script.sh "git fetch"
        script.sh "git checkout master"
        script.sh "git merge remotes/origin/${env.BRANCH_NAME}"
        script.sh 'git push origin master'
    }
}

return this;