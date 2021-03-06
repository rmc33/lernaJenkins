
import org.rmc33.lernaJenkins.LernaUtilities

def runBeforePackagesBuild(script, branchConfig, config) {
    script.sh "yarn"
    steps.sh "lerna bootstrap"
}

def runPackageBuild(script, packageProperties, branchConfig, config) {
    println "runPipeline ${packageProperties.name}"
    //build package
    script.sh "yarn build"
    //test package
    //scan package
    //deploy or publish package
}

def runAfterPackagesBuild(script, branchConfig, config) {
    withCredentials([usernamePassword(credentialsId: config.credentialsId, passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USER')]) {
        def releaseVersion = LernaUtilities.getRootVersion(script, config)
        script.sh "git tag ${releaseVersion}"
        script.sh 'git push origin --tags'
    }
}

return this;