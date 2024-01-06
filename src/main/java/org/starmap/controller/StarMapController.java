package org.starmap.controller;

import org.starmap.model.Constellation;
import org.starmap.model.Star;
import org.starmap.utils.DataLoader;
import org.starmap.view.NumberSizeException;

import java.util.List;
import java.util.Optional;

// Controller for managing the star map
public class StarMapController {
    private List<Star> stars;
    private List<Constellation> constellations;

    public StarMapController(String dataFilePath) throws NumberSizeException {
        this.stars = DataLoader.loadStars(dataFilePath);
        this.constellations = DataLoader.loadConstellations(dataFilePath, stars);
    }

    public List<Star> getStars() {
        return stars;
    }

    public void setStars(List<Star> stars) {
        this.stars = stars;
    }

    public List<Constellation> getConstellations() {
        return constellations;
    }

    public void setConstellations(List<Constellation> constellations) {
        this.constellations = constellations;
    }

    public Optional<Star> getStarByName(String name) {
        return stars.stream().filter(star -> star.getName().equalsIgnoreCase(name)).findFirst();
    }

    public Optional<Constellation> getConstellationByName(String name) {
        return constellations.stream().filter(constellation -> constellation.getName().equalsIgnoreCase(name)).findFirst();
    }


    public void addStar(Star star) {
        stars.add(star);
    }

    public void removeStar(String name) {
        Optional<Star> starToRemove = stars.stream()
                .filter(star -> star.getName().equalsIgnoreCase(name))
                .findFirst();

        starToRemove.ifPresent(star -> {
            stars.remove(star);
            constellations.forEach(constellation -> constellation.removeStar(star));
        });
    }

    public void addConstellation(Constellation constellation) {
        constellations.add(constellation);
    }

    public void removeConstellation(String name) {
        constellations.removeIf(constellation -> constellation.getName().equalsIgnoreCase(name));
    }

    public void addStartoConst(Star star,String constellationName) {
        stars.add(star);

        Optional<Constellation> optionalConstellation = getConstellationByName(constellationName);
        if (optionalConstellation.isPresent()) {
            Constellation constellation = optionalConstellation.get();
            constellation.addStar(star);
        }

    }
    public void setNewStarBrightness(String starName,double brightness){
        for (Star star:stars){
            if(star.getName().equalsIgnoreCase(starName)){
               star.setBrightness(brightness);
               break;
            }
        }
    }
    public void setNewStarName(String oldName,String newName){
        for (Star star:stars){
            if(star.getName().equalsIgnoreCase(oldName)){
                star.setName(newName);
                break;
            }
        }
    }
}
