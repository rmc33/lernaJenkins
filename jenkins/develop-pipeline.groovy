import org.rmc33.lernaJenkins.LernaUtilities
import org.rmc33.lernaJenkins.GitUtilities

def listChangedPackages(steps, config) {
    steps.echo "getChangedPackages"
    def releaseVersion = steps.sh (script: "node -p -e \"require('./package.json').version\"", returnStdout: true)
    return LernaUtilities.listChangedPackagesSince(steps, "remotes/origin/master")
}

def runBeforePackagesPipeline(script, config) {
    script.sh "git checkout develop"
    script.sh "yarn"
}

def runPackagePipeline(script, packageProperties, config) {
    def packageName = packageProperties.name
    script.echo "runPipeline ${packageName}"
    script.sh "yarn config set version-tag-prefix ''"
    script.sh "yarn config set version-git-message 'updating version'"
    script.dir("packages/${packageName}") {
        withCredentials([usernamePassword(credentialsId: 'GITHUB_USER', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
            //bump up package version (should also get user input for version number)
            GitUtilities.releaseVersion(script, "https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/rmc33/lernaJenkins.git", false)
        }
    }
}

def runAfterPackagesPipeline(script, config) {
    script.echo "create release after develop build"
    script.sh "yarn config set version-tag-prefix ''"
    script.sh "yarn config set version-git-message 'updating version'"
    //bump up repo version get user input for version number
    withCredentials([usernamePassword(credentialsId: 'GITHUB_USER', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
        var newVersion = GitUtilities.releaseVersion(script, "https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/rmc33/lernaJenkins.git", false)
        if (newVersion) {
            script.sh "git checkout -b release/${newVersion}"
            script.sh "git push origin release/${newVersion}"
        }
    }
}

return this;