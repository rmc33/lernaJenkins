#!groovy?
echo "startPackagePipeline start..."

node {
    startPackagePipeline {
        branchMapping = [
            "feature": [path: "jenkins/pull-request-pipeline.groovy"],
            "develop": [path: "jenkins/develop-pipeline.groovy"],
            "hotfix": [path: "jenkins/hotfix-pipeline.groovy"],
            "master": [path: "jenkins/master-pipeline.groovy"],
            "release": [path: "jenkins/release-pipeline.groovy"]
        ]
        credentialsId = 'GITHUB_ID'
        gitUrl = 'https://github.com/rmc33/lernaJenkins.git'
        nodeJsHome = '/usr/local'
    }
}