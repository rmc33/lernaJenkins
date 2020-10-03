import org.rmc33.lernaJenkins.LernaUtilities

def listChangedPackages(steps, config) {
    steps.echo "getChangedPackages"
    return LernaUtilities.listChangedPackagesSince(steps, "remotes/origin/master")
}

def runBeforePackagesPipeline(script, config) {
    steps.sh "yarn"
}

def runPackagePipeline(script, packageProperties, config) {
    def packageName = packageProperties.name
    script.echo "runPipeline ${packageName}"
    //scan package
    //deploy or publish RC package
}

def runAfterPackagesPipeline(script, config) {
    //merge to master
    script.input message: 'Approve Merge to maser?', ok: 'Yes'
    withCredentials([usernamePassword(credentialsId: 'GITHUB_USER', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
        script.sh "git pull"
        script.sh "git checkout master"
        script.sh "git merge ${env.BRANCH_NAME}"
        script.sh 'git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/rmc33/lernaJenkins.git'
    }
}

return this;