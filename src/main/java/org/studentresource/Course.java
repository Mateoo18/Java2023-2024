package org.studentresource;

public class Course implements StudentResource {
    private String id;
    private String name;
    public Course(String id,String name)
    {
        this.id=id;
        this.name=name;
    }
    public String getId() {
return id;
    }
    public void setId(String id){
        this.id=id;
    }

    @Override
    public String getName() {
        return name;
    }
    public void setName(String name){//change
        this.name=name;
    }

}