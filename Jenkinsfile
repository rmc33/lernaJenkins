#!groovy?

startPackagePipeline(script: this, 
    branchMapping: [
        "develop": [path: "jenkins/develop-pipeline.groovy", releaseStart: true],
        "feature": [path: "jenkins/pull-request-pipeline.groovy"],
        "hotfix": [path: "jenkins/hotfix-pipeline.groovy"],
        "master": [path: "jenkins/master-pipeline.groovy"],
        "release": [path: "jenkins/release-pipeline.groovy", releaseEnd: true]
    ],
    name: packageName
)