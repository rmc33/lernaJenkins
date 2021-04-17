

def runBeforePackagesBuild(script, branchConfig, config) {
    steps.sh "yarn"
    steps.sh "lerna bootstrap"
}

def runPackageBuild(script, packageProperties, branchConfig, config) {
    println "runPipeline ${packageProperties.name}"
    //build package
    script.sh "yarn build"
    script.sh "yarn test"
    //test package
    //scan package
}

return this;