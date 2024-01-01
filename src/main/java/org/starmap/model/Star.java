package org.starmap.model;

import org.starmap.view.NumberSizeException;

// Represents a star in the star map
public class Star {
     private String name;
     private double xPosition;
     private double yPosition;
     private double brightness;

    public Star(String name, double xPosition, double yPosition, double brightness) throws NumberSizeException {
       if(xPosition<10  || xPosition>1024) {
           throw new NumberSizeException("Invalid x Position");
       }
       if(yPosition<0 || yPosition>768){
           throw new NumberSizeException("Invalid y Position");
       }
       if(brightness<0 || brightness>7){
           throw new NumberSizeException("Invalid brightness");
       }
        this.name = name;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.brightness = brightness;
    }

    public String getName() {
        return name;
    }

    public double getXPosition() {
        return xPosition;
    }

    public double getYPosition() {
        return yPosition;
    }

    public double getBrightness() {
        return brightness;
    }

    public void setXPosition(double xPosition){
        this.xPosition=xPosition;
    }
    public void setYPosition(double yPosition){
        this.yPosition=yPosition;
    }
    public void setBrightness(double brightness)
    {
        this.brightness=brightness;
    }
    public void setName(String name){
        this.name=name;
    }
}
