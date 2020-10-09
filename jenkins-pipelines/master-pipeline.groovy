

def runBeforePackagesPipeline(script, branchConfig, config) {
    script.sh "git checkout master"
    script.sh "yarn"
}

def runPackagePipeline(script, packageProperties, branchConfig, config) {
    def packageName = packageProperties.name
    script.echo "runPipeline ${packageName}"
}

def runAfterPackagesPipeline(script, branchConfig, config) {
    withCredentials([usernamePassword(credentialsId: config.credentialsId, passwordVariable: config.credentialsPw, usernameVariable: config.credentialsUser)]) {
        def releaseVersion = steps.sh (script: "node -p -e \"require('./package.json').version\"", returnStdout: true)
        script.sh "git tag $releaseVersion"
        script.sh 'git push origin --tags'
    }
}

return this;