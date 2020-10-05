

def runBeforePackagesPipeline(script, config) {
    script.sh "git checkout master"
    script.sh "yarn"
}

def runPackagePipeline(script, packageProperties, config) {
    def packageName = packageProperties.name
    script.echo "runPipeline ${packageName}"
}

def runAfterPackagesPipeline(script, config) {
    withCredentials([usernamePassword(credentialsId: 'GITHUB_USER', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
        def releaseVersion = steps.sh (script: "node -p -e \"require('./package.json').version\"", returnStdout: true)
        script.sh "git tag $releaseVersion"
        script.sh 'git push origin --tags'
    }
}

return this;