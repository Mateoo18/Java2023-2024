package org.starmap.model;

import java.util.List;

// Represents a constellation made up of stars
public class Constellation {
     private String name;
    final private List<Star> stars;

    public Constellation(String name, List<Star> stars) {
        this.name = name;
        this.stars = stars;
    }

    public String getName() {
        return name;
    }

    public List<Star> getStars() {
        return stars;
    }
    public void removeStar(Star star) {
        stars.remove(star);
    }
    public void addStar(Star star){
        stars.add(star);
    }
    public void setName(String name){
        this.name=name;

    }
}
