package org.rmc33.lernaJenkins

import groovy.json.JsonSlurper

class GitUtilities {

   static def listChangedFiles(script, targetBranch) {
        def changedPackages = new HashSet<String>()

        targetBranch = targetBranch.trim()
        script.echo "target branch=[${targetBranch}]"
        String diffFilesList = script.sh(script: "git diff ${targetBranch} --name-only", returnStdout: true)
        List<String> files = Arrays.asList(diffFilesList.split("\\n"))
    }

    static def releaseVersion(script, pushUrl, tagNewVersion) {
        def version = script.sh (script: "node -p -e \"require('./package.json').version\"", returnStdout: true)
        def name = script.sh (script: "node -p -e \"require('./package.json').name\"", returnStdout: true)
        def RELEASE_VERSION = script.input message: "Current version for ${name} is ${version}. User input required', ok: 'Release!",
                parameters: [script.choice(name: 'RELEASE_VERSION', choices: 'patch\nminor\nmajor', description: 'What is the release version?')]
        def noTagFlag = tagNewVersion ? '' : '--no-git-tag-version'
        script.sh "yarn version {$noTagFlag} --new-version ${RELEASE_VERSION}"
        script.sh "git push ${pushUrl}"
        version = script.sh (script: "node -p -e \"require('./package.json').version\"", returnStdout: true)
        echo "new version is ${version}"
        return version
    }
}