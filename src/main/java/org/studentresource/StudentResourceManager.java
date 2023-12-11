package org.studentresource;

import java.util.ArrayList;
import java.util.List;

// This class should manage different student resources
public class StudentResourceManager<T extends StudentResource> {
    public List<T> resources;
    public StudentResourceManager(){
        this.resources=new ArrayList<>();
    }
    public T getResource(String id) {
        for(T object:resources){
            if(object.getId().equals(id)){
                return object;
            }
        }
        return null;
    }
    public void  addResource(T obj)
    {
        resources.add(obj);
    }
    public int remove(String id)
    {
        for(T object:resources){
            if(object.getId().equals(id)){
                resources.remove(object);
                return 1;
            }
        }
        return -1;
    }

}
