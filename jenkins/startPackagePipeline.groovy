@Library('utils') import static org.rmc33.lernaJenkins.Utilities.*

def call(script) {

}

def call(script, config) {
    def matcher = env.BRANCH_NAME =~ /(.*?)\//
    def changedPackages = new HashSet<String>();

    if (matcher) {
        def branchName = matcher[0][1]
        node {
            stage('Find changed packages') {
                echo ('Get changed packages for pipeline branch name=' + env.BRANCH_NAME)
                switch(branchName) {
                    case "feature":
                        changedPackages = findChangedPackages(steps, 'develop')
                        break
                    case "develop":
                        def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
                        def version = matcher[0][1].replace('-SNAPSHOT')
                        changedPackages = findChangedPackages(steps, "release/${version}")
                        break
                    default:
                        changedPackages = findChangedPackages(steps, 'master')
                }
            }
        }
        changedPackages.each { packageName ->
            echo "changed package = ${it}"
            runPackagePipeline(script, branchName, packageName)
        }
    }
}

def runPackagePipeline(script, branchName, packageName) {
    node {
        checkout scm
        //pipeline = load config[branchName]
        //pipeline.runPipeline(packageName)
        echo "starting package pipeline for ${packageName}"
    }
}