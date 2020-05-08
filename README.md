# lernaJenkins

Jenkins shared library for executing branch specific scripted pipelines for lerna (https://github.com/lerna/lerna) mono repo's.

## Getting Started

* Add lernaJenkins as a shared library (https://jenkins.io/doc/book/pipeline/shared-libraries/) in Jenkins.

* Add Jenkinsfile to project root and call global variable startPackagePipeline with branchMapping, gitUrl, credentialsId and nodeJsHome (optional).

```
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
```

Or use default script for all branches

```
node {
    startPackagePipeline {
        branchMapping = [
            "default": [path: "jenkins/pipeline.groovy"]
        ]
        credentialsId = 'GITHUB_ID'
        gitUrl = 'https://github.com/rmc33/lernaJenkins.git'
    }
}
```

* Create directory in lerna repo for jenkins pipelines and add branch pipeline script/s.

```
(root)
+- Jenkinsfile           # Jenkinsfile in repo to call startPackagePipeline
+- jenkins               # branch pipeline scripts called by startPackagePipeline
|   +- develop-pipeline.groovy
|   +- master-pipeline.groovy
|   ...
```

## Branch pipeline lifecycle methods

* listChangedPackages
* runBeforePackagesPipeline
* runPackagePipeline
* runAfterPackagesPipeline


