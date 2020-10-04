import org.rmc33.lernaJenkins.LernaUtilities
import org.rmc33.lernaJenkins.GitUtilities


def listChangedPackages(script, config) {
    steps.echo "getChangedPackages"
    return LernaUtilities.listChangedPackagesSince(steps, "remotes/origin/master")
}

def runBeforePackagesPipeline(script, config) {
    script.sh "git checkout develop"
    script.sh "yarn"
}

def runPackagePipeline(script, Package packageProperties, config) {
    script.echo "runPipeline ${packageProperties.name}"
    script.dir("${packageProperties.location}") {
        withCredentials([usernamePassword(credentialsId: 'GITHUB_USER', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
            //bump up package version (should also get user input for version number)
            GitUtilities.releaseVersion(script, "https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/rmc33/lernaJenkins.git", null)
        }
    }
}

def runAfterPackagesPipeline(script, config) {
    script.echo "create release after develop build"
    //bump up repo version get user input for version number
    withCredentials([usernamePassword(credentialsId: 'GITHUB_USER', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
        def newVersion = GitUtilities.releaseVersion(script, "https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/rmc33/lernaJenkins.git", null)
        if (newVersion) {
            script.sh "git checkout -b release/${newVersion}"
            script.sh "git push origin release/${newVersion}"
        }
    }
}

return this;