package org.rmc33.lernaJenkins


class Package implements Serializable {

    public String name;
    public String location;

    Package() {}

    Package(String name, String location) {
        this.name = name
        this.location = location
    }

}