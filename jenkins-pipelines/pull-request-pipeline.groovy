

def runBeforePackagesBuild(script, branchConfig, config) {
    steps.sh "yarn"
    steps.sh "lerna bootstrap"
}

def runPackageBuild(script, packageProperties, branchConfig, config) {
    println "runPipeline ${packageProperties.name}"
    //build package
    script.sh "yarn build"
    //test package
    //scan package
}

def runAfterPackagesBuild(script, branchConfig, config) {

}

return this;