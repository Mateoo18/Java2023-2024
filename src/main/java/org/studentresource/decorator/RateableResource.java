package org.studentresource.decorator;

import org.studentresource.StudentResource;

public class RateableResource extends ResourceDecorator{
    public StudentResource decoratedResource;
    double rate;
    public RateableResource(StudentResource decoratedResource) {
        super(decoratedResource);
        this.decoratedResource=decoratedResource;
    }
    public StudentResource getDecoratedResource(){
        return decoratedResource;
    }

    public void setRating(double value) {
        rate=value;
    }

    public double getRating() {
        return rate;
    }

    @Override
    public String getId() {
        return decoratedResource.getId();
    }

    @Override
    public String getName() {
        return decoratedResource.getName();
    }
}
