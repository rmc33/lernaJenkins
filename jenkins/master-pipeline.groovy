
def getChangedPackages(script) {
    println "getChangedPackages"
    return ['webpack-demo']
}

def runPipeline(script, packageName) {
    println "runPipeline ${packageName}"
}
