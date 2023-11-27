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

    public void setRating(double v) {
        rate=v;
    }

    public double getRating() {
        return rate;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
