import groovy.json.JsonSlurper

class GitUtilities {

   static def listChangedFiles(steps, targetBranch) {
        def changedPackages = new HashSet<String>()

        targetBranch = targetBranch.trim()
        steps.echo "target branch=[${targetBranch}]"
        String diffFilesList = steps.sh(script: "git diff ${targetBranch} --name-only", returnStdout: true)
        List<String> files = Arrays.asList(diffFilesList.split("\\n"))
    }

    static def releaseVersion(steps, pushUrl, tagNewVersion) {
        def version = script.sh (script: "node -p -e \"require('./package.json').version\"", returnStdout: true)
        def name = script.sh (script: "node -p -e \"require('./package.json').name\"", returnStdout: true)
        env.RELEASE_VERSION = steps.input message: "Current version for ${name} is ${version}. User input required', ok: 'Release!",
                parameters: [choice(name: 'RELEASE_VERSION', choices: 'patch\nminor\nmajor', description: 'What is the release version?')]
        def noTagFlag = tagNewVersion ? '' : '--no-git-tag-version'
        steps.sh "yarn version {$noTagFlag} --new-version ${env.RELEASE_VERSION}"
        steps.sh "git push ${pushUrl}"
        version = script.sh (script: "node -p -e \"require('./package.json').version\"", returnStdout: true)
        echo "new version is ${version}"
        return version
    }
}