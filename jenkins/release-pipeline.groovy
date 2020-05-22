import org.rmc33.lernaJenkins.Utilities

def listChangedPackages(steps) {
    steps.echo "getChangedPackages"
    return Utilities.listChangedPackagesGitDiff(steps, "remotes/origin/master")
}

def runBeforePackagesPipeline(script) {
    steps.sh "yarn"
}

def runPackagePipeline(script, packageName) {
    script.echo "runPipeline ${packageName}"
    //scan package
    //deploy or publish RC package
}

def runAfterPackagesPipeline(script) {
    //merge to master
     userInput = input(
        id: 'Proceed1', message: 'Was this successful?', parameters: [
        [$class: 'BooleanParameterDefinition', defaultValue: true, description: '', name: 'Build completed. Please confirm would like to merge to master']
    ])
    if (userInput == true) {
        withCredentials([usernamePassword(credentialsId: 'GITHUB_USER', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
            script.sh "git checkout master"
            script.sh "git merge ${env.BRANCH_NAME}"
            script.sh 'git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/rmc33/lernaJenkins.git'
        }
    }
}

return this;