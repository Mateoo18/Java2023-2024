package org.smartcity;

public abstract class Building {
    protected String address;
    protected int floors;

    public Building(String address, int floors) {
        this.address = address;
        this.floors = floors;
    }

    public abstract void operate();

    public String getAddress() {
        return address;
    }

    public int getFloors() {
        return floors;
    }
}