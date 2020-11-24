#!groovy?
echo "startLernaPipeline start..."
node {
    sh 'printenv'
    startLernaPipeline {
        branchMapping = [
            "feature": [path: "jenkins-pipelines/pull-request-pipeline.groovy", since: "remotes/origin/develop"],
            "develop": [path: "jenkins-pipelines/develop-pipeline.groovy", since: "remotes/origin/master"],
            "hotfix": [path: "jenkins-pipelines/hotfix-pipeline.groovy", since: "remotes/origin/master"],
            "master": [path: "jenkins-pipelines/master-pipeline.groovy", listAll: true],
            "release": [path: "jenkins-pipelines/release-pipeline.groovy", since: "remotes/origin/master"]
        ]
        credentialsId = 'GITHUB_ID'
        credentialsPw = 'GIT_PASSWORD'
        credentialsUser = 'GIT_USERNAME'
        gitUrl = 'https://github.com/rmc33/lernaJenkins.git'
        nodeJsHome = '/usr/local'
    }
}