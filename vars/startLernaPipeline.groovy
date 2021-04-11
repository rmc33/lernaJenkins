import org.rmc33.lernaJenkins.LernaUtilities

def call(closure) {
    def config = [:]
    def scriptPath
    def changedPackages
    def pipeline
    def branchConfig
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
            branchConfig = e.value
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

    if (!config.rootPath) {
        config.rootPath = pwd()
    }
    
    config.branchName = branchName

    stage("Cloning repo") {
        deleteDir()
        checkout scm: [$class: 'GitSCM', branches: [[name: branchName]], extensions: [],  userRemoteConfigs: [[credentialsId: config.credentialsId, url: config.gitUrl]]]
        println "loading repo branch pipeline ${env.WORKSPACE}/${scriptPath}"
        config.changedPackages = LernaUtilities.listChangedPackages(this, branchConfig)
        def changedPackageNames = config.changedPackages.each { p ->
            return p.name
        }.join(",")
        println "changedPackageNames = ${changedPackageNames}"
        pipeline = load "${env.WORKSPACE}/${scriptPath}"
    }

    stage("Running runBeforePackagesBuild") {
        if (pipeline.runBeforePackagesBuild) {
            pipeline.runBeforePackagesBuild(this, branchConfig, config)
        }
    }

    stage("Running runPackageBuild(s)") {
        config.changedPackages.each { p ->
            dir("${p.location}") {
                if (pipeline.runPackageBuild) {
                    pipeline.runPackageBuild(this, p, branchConfig, config)
                }
            }
        }
    }

    stage("Running runAfterPackagesBuild") {
        if (pipeline.runAfterPackagesBuild) {
            pipeline.runAfterPackagesBuild(this, branchConfig, config)
        }
    }

    stage("Running runAfterPackageBuild") {
        config.changedPackages.each { p ->
            dir("${p.location}") {
                if (pipeline.runAfterPackageBuild) {
                    pipeline.runAfterPackageBuild(this, p, branchConfig, config)
                }
            }
        }
    }

}