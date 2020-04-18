
def call(closure) {
    def config = [:]

    closure.delegate = config
    closure.resolveStrategy = Closure.DELEGATE_ONLY
    closure()

    if (!config.credentialsId || !config.gitUrl) {
        println "credientialsId, gitUrl and nodeJsHome are required"
        return
    }

    def scriptPath
    def changedPackages
    def pipeline

    for (e in config.branchMapping) {
        println "branch name ${env.BRANCH_NAME}"
        if (env.BRANCH_NAME.startsWith(e.key)) {
            scriptPath = e.value.path
        }
    }

    node {

        if (config.nodeJsHome) {
            env.NODEJS_HOME = config.nodeJsHome
            env.PATH="${env.NODEJS_HOME}/bin:${env.PATH}"
        }

        stage("Running pipeline for packages") {
            checkout scm: [$class: 'GitSCM', branches: [[name: env.BRANCH_NAME]], extensions: [],  userRemoteConfigs: [[credentialsId: config.credentialsId, url: config.gitUrl]]]
            println "loading class ${env.WORKSPACE}/${scriptPath}"
            pipeline = load "${env.WORKSPACE}/${scriptPath}"
            changedPackages = pipeline.listChangedPackages(this)
            changedPackages.each { packageName ->
                pipeline.runPackagePipeline(this, packageName)
            }
        }
        stage("Running after packages pipeline") {
            pipeline.runAfterPackagesPipeline(this)
        }
    }
}