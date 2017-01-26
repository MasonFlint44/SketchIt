/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mbf437sketchit;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mason
 */
public class AboutController implements Initializable {
    private Stage stage;
    private Scene prevScene;
    private String aboutMe = "My name is Mason Flint.  I am from Stewartsville, MO.  I am passionate about coding and enjoy creating software in my free time.";
    private String aboutApp = "SketchIt is an application that lets you create and design.  I hope you enjoy using it as much as I enjoyed making it.";
    private String howTo = "There are five brushes.  The draw brush lets you draw a path on the screen.  The line brush draws a staight line.  The ellipse brush draws an ellipse.  The rectangle brush draws a rectangle.  The erase brush lets you erase previously drawn elements.  You can select a color for your brush with the color picker.  You can select a width for your brush using the stroke slider.  Under the file menu, you can open an image to edit or save your file.  Under the edit menu, you can undo or redo your actions, resize the canvas, or clear the canvas.";
    
    @FXML
    private ImageView imageView;
    @FXML
    private Text aboutMeText;
    @FXML
    private Text aboutAppText;
    @FXML
    private Text howToText;
    
    public void setStage(Stage stage, double width, double height) {
        this.stage = stage;
        
        //Maintains size of stage while switching scenes
        this.stage.setWidth(width);
        this.stage.setHeight(height);
    }
    
    public void setPreviousScene(Scene prevScene) {
        this.prevScene = prevScene;
    }
    
    @FXML
    private void onBackButtonAction() {
        stage.setScene(prevScene);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            imageView.setImage(new Image(getClass().getResource("masonUshmaChristmas.jpg").toURI().toString()));
        } catch(Exception e) {
            //Do nothing
        }
        aboutMeText.setText(aboutMe);
        aboutAppText.setText(aboutApp);
        howToText.setText(howTo);
    }    
}
