#!groovy?

startPackagePipeline(script: this, 
    branchMapping: [
        "feature": [path: "jenkins/pull-request-pipeline.groovy"],
        "develop": [path: "jenkins/develop-pipeline.groovy", releaseStart: true],
        "hotfix": [path: "jenkins/hotfix-pipeline.groovy"],
        "master": [path: "jenkins/master-pipeline.groovy"],
        "release": [path: "jenkins/release-pipeline.groovy", releaseEnd: true]
    ]
)