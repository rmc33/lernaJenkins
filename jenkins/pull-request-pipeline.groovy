

def runBeforePackagesPipeline(script, config) {
    steps.sh "yarn"
}

def runPackagePipeline(script, packageProperties, config) {
    script.echo "runPipeline ${packageProperties.name}"
    //scan package
    //test package
}

def runAfterPackagesPipeline(script) {
    //functional test all packages
}

return this;