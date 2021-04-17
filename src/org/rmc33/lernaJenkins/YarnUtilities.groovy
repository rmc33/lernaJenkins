package org.rmc33.lernaJenkins

class YarnUtilities {

    static def inputToCreateReleaseBranch(script, config, tagConfig) {
        def version = getVersion()
        def choice = script.input message: "Create release/${version} ?",
                parameters: [script.choice(name: 'RELEASE_VERSION', choices: 'yes\nno', description: 'make new release version?')]

        if (choice == 'yes') {
            script.sh "git checkout -b release/${version}"
            script.sh "git push origin release/${version}"
            script.sh "git checkout ${config.branchName}"
            return true
        }
        return false
    }

    static def getVersion(script) {
        return script.sh (script: "node -p -e \"require('./package.json').version\"", returnStdout: true)
    }
}