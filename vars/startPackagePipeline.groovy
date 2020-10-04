

def call(closure) {
    def config = [:]
    def scriptPath
    def changedPackages
    org.rmc33.lernaJenkins.LernaPipeline pipeline
    def branchName = env.BRANCH_NAME

    closure.delegate = config
    closure.resolveStrategy = Closure.DELEGATE_ONLY
    closure()

    if (!config.credentialsId || !config.gitUrl) {
        println "credientialsId, gitUrl and nodeJsHome are required"
        return
    }

    for (e in config.branchMapping) {
        println "branch name ${env.BRANCH_NAME}"
        if (env.BRANCH_NAME.startsWith(e.key)) {
            scriptPath = e.value.path
            if (e.value.sourceBranch) {
                branchName = e.value.sourceBranch
            }
        }
    }

    if (!scriptPath) {
        if (!config.branchMapping.default || !config.branchMapping.default.path) {
            println "script path not found"
            return
        }
        scriptPath = config.branchMapping.default.path
    }

    if (config.nodeJsHome) {
        env.NODEJS_HOME = config.nodeJsHome
        env.PATH="${env.NODEJS_HOME}/bin:${env.PATH}"
    }

    stage("Cloning repo") {
        deleteDir()
        checkout scm: [$class: 'GitSCM', branches: [[name: branchName]], extensions: [],  userRemoteConfigs: [[credentialsId: config.credentialsId, url: config.gitUrl]]]
        println "loading repo branch pipeline ${env.WORKSPACE}/${scriptPath}"
        pipeline = load "${env.WORKSPACE}/${scriptPath}"
    }

    stage("Running branch pipeline before packages method") {
        pipeline.runBeforePackagesPipeline(this, config)
    }

    stage("Running branch pipeline method for changed packages") {
        changedPackages = pipeline.listChangedPackages(this, config)
        changedPackages.each { p ->
            pipeline.runPackagePipeline(this, p, config)
        }
    }

    stage("Running branch pipeline after packages method") {
        pipeline.runAfterPackagesPipeline(this, config)
    }

}