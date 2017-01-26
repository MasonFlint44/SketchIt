/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mbf437sketchit;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

/**
 *
 * @author Mason
 */
public class ResizeDialog<R> extends Dialog {
    private GridPane grid = new GridPane();
    private TextField width = new TextField();
    private TextField height = new TextField();
    private ButtonType applyButton = ButtonType.APPLY;
    private ButtonType cancelButton = ButtonType.CANCEL;
    
    public ResizeDialog(Double currentWidth, Double currentHeight) {
        setTitle("Resize");
        setHeaderText("Resize the drawing pane");
        getDialogPane().getButtonTypes().addAll(applyButton, cancelButton);
        
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        width.setPromptText(currentWidth.toString());
        height.setPromptText(currentHeight.toString());
        
        grid.add(new Label("Width:"), 0, 0);
        grid.add(width, 1, 0);
        grid.add(new Label("Height:"), 0, 1);
        grid.add(height, 1, 1);
        
        width.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                double widthNewValue = Double.parseDouble(newValue);
                if(widthNewValue < 1) {
                    width.setText("1");
                } else if(widthNewValue > 10000) {
                    width.setText("10000");
                }
            } catch(Exception e) {
                width.setText("");
            }
        });
        
        height.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                double heightNewValue = Double.parseDouble(newValue);
                if(heightNewValue < 1) {
                    height.setText("1");
                } else if(heightNewValue > 10000) {
                    height.setText("10000");
                }
            } catch(Exception e) {
                height.setText("");
            }
        });
        
        getDialogPane().setContent(grid);
        
        setResultConverter(dialogButton -> {
            if (dialogButton == applyButton) {
                return new Pair<>(width.getText(), height.getText());
            }
            return null;
        });
    }
}
