import org.rmc33.lernaJenkins.LernaUtilities


def listChangedPackages(steps) {
    steps.echo "getChangedPackages"
    def releaseVersion = steps.sh (script: "node -p -e \"require('./package.json').version\"", returnStdout: true)
    return LernaUtilities.listChangedPackagesSince(steps, "remotes/origin/master")
}

def runBeforePackagesPipeline(script) {
    script.sh "git checkout develop"
    script.sh "yarn"
}

def runPackagePipeline(script, packageName) {
    script.echo "runPipeline ${packageName}"
    script.sh "yarn config set version-tag-prefix ''"
    script.sh "yarn config set version-git-message 'updating version'"
    script.dir("packages/${packageName}") {
        withCredentials([usernamePassword(credentialsId: 'GITHUB_USER', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
            //bump up package version (should also get user input for version number)
            script.sh "yarn version --no-git-tag-version --new-version patch"
            script.sh 'git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/rmc33/lernaJenkins.git'
        }
    }
}

def runAfterPackagesPipeline(script) {
    script.echo "create release after develop build"
    script.sh "yarn config set version-tag-prefix ''"
    script.sh "yarn config set version-git-message 'updating version'"
    //bump up repo version (should also get user input for version number)
    withCredentials([usernamePassword(credentialsId: 'GITHUB_USER', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
        script.sh "yarn version --no-git-tag-version --new-version patch"
        script.sh 'git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/rmc33/lernaJenkins.git'
        def newVersion = steps.sh (script: "node -p -e \"require('./package.json').version\"", returnStdout: true)
        script.sh "git checkout -b release/${newVersion}"
        script.sh "git push origin release/${newVersion}"
    }
}

return this;