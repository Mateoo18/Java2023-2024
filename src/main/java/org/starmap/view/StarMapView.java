package org.starmap.view;

import javafx.animation.PauseTransition;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.util.Pair;
import org.starmap.controller.StarMapController;
import org.starmap.model.Constellation;
import org.starmap.model.Star;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Optional;

import javafx.scene.layout.GridPane;
import org.starmap.utils.DataWriter;

import javax.swing.*;

public class StarMapView extends Canvas {
    private final StarMapController controller;
    private PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
    private Star currentHoveredStar = null;
    private Map<String, Color> constellationColors = new HashMap<>();

    private MenuBar menuBar;
    private boolean axes;
    private boolean isMouseDragging = false;


    public StarMapView(StarMapController controller) {
        this.controller = controller;
        this.setWidth(1024);
        this.setHeight(768);

        drawMap();
        addMenu();
        initializeConstellationColors();
        addMouseMotionListener();
    }

    private void initializeConstellationColors() {
        List<Constellation> constellations = controller.getConstellations();
        for (Constellation constellation : constellations) {
            int hash = constellation.getName().hashCode();
            Random rand = new Random(hash);
            Color color = new Color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble(), 1);
            constellationColors.put(constellation.getName(), color);
        }
    }

    public void drawMap() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, getWidth(), getHeight());
        if(axes)showAxisXY();
        drawStars();
        drawConstellations();
    }

    private void drawStars() {
        GraphicsContext gc = getGraphicsContext2D();
        List<Star> stars = controller.getStars();
        for (Star star : stars) {

            double brightnessScale = star.getBrightness() / 2.0; // Scale brightness
            double starSize = 2 + (5 - brightnessScale); // Calculate star size
            Color starColor = Color.hsb(60, 0.5, 1 - 0.2 * brightnessScale); // Color based on brightness
            drawStar(gc, star.getXPosition(), star.getYPosition(), starSize, starColor);
        }
    }

    private void drawStar(GraphicsContext gc, double x, double y, double size, Color color) {
        double[] xPoints = new double[10];
        double[] yPoints = new double[10];
        for (int i = 0; i < 10; i++) {
            double angle = Math.PI / 5 * i;
            double radius = i % 2 == 0 ? size : size / 2;
            xPoints[i] = x + radius * Math.sin(angle);
            yPoints[i] = y - radius * Math.cos(angle);
        }
        gc.setStroke(color);
        gc.strokePolyline(xPoints, yPoints, 10);
    }

    private void drawConstellations() {
        GraphicsContext gc = getGraphicsContext2D();
        List<Constellation> constellations = controller.getConstellations();

        for (Constellation constellation : constellations) {
            Color lineColor = constellationColors.getOrDefault(constellation.getName(), Color.BLUE);
            gc.setStroke(lineColor);
            gc.setLineWidth(1);
            gc.setFill(lineColor);
            gc.setFont(new Font("Arial", 14));
            List<Star> starsInConstellation = constellation.getStars();
            for (int i = 0; i < starsInConstellation.size() - 1; i++) {
                Star start = starsInConstellation.get(i);
                Star end = starsInConstellation.get(i + 1);
                gc.strokeLine(start.getXPosition(), start.getYPosition(), end.getXPosition(), end.getYPosition());
            }

            if (!starsInConstellation.isEmpty()) {
                Star firstStar = starsInConstellation.get(0);
                gc.fillText(constellation.getName(), firstStar.getXPosition(), firstStar.getYPosition() - 15);
            }
        }
    }


    private void addMouseMotionListener() {
        this.setOnMousePressed(event -> isMouseDragging = true);

        this.setOnMouseReleased(event -> {
            isMouseDragging = false;
            DataWriter.writeDataToFile("src/main/resources/stars.json", controller.getStars(), controller.getConstellations());
        });

        this.setOnMouseMoved(event -> {
            double mouseX = event.getX();
            double mouseY = event.getY();
            Star foundStar = null;
            List<Star> stars = controller.getStars();
            for (Star star : stars) {
                if (Math.abs(mouseX - star.getXPosition()) < 10 && Math.abs(mouseY - star.getYPosition()) < 10) {
                    foundStar = star;
                    break;
                }
            }

            if (foundStar != null && foundStar != currentHoveredStar) {
                currentHoveredStar = foundStar;
                pause.stop();
                drawStarName(foundStar);
            } else if (foundStar == null && currentHoveredStar != null) {
                pause.setOnFinished(e -> {
                    hideStarName();
                    currentHoveredStar = null;
                });
                pause.playFromStart();
            }
        });

       this.setOnMouseDragged(event -> {
            double mouseX = event.getX();
            double mouseY = event.getY();
            Star foundStar = null;
            List<Star> stars = controller.getStars();
            for (Star star : stars) {
                if (Math.abs(mouseX - star.getXPosition()) < 10 && Math.abs(mouseY - star.getYPosition()) < 10) {
                    foundStar = star;
                    break;
                }
            }

            if (foundStar != null) {
                foundStar.setXPosition(event.getX());
                foundStar.setYPosition(event.getY());
            }

            drawMap();
           pause.setOnFinished(e -> {
               DataWriter.writeDataToFile("src/main/resources/stars.json", controller.getStars(), controller.getConstellations());
           });
           // DataWriter.writeDataToFile("src/main/resources/stars.json", controller.getStars(), controller.getConstellations());

       });
       pause.playFromStart();

    }

    private void drawStarName(Star star) {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillText(star.getName(), star.getXPosition() + 10, star.getYPosition() - 10);
    }

    private void hideStarName() {
        if (currentHoveredStar != null) {
            pause.setOnFinished(e -> {
                clearCanvas();
                drawMap();
            });
            pause.playFromStart();
        }
    }

    private void clearCanvas() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
    }
    private void addMenu(){
        Menu fileMenu =new Menu("View Stars");
        MenuItem addItem=new MenuItem("Add Star");
        addItem.setOnAction(event -> showAddStarDialog());
        MenuItem deleteItem=new MenuItem("Remove Star");
        deleteItem.setOnAction(event -> showRemoveStarDialog());
        MenuItem setNewBrightness=new MenuItem("Brightness");
        setNewBrightness.setOnAction(event->showChangeBrightness());
        MenuItem changeStarName= new MenuItem("Change Star name");
        changeStarName.setOnAction(event->changeNameofStar());
        fileMenu.getItems().addAll(addItem,deleteItem,setNewBrightness,changeStarName);

        Menu viewConstellationsItem = new Menu("View Constellations");
        MenuItem addStarConstellation=new MenuItem("Add Star to Constellation");
        addStarConstellation.setOnAction(event->addStarCons());
        MenuItem changeNameConstellation=new MenuItem("Change name of Constellation");
        changeNameConstellation.setOnAction(event->changeNameCons());

        viewConstellationsItem.getItems().addAll(addStarConstellation,changeNameConstellation);

        Menu Axis=new Menu("Axis");
        MenuItem showAxis=new MenuItem("Show Axis");
        showAxis.setOnAction(event->showAxisXY());

        MenuItem hideAxis=new MenuItem("Hide Axis");
        hideAxis.setOnAction(event->hideAxisXY());
        Axis.getItems().addAll(showAxis,hideAxis);
        menuBar = new MenuBar(fileMenu,viewConstellationsItem,Axis);
        menuBar.setTranslateX(0);
        menuBar.setTranslateY(0);
        menuBar.setPrefWidth(1024);

    }
    public MenuBar getMenuBar(){
        return menuBar;
    }

    private void showAddStarDialog() {
        Dialog<Star> dialog = new Dialog<>();
        dialog.setTitle("Add Star");
        dialog.setHeaderText("Enter star details:");


        TextField nameField = new TextField();
        TextField xPositionField = new TextField();
        TextField yPositionField = new TextField();
        TextField brightnessField = new TextField();

        Label nameLabel = new Label("Name:");
        Label xPositionLabel = new Label("X Position:");
        Label yPositionLabel = new Label("Y Position:");
        Label brightnessLabel = new Label("Brightness:");

        GridPane grid = new GridPane();
        grid.add(nameLabel, 1, 1);
        grid.add(nameField, 2, 1);
        grid.add(xPositionLabel, 1, 2);
        grid.add(xPositionField, 2, 2);
        grid.add(yPositionLabel, 1, 3);
        grid.add(yPositionField, 2, 3);
        grid.add(brightnessLabel, 1, 4);
        grid.add(brightnessField, 2, 4);

        dialog.getDialogPane().setContent(grid);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    String name = nameField.getText();
                    double xPosition = Double.parseDouble(xPositionField.getText());
                    double yPosition = Double.parseDouble(yPositionField.getText());
                    double brightness = Double.parseDouble(brightnessField.getText());

                    return new Star(name, xPosition, yPosition, brightness);
                } catch (NumberFormatException e) {
                    return null;
                }
                catch(NumberSizeException e){
                    System.out.println("Error! " + e.getMessage());
                }
            }
            return null;
        });

        Optional<Star> result = dialog.showAndWait();

        result.ifPresent(star -> {
            controller.addStar(star);
            pause.playFromStart();
        });
        DataWriter.writeDataToFile("src/main/resources/stars.json",controller.getStars(),controller.getConstellations());
    }
    private void showRemoveStarDialog() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Remove Star");
        dialog.setHeaderText("Enter star name to remove:");

        TextField nameField = new TextField();
        Label nameLabel = new Label("Star Name:");
        GridPane grid = new GridPane();
        grid.add(nameLabel, 1, 1);
        grid.add(nameField, 2, 1);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                return nameField.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(starName -> {
            controller.removeStar(starName);
            pause.playFromStart();
        });
        DataWriter.writeDataToFile("src/main/resources/stars.json",controller.getStars(),controller.getConstellations());
    }
    public void addStarCons(){
        Dialog<Star> dialog = new Dialog<>();
        dialog.setTitle("Add Star");
        dialog.setHeaderText("Enter star details:");

        TextField nameField = new TextField();
        TextField xPositionField = new TextField();
        TextField yPositionField = new TextField();
        TextField brightnessField = new TextField();
        TextField constellationField =new TextField();

        Label nameLabel = new Label("Name:");
        Label xPositionLabel = new Label("X Position:");
        Label yPositionLabel = new Label("Y Position:");
        Label brightnessLabel = new Label("Brightness:");
        Label constellationLabel = new Label("Constellation:");

        GridPane grid = new GridPane();
        grid.add(nameLabel, 1, 1);
        grid.add(nameField, 2, 1);
        grid.add(xPositionLabel, 1, 2);
        grid.add(xPositionField, 2, 2);
        grid.add(yPositionLabel, 1, 3);
        grid.add(yPositionField, 2, 3);
        grid.add(brightnessLabel, 1, 4);
        grid.add(brightnessField, 2, 4);
        grid.add(constellationLabel,1,5);
        grid.add(constellationField,2,5);

        dialog.getDialogPane().setContent(grid);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    String name = nameField.getText();
                    double xPosition = Double.parseDouble(xPositionField.getText());
                    double yPosition = Double.parseDouble(yPositionField.getText());
                    double brightness = Double.parseDouble(brightnessField.getText());
                    String constellation =constellationField.getText();
                    return new Star(name, xPosition, yPosition, brightness);
                } catch (NumberFormatException e) {
                    return null;
                }
                catch(NumberSizeException e){
                    System.out.println("Error! " + e.getMessage());
                }
            }
            return null;
        });

        Optional<Star> result = dialog.showAndWait();

        result.ifPresent(star -> {
            controller.addStartoConst(star,constellationField.getText());
            pause.playFromStart();
        });
        DataWriter.writeDataToFile("src/main/resources/stars.json",controller.getStars(),controller.getConstellations());

    }
    public void showChangeBrightness() {
        Dialog<Pair<String, Double>> dialog = new Dialog<>();
        dialog.setTitle("Change Brightness");
        dialog.setHeaderText("Enter star name and new brightness:");

        TextField nameField = new TextField();
        TextField brightnessField = new TextField();

        Label nameLabel = new Label("Star Name:");
        Label brightnessLabel = new Label("New Brightness:");

        GridPane grid = new GridPane();
        grid.add(nameLabel, 1, 1);
        grid.add(nameField, 2, 1);
        grid.add(brightnessLabel, 1, 2);
        grid.add(brightnessField, 2, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    String name = nameField.getText();
                    double newBrightness = Double.parseDouble(brightnessField.getText());

                    return new Pair<>(name, newBrightness);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
            return null;
        });
        Optional<Pair<String, Double>> result = dialog.showAndWait();

        result.ifPresent(pair -> {
            String starName = pair.getKey();
            double newBrightness = pair.getValue();
            controller.setNewStarBrightness(starName,newBrightness);
            pause.playFromStart();
        });
        DataWriter.writeDataToFile("src/main/resources/stars.json",controller.getStars(),controller.getConstellations());
    }
    public void changeNameCons(){
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Change Constellation Name");
        dialog.setHeaderText("Enter current and new constellation names:");

        TextField currentNameField = new TextField();
        TextField newNameField = new TextField();

        Label currentNameLabel = new Label("Current Name:");
        Label newNameLabel = new Label("New Name:");

        GridPane grid = new GridPane();
        grid.add(currentNameLabel, 1, 1);
        grid.add(currentNameField, 2, 1);
        grid.add(newNameLabel, 1, 2);
        grid.add(newNameField, 2, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                return new Pair<>(currentNameField.getText(), newNameField.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(pair -> {
            String currentName = pair.getKey();
            String newName = pair.getValue();

            List<Constellation> constellations = controller.getConstellations();
            for (Constellation constellation : constellations) {
                if (constellation.getName().equals(currentName)) {
                    constellation.setName(newName);
                    break;
                }
            }

            clearCanvas();
            drawMap();
        });
        DataWriter.writeDataToFile("src/main/resources/stars.json",controller.getStars(),controller.getConstellations());
    }
    public void changeNameofStar(){
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Change Star Name");
        dialog.setHeaderText("Enter current and new Star names:");

        TextField currentNameField = new TextField();
        TextField newNameField = new TextField();

        Label currentNameLabel = new Label("Current Name:");
        Label newNameLabel = new Label("New Name:");

        GridPane grid = new GridPane();
        grid.add(currentNameLabel, 1, 1);
        grid.add(currentNameField, 2, 1);
        grid.add(newNameLabel, 1, 2);
        grid.add(newNameField, 2, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                return new Pair<>(currentNameField.getText(), newNameField.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(pair -> {
            String currentName = pair.getKey();
            String newName = pair.getValue();
            controller.setNewStarName(currentName,newName);
            clearCanvas();
            drawMap();
        });
        DataWriter.writeDataToFile("src/main/resources/stars.json",controller.getStars(),controller.getConstellations());
    }
    public void showAxisXY(){

            GraphicsContext gc = getGraphicsContext2D();
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(0.5);
            axes = true;
            gc.strokeLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
            gc.strokeLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
pause.playFromStart();
    }
    public void hideAxisXY(){
        axes=false;
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
        clearCanvas();
        drawMap();

    }

}
