package org.rmc33.lernaJenkins


class YarnUtilities {

    static def inputToUpdateVersion(script, tagConfig) {
        def version = getVersion()
        def name = script.sh (script: "node -p -e \"require('./package.json').name\"", returnStdout: true)
        def RELEASE_VERSION = script.input message: "Current version for ${packageName} is ${version}.",
                parameters: [script.choice(name: 'RELEASE_VERSION', choices: 'patch\nminor\nmajor\nskip', description: 'What is the release version?')]

        if (RELEASE_VERSION == 'skip') return false

        def noTagFlag = ''

        if (tagConfig) {
            tagConfig.gitTagVersion ? '' : '--no-git-tag-version'
            if (tagConfig.versionTagPrefix) {
                script.sh "yarn config set version-tag-prefix '${tagConfig.versionTagPrefix}'"
            }
            if (tagConfig.versionGitMesage) {
                script.sh "yarn config set version-git-message '${tagConfig.versionGitMesage}'"
            }
        }

        script.sh "yarn version {$noTagFlag} --new-version ${RELEASE_VERSION}"
        script.sh "git push origin"
        version = getVersion()
        script.echo "new version is ${version}"
        return version
    }

    static def inputToCreateReleaseBranch(script, config, tagConfig) {
        def version = getVersion()
        def choice = script.input message: "Create release/${version} ?",
                parameters: [script.choice(name: 'RELEASE_VERSION', choices: 'yes\nno', description: 'make new release version?')]

        if (choice == 'yes') {
            def newVersion = inputToUpdateVersion(script, tagConfig)
            script.sh "git checkout -b release/${version}"
            script.sh "git push origin release/${version}"
            script.sh "git checkout ${config.branchName}"
            config.releaseVersion = newVersion
        }
    }

    static def getVersion(script) {
        return script.sh (script: "node -p -e \"require('./package.json').version\"", returnStdout: true)
    }
}