import org.rmc33.lernaJenkins.Utilities


def call(closure) {
    def config = [:]

    closure.delegate = config
    closure.resolveStrategy = Closure.DELEGATE_ONLY
    closure()

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
        stage("Running pipeline for packages") {
            echo "p=$PATH"
            sh "PATH=$PATH:./node_modules/.bin/:/usr/local/bin/"
            checkout scm: [$class: 'GitSCM', branches: [[name: env.BRANCH_NAME]], extensions: [],  userRemoteConfigs: [[credentialsId: 'GITHUB_ID', url: 'https://github.com/rmc33/lernaJenkins.git']]]
            println "loading class ${env.WORKSPACE}/${scriptPath}"
            pipeline = load "${env.WORKSPACE}/${scriptPath}"
            changedPackages = pipeline.listChangedPackages(this)
        }
        changedPackages.each { packageName ->
            pipeline.runPipeline(this, packageName)
        }
    }
}