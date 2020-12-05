

def runBeforePackagesBuild(script, branchConfig, config) {
    steps.sh "yarn"
}

def runPackageBuild(script, packageProperties, branchConfig, config) {
    script.echo "runPipeline ${packageProperties.name}"
    //scan package
    //test package
}

def runAfterPackagesBuild(script, branchConfig, config) {
    //functional test all packages
}

return this;