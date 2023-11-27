package org.studentresource.decorator;

import org.studentresource.StudentResource;

// This class should allow adding comments to the resource
public class CommentableResource extends ResourceDecorator {

   StudentResource decoratedResource;
   String comment;
    public CommentableResource(StudentResource decoratedResource) {

        super(decoratedResource);
        this.decoratedResource = decoratedResource;
    }

    public StudentResource getDecoratedResource(){
        return decoratedResource;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    public void addComment(String comment) {
    this.comment=comment;
    }
    public String getComment(){
        return comment;
    }

    // Implement commenting features
}
