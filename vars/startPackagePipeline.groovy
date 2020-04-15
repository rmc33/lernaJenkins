import org.rmc33.lernaJenkins.Utilities


def call(closure) {
    def config = [:]

    closure.delegate = config
    closure.resolveStrategy = Closure.DELEGATE_ONLY
    closure()

    def scriptPath

    for (e in config.branchMapping) {
        println "branch name ${env.BRANCH_NAME}"
        if (env.BRANCH_NAME.startsWith(e.key)) {
            scriptPath = e.value.path
        }
    }

    node {
        sh "PATH=$PATH:./node_modules/.bin/:/usr/local/bin/"
        checkout scm: [$class: 'GitSCM', branches: [[name: env.BRANCH_NAME]], extensions: [],  userRemoteConfigs: [[credentialsId: 'GITHUB_ID', url: 'https://github.com/rmc33/lernaJenkins.git']]]
        println "loading class ${env.WORKSPACE}/${scriptPath}"
        def pipeline = load "${env.WORKSPACE}/${scriptPath}"
        def changedPackages = pipeline.listChangedPackages(this)

        stage("Running pipeline for packages") {
            changedPackages.each { packageName ->
                pipeline.runPipeline(this, packageName)
            }
        }
    }
}