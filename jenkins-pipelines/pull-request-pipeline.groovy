

def runBeforePackagesPipeline(script, branchConfig, config) {
    steps.sh "yarn"
}

def runPackagePipeline(script, packageProperties, branchConfig, config) {
    script.echo "runPipeline ${packageProperties.name}"
    //scan package
    //test package
}

def runAfterPackagesPipeline(script, branchConfig, config) {
    //functional test all packages
}

return this;