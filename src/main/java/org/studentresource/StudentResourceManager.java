package org.studentresource;

import java.util.ArrayList;
import java.util.List;

// This class should manage different student resources
public class StudentResourceManager<T extends StudentResource> {
    public List<T> resources;
    public StudentResourceManager(){
        this.resources=new ArrayList<>();
    }
    public T getResource(String cs101) {
        for(T obiekt:resources){
            if(obiekt.getId().equals(cs101)){
                return obiekt;
            }
        }
        return null;
    }
    public void  addResource(T obj)
    {
        resources.add(obj);
    }
    public int remove(String idee)
    {
        for(T obiekt:resources){
            if(obiekt.getId().equals(idee)){
                resources.remove(obiekt);
                return 1;
            }
        }
        return -1;
    }

    // Implement methods to manage resources (add, remove, find, etc.)

}
