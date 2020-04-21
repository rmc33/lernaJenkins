# lernaJenkins

Jenkins shared library for executing branch specific scripted pipelines for lerna mono repo's.

https://jenkins.io/doc/book/pipeline/shared-libraries/

https://github.com/lerna/lerna

Add lernaJenkins as a shared library in Jenkins where repo is built.

Add Jenkinsfile and call global variable startPackagePipeline with branchMapping, gitUrl, credentialsId and nodeJsHome (optional).

Create directory in lerna repo for jenkins pipelines and add branch pipeline scripts.

(root)
+- Jenkinsfile           # Jenkinsfile in repo to call startPackagePipeline
+- jenkins               # branch pipeline scripts called by startPackagePipeline
|   +- develop-pipeline.groovy
|   +- master-pipeline.groovy
|   ...

Branch pipeline script methods

listChangedPackages
runBeforePackagesPipeline
runPackagePipeline
runAfterPackagesPipeline


