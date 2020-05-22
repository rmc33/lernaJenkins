# lernaJenkins

Jenkins shared library for executing branch specific scripted pipelines for lerna (https://github.com/lerna/lerna) mono repo.

## Getting Started

* Create a new multibranch pipeline in Jenkins.

* Configure pipleine and add lernaJenkins as a shared library (https://jenkins.io/doc/book/pipeline/shared-libraries/).

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
        gitUrl = 'https://github.com/your git hub'
        nodeJsHome = '/usr/local'
    }
}
```

Or use default pipeline script for all branches

```
node {
    startPackagePipeline {
        branchMapping = [
            "default": [path: "jenkins/pipeline.groovy"]
        ]
        credentialsId = 'GITHUB_ID'
        gitUrl = 'https://github.com/your git hub'
    }
}
```

* Create directory in lerna repo for pipeline script/s.

```
(root)
+- Jenkinsfile           # Jenkinsfile in repo to call startPackagePipeline
+- jenkins               # directory for pipeline scripts called by startPackagePipeline
|   +- develop-pipeline.groovy
|   +- master-pipeline.groovy
|   ...
```


## Branch pipeline lifecycle methods

Lerna jenkins includes sample branch pipeline scripts. startPackagePipeline will load the pipeline script defined in branchMapping and the lifecycle methods will be called in the following order:

* listChangedPackages
* runBeforePackagesPipeline
* runPackagePipeline
* runAfterPackagesPipeline


## Pipeline script

A Pipeline script should implement the lifecycle methods and end with a return this. You may import the lernaJenkins.Utilities or any other shared libary for use in the pipline script.

Example script:
```
import org.rmc33.lernaJenkins.Utilities

def listChangedPackages(steps) {
    steps.echo "getChangedPackages"
    return Utilities.listChangedPackagesLerna(steps)
}

def runBeforePackagesPipeline(script) {
    script.sh "yarn"
}

def runPackagePipeline(script, packageName) {
    script.echo "runPipeline ${packageName}"
    script.dir("packages/${packageName}") {
        script.sh "yarn test"
        script.sh "yarn publish"
        script.sh "yarn deploy"
    }
}

def runAfterPackagesPipeline(script) {
    script.echo "pipeline finished successfully"
}

return this;
```


## lernaJenkins.Utilities 

* listChangedPackagesGitDiff(steps, targetBranch)
* listChangedPackagesLerna(steps)
* listAllPackages(steps)

