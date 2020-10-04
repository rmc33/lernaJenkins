package org.rmc33.lernaJenkins

public interface LernaPipeline {
    public List<Package> listChangedPackages(script, config)
    public void runBeforePackagesPipeline(script, config)
    public void runPackagePipeline(script, Package p, config)
    public void runAfterPackagesPipeline(script)
}