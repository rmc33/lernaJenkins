# lernaJenkins

Jenkins shared library for executing branch specific scripted pipelines for lerna mono repo.

## Getting Started

* Create a new multibranch pipeline in Jenkins.

* Configure pipeline and add lernaJenkins as a shared library https://jenkins.io/doc/book/pipeline/shared-libraries/.

* Add Jenkinsfile to project root and call global variable startPackagePipeline with branchMapping, gitUrl, credentialsId and nodeJsHome (optional).

```
node {
    startLernaPipeline {
        branchMapping = [
            "feature": [path: "jenkins/pull-request-pipeline.groovy"],
            "develop": [path: "jenkins/develop-pipeline.groovy"],
            "hotfix": [path: "jenkins/hotfix-pipeline.groovy"],
            "master": [path: "jenkins/master-pipeline.groovy"],
            "release": [path: "jenkins/release-pipeline.groovy"]
        ]
        credentialsId = 'GITHUB_ID'
        gitUrl = 'https://github.com/your git hub'
        nodeJsHome = '/usr/local'
    }
}
```

Or use default pipeline script for all branches

```
node {
    startLernaPipeline {
        branchMapping = [
            "default": [path: "jenkins/pipeline.groovy"]
        ]
        credentialsId = 'GITHUB_ID'
        gitUrl = 'https://github.com/your git hub'
    }
}
```

* branchMapping is a Map with keys representing a string to match the beginning of the branch name. The values should be an object with the following properties:

```
path - the path to the pipeline script (required)
since - the branch to compare with to determine changed packages. Translates to running the lerna ls -since $since command. Leaving this out will result in using the lerna changed command. (optional)
listAll - indicates that all packages should be listed when determining changed packages. (optional)
```

* Create directory in lerna repo for pipeline script/s.

```
(root)
+- Jenkinsfile           # Jenkinsfile in repo to call startPackagePipeline
+- jenkins-pipelines               # directory for pipeline scripts called by startLernaPipeline
|   +- develop-pipeline.groovy
|   +- master-pipeline.groovy
|   ...
```


## Branch pipeline lifecycle methods

lernaJenkins includes sample branch pipelines. startLernaPipeline will load the pipeline file defined in branchMapping and the lifecycle methods will be called in the following order:

* runBeforePackagesPipeline - runs at workspace directory before getting changed packages
* runPackagePipeline - runs at package location directory for each changed package
* runAfterPackagesPipeline - runs at workspace directory after all changed packages have completed


## Pipeline script

A Pipeline script should implement the lifecycle methods and end with a return this. You may import any of the org.rmc33.lernaJenkins or other shared library for use in the pipeline.

Example pipeline:

```

def runBeforePackagesPipeline(script, branchConfig, config) {
    script.sh "yarn"
}

def runPackagePipeline(script, packageProperties, branchConfig, config) {
    script.echo "runPipeline ${packageProperties.name}"
    script.sh "yarn test"
    GitUtilities.releaseVersion(script, config.credentialsId)
    script.sh "yarn publish"
    script.sh "yarn deploy"
}

def runAfterPackagesPipeline(script, branchConfig, config) {
    script.echo "pipeline finished successfully"
}

return this;
```


[![lerna](https://img.shields.io/badge/maintained%20with-lerna-cc00ff.svg)](https://lerna.js.org/)
