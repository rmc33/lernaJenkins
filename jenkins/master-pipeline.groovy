
import org.rmc33.lernaJenkins.LernaUtilities

def listChangedPackages(steps, config) {
    steps.echo "getChangedPackages"
    return Utilities.listChangedPackages(steps)
}

def runBeforePackagesPipeline(script, config) {
    script.sh "git checkout master"
    script.sh "yarn"
}

def runPackagePipeline(script, packageName, config) {
    script.echo "runPipeline ${packageName}"
    script.dir("packages/${packageName}") {
        def releaseVersion = steps.sh (script: "node -p -e \"require('./package.json').version\"", returnStdout: true)
        script.sh "git tag ${packageName}@${releaseVersion}"
    }
}

def runAfterPackagesPipeline(script, config) {
    //lerna publish and deploy to prod
    withCredentials([usernamePassword(credentialsId: 'GITHUB_USER', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
        def releaseVersion = steps.sh (script: "node -p -e \"require('./package.json').version\"", returnStdout: true)
        script.sh "git tag $releaseVersion"
        script.sh 'git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/rmc33/lernaJenkins.git --tags'
    }
}

return this;