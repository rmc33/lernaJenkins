package org.rmc33.lernaJenkins


class YarnUtilities {

    static def updateVersion(script, tagConfig) {
        def version = script.sh (script: "node -p -e \"require('./package.json').version\"", returnStdout: true)
        def name = script.sh (script: "node -p -e \"require('./package.json').name\"", returnStdout: true)
        def RELEASE_VERSION = script.input message: "Current version for ${name} is ${version}. Cut Release:",
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
        version = script.sh (script: "node -p -e \"require('./package.json').version\"", returnStdout: true)
        script.echo "new version is ${version}"
        return version
    }
}