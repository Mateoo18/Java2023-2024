package org.studentresource.decorator;

import org.studentresource.StudentResource;

public class CommentableResource extends ResourceDecorator {

   StudentResource decoratedResource;
   String comment;
    public CommentableResource(StudentResource decoratedResource) {

        super(decoratedResource);
    }

    public StudentResource getDecoratedResource(){
        return decoratedResource;
    }

    @Override
    public String getId() {
        return decoratedResource.getId();
    }

    @Override
    public String getName() {
        return  decoratedResource.getName();
    }
    public void addComment(String comment) {
    this.comment=comment;
    }


    public String getComment(){
        return comment;
    }
}
