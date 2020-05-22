import org.rmc33.lernaJenkins.Utilities

def listChangedPackages(steps) {
    steps.echo "getChangedPackages"
    def releaseVersion = steps.sh (script: "node -p -e \"require('./package.json').version\"", returnStdout: true)
    return Utilities.listChangedPackagesGitDiff(steps, "remotes/origin/master") //release/${releaseVersion}")
}

def runBeforePackagesPipeline(script) {
    script.sh "git checkout develop"
    script.sh "yarn"
}

def runPackagePipeline(script, packageName) {
    script.echo "runPipeline ${packageName}"
    script.sh "yarn config set version-tag-prefix ''"
    script.dir("packages/${packageName}") {
        withCredentials([usernamePassword(credentialsId: 'GITHUB_USER', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
            //bump up package version (should also get user input for version number)
            script.sh "yarn --new-version version -m 'updating version' --no-git-tag-version patch"
            script.sh 'git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/rmc33/lernaJenkins.git'
        }
    }
}

def runAfterPackagesPipeline(script) {
    script.echo "create release after develop build"
    script.sh "yarn config set version-tag-prefix ''"
    //bump up repo version (should also get user input for version number)
    withCredentials([usernamePassword(credentialsId: 'GITHUB_USER', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
        script.sh "yarn --new-version version -m 'updating version' --no-git-tag-version patch"
        script.sh 'git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/rmc33/lernaJenkins.git'
        script.sh "git checkout -b release/${newVersion}"
        script.sh "git push origin release/${newVersion}"
    }
}

return this;