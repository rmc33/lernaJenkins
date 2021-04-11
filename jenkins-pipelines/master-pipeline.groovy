

def runBeforePackagesBuild(script, branchConfig, config) {
    script.sh "yarn"
    steps.sh "lerna bootstrap"
}

def runPackageBuild(script, packageProperties, branchConfig, config) {
    script.echo "runPipeline ${packageProperties.name}"
    //build package
    script.sh "yarn build"
    //test package
    //scan package
    //deploy or publish package
}

def runAfterPackagesBuild(script, branchConfig, config) {
    withCredentials([usernamePassword(credentialsId: config.credentialsId, passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USER')]) {
        def releaseVersion = steps.sh (script: "node -p -e \"require('./package.json').version\"", returnStdout: true)
        script.sh "git tag $releaseVersion"
        script.sh 'git push origin --tags'
    }
}

return this;