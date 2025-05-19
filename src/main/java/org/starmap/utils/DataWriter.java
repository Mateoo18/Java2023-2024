package org.starmap.utils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.starmap.model.Constellation;
import org.starmap.model.Star;
import org.starmap.view.NumberSizeException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
public class DataWriter {


        public static void writeDataToFile(String filePath, List<Star> stars, List<Constellation> constellations) {
            JSONObject jsonData = new JSONObject();
            JSONArray starsJsonArray = new JSONArray();
            for (Star star : stars) {
                JSONObject starJson = new JSONObject();
                starJson.put("name", star.getName());
                starJson.put("xPosition", star.getXPosition());
                starJson.put("yPosition", star.getYPosition());
                starJson.put("brightness", star.getBrightness());
                starsJsonArray.put(starJson);
            }
            jsonData.put("stars", starsJsonArray);

            JSONArray constellationsJsonArray = new JSONArray();
            for (Constellation constellation : constellations) {
                JSONObject constellationJson = new JSONObject();
                constellationJson.put("name", constellation.getName());

                // Write stars in the constellation
                JSONArray constellationStarsJsonArray = new JSONArray();
                for (Star star : constellation.getStars()) {
                    constellationStarsJsonArray.put(star.getName());
                }
                constellationJson.put("stars", constellationStarsJsonArray);

                constellationsJsonArray.put(constellationJson);
            }
            jsonData.put("constellations", constellationsJsonArray);


            try (FileWriter fileWriter = new FileWriter(filePath)) {
                fileWriter.write(jsonData.toString(2));
            } catch (IOException e) {
        
                e.printStackTrace();
            }
        }
}
