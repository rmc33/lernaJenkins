package org.rmc33.lernaJenkins

public interface LernaPipeline {
    public List<Package> listChangedPackages(script)
    public void runBeforePackagesPipeline(script)
    public void runPackagePipeline(script)
    public void runAfterPackagesPipeline(script)
}