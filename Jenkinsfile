#!groovy?
echo "startLernaPipeline start..."

node {
    startLernaPipeline {
        branchMapping = [
            "feature": [path: "jenkins/pull-request-pipeline.groovy", sinceBranch: "remotes/origin/develop"],
            "develop": [path: "jenkins/develop-pipeline.groovy", sinceBranch: "remotes/origin/master"],
            "hotfix": [path: "jenkins/hotfix-pipeline.groovy", sinceBranch: "remotes/origin/master"],
            "master": [path: "jenkins/master-pipeline.groovy", listAll: true],
            "release": [path: "jenkins/release-pipeline.groovy", sinceBranch: "remotes/origin/master"]
        ]
        credentialsId = 'GITHUB_ID'
        gitUrl = 'https://github.com/rmc33/lernaJenkins.git'
        nodeJsHome = '/usr/local'
    }
}