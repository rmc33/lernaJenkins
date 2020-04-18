package org.rmc33.lernaJenkins

interface BranchPipeline {
    void runPackagePipeline(startPackagePipeline script, String packageName)
    void runAfterPackagesPipeline(startPackagePipeline script)
}