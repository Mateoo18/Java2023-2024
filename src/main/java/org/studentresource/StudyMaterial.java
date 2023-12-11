package org.studentresource;

public class StudyMaterial implements StudentResource{
    private String id;
    private String name;
    public StudyMaterial(String id, String name)
    {
        this.id=id;
        this.name=name;
    }
    @Override
    public String getId() {
return id;
    }

    @Override
    public String getName() {
        return name;
    }
    public void setId(String id){
        this.id=id;
    };
    public void setName(String name){this.name=name;
    }
}
