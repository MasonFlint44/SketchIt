/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mbf437sketchit;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 *
 * @author Mason
 */
public class EllipseBrush extends Brush {
    private Ellipse ellipse;
    private double centerX;
    private double centerY;
    
    @Override
    public Shape startBrush(double x, double y) {
        ellipse = new Ellipse();
        ellipse.setStroke(color);
        ellipse.setStrokeWidth(stroke);
        ellipse.setFill(Color.TRANSPARENT);
        
        centerX = x;
        centerY = y;
        ellipse.setCenterX(x);
        ellipse.setCenterY(y);
        
        //pane.getChildren().add(ellipse);
        return ellipse;
    }
    
    @Override
    public Shape brush(double x, double y) {
        ellipse.setRadiusX(Math.abs(x - centerX));
        ellipse.setRadiusY(Math.abs(y - centerY));
        return ellipse;
    }
    
    @Override
    public Shape stopBrush(double x, double y) {
        brush(x, y);
        return ellipse;
    }
}
