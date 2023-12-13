package org.smartcity;

public class Office extends Building {
    int employees;
    public Office(String address, int floors, int employees) {
        super(address, floors);
        this.employees = employees;
    }

    @Override
    public void operate() {
        System.out.println("Office at: "+getAddress()+" with a employees: "+employees);
    }


    public int getEmployees() {
        return employees;
    }

    public void setEmployees(int employees) {
        this.employees = employees;
    }
}
