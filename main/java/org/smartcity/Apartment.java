package org.smartcity;

public class Apartment extends Building {
    int residents;
    public Apartment(String address, int floors, int residents) {
        super(address, floors);
        this.residents = residents;
    }
    @Override
    public void operate() {
        System.out.println("Apartment  at: "+getAddress()+" with a residents: "+residents);
    }
    public int getResidents() {
        return residents;
    }

    public void setResidents(int residents) {
        this.residents = residents;
    }


}
