
def getChangedPackages(script) {
    script.echo "getChangedPackages"
    return ['webpack-demo']
}

def runPipeline(script, packageName) {
    script.echo "runPipeline ${packageName}"
}
