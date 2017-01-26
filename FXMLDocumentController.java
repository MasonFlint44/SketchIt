/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mbf437sketchit;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Pair;
import javax.imageio.ImageIO;

/**
 *
 * @author Mason
 */
public class FXMLDocumentController implements Initializable { 
    private SketchItModel sketchItModel = new SketchItModel();
    private Rectangle paneBoundary = new Rectangle();
    private ToggleGroup toggleBrushes = new ToggleGroup();
    
    private DrawBrush drawBrush = new DrawBrush();
    private LineBrush lineBrush = new LineBrush();
    private EllipseBrush ellipseBrush = new EllipseBrush();
    private RectangleBrush rectangleBrush = new RectangleBrush();
    private DrawBrush eraseBrush = new DrawBrush();
    
    private FileChooser fileChooser;
    
    @FXML
    private ToggleButton drawButton;
    @FXML
    private ToggleButton lineButton;
    @FXML
    private ToggleButton ellipseButton;
    @FXML
    private ToggleButton rectangleButton;
    @FXML
    private ToggleButton eraseButton;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private Slider strokeSlider;
    @FXML
    private Label sliderLabel;
    @FXML
    private Pane pane;
    @FXML
    private MenuItem undo;
    @FXML
    private MenuItem redo;
    
    @FXML
    private void onDrawButtonAction() {
        sketchItModel.setBrush(drawBrush);
    }
    @FXML
    private void onLineButtonAction() {
        sketchItModel.setBrush(lineBrush);
    }
    @FXML
    private void onEllipseButtonAction() {
        sketchItModel.setBrush(ellipseBrush);
    }
    @FXML
    private void onRectangleButtonAction() {
        sketchItModel.setBrush(rectangleBrush);
    }
    @FXML
    private void onEraseButtonAction() {
        sketchItModel.setBrush(eraseBrush);
    }
    @FXML
    private void onColorPickerAction() {
        sketchItModel.setColor(colorPicker.getValue());
    }
    @FXML
    private void onStrokeSliderDrag() {
        sliderLabel.setText(strokeSlider.getValue() + " px");
        
        sketchItModel.setStroke(strokeSlider.getValue());
    }
    @FXML
    private void onPaneMousePress(MouseEvent mouseEvent) {
        if(sketchItModel.getBrush() != eraseBrush) {
            sketchItModel.getBrush().setColor(sketchItModel.getColor());
        } else {
            sketchItModel.getBrush().setColor(Color.WHITE);
        }
        
        sketchItModel.getBrush().setStroke(sketchItModel.getStroke());
        Shape shape = sketchItModel.getBrush().startBrush(mouseEvent.getX(), mouseEvent.getY());
        pane.getChildren().add(shape);
    }
    @FXML
    private void onPaneMouseDrag(MouseEvent mouseEvent) {
        sketchItModel.getBrush().brush(mouseEvent.getX(), mouseEvent.getY());
    }
    @FXML
    private void onPaneMouseRelease(MouseEvent mouseEvent) {
        Shape shape = sketchItModel.getBrush().stopBrush(mouseEvent.getX(), mouseEvent.getY());
        sketchItModel.addToUndo(shape);
    }
    @FXML
    private void onUndoButtonAction() {
        Shape shape = sketchItModel.undo();
        if(shape != null) {
            pane.getChildren().remove(shape);
        }
    }
    @FXML
    private void onRedoButtonAction() {
        Shape shape = sketchItModel.redo();
        if(shape != null) {
            pane.getChildren().add(shape);
        }
    }
    @FXML
    private void onOpenButtonAction() {
        fileChooser.setTitle("Select an image");
        File file = fileChooser.showOpenDialog(pane.getScene().getWindow());

        if (file != null) {
            pane.getChildren().clear();
            sketchItModel.clearUndoRedo();
            
            try {
                Image image = new Image(file.toURI().toString());
                ImageView imageView = new ImageView(image);
                pane.setPrefSize(image.getWidth(), image.getHeight());
                pane.getChildren().add(imageView);
            } catch(Exception e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Load error");
                alert.setContentText("There was an error opening the file.");

                alert.showAndWait();
            }
        }
    }
    @FXML
    private void onSaveButtonAction() {
        WritableImage image = pane.snapshot(null, null);
        
        fileChooser.setTitle("Save image");
        File file = fileChooser.showSaveDialog(pane.getScene().getWindow());
        
        if(file != null) {
            String fileExt = getExtensionFromFileName(file.getName());
            
            //Get list of valid file extensions
            ArrayList<String> validExtensions = new ArrayList<>();
            for(String extension : fileChooser.getExtensionFilters().get(0).getExtensions()) {
                validExtensions.add(getExtensionFromFileName(extension));
            }
            
            //If file has no extension or is not a valid extension save as bitmap
            if(fileExt == null || validExtensions.contains(fileExt) == false) {
                fileExt = getExtensionFromFileName(fileChooser.getSelectedExtensionFilter().getExtensions().get(0));
                String path = file.getAbsolutePath() + "." + fileExt;
                file = new File(path);
            }
            
            try {
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
                
                //Create new BufferedImage without alpha channel
                //Prevents issues with saving bitmap and jpeg images
                BufferedImage bufferedImageNoAlpha = 
                        new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.OPAQUE); 
                Graphics2D graphics = bufferedImageNoAlpha.createGraphics();
                graphics.drawImage(bufferedImage, 0, 0, null);
                graphics.dispose();
                
                ImageIO.write(bufferedImageNoAlpha, fileExt, file);
                
            } catch(Exception e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Save error");
                alert.setContentText("There was an error saving the file.");

                alert.showAndWait();
            }
        }
    }
    @FXML
    private void onClearButtonAction() {
        pane.getChildren().clear();
        sketchItModel.clearUndoRedo();
    }
    @FXML
    private void onResizeButtonAction() {
        ResizeDialog<Pair<String, String>> resizeDialog = new ResizeDialog<>(pane.getWidth(), pane.getHeight());
        Optional<Pair<String, String>> newSize = resizeDialog.showAndWait();
        newSize.ifPresent(widthHeight -> {
            double width = Double.parseDouble(widthHeight.getKey());
            pane.setPrefWidth(width);
            double height = Double.parseDouble(widthHeight.getValue());
            pane.setPrefHeight(height);
        });
    }
    @FXML
    private void onAboutButtonAction() {
        Scene currScene = pane.getScene();
        Stage stage = (Stage)pane.getScene().getWindow();
        
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("About.fxml"));
            Parent about = fxmlLoader.load();
            
            AboutController aboutController = fxmlLoader.getController();
            aboutController.setStage(stage, stage.getWidth(), stage.getHeight());
            aboutController.setPreviousScene(currScene);
            
            Scene aboutScene = new Scene(about);
            stage.setScene(aboutScene);
            stage.show();
        } catch(Exception e) {
            //Do nothing
        }
    }
    
    private String getExtensionFromFileName(String fileName) {
        String[] fileNameArr = fileName.split("\\.", 2);
        if(fileNameArr.length > 1) {
            return fileNameArr[1];
        } else {
            return null;
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sketchItModel.setStroke(1);
        sketchItModel.setColor(Color.BLACK);
        sketchItModel.setBrush(drawBrush);
        
        colorPicker.setValue(Color.BLACK);
        
        drawButton.setToggleGroup(toggleBrushes);
        lineButton.setToggleGroup(toggleBrushes);
        ellipseButton.setToggleGroup(toggleBrushes);
        rectangleButton.setToggleGroup(toggleBrushes);
        eraseButton.setToggleGroup(toggleBrushes);

        pane.widthProperty().addListener( (observable, oldValue, newValue) -> {
            paneBoundary.setWidth((double)newValue);
            pane.setClip(paneBoundary);
        });
        
        pane.heightProperty().addListener( (observable, oldValue, newValue) -> {
            paneBoundary.setHeight((double)newValue);
            pane.setClip(paneBoundary);
        });
        
        fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Pictures"));
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Image Files (*.bmp, *.gif, *.jpeg, *.png)", "*.bmp", "*.BMP", "*.gif", "*.GIF", "*.jpg", "*.JPG", "*.jpeg", "*.JPEG", "*.png", "*.PNG"),
                new ExtensionFilter("Bitmap Image (*.bmp)", "*.bmp", "*.BMP"),
                new ExtensionFilter("GIF Image (*.gif)", "*.gif", "*.GIF"),
                new ExtensionFilter("JPEG Image (*.jpeg)", "*.jpeg", "*.JPEG", "*.jpg", "*.JPG"),
                new ExtensionFilter("PNG Image (*.png)", "*.png", "*.PNG"));
        
        undo.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        redo.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));
    }    
}
