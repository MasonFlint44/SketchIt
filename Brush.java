/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mbf437sketchit;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

/**
 *
 * @author Mason
 */
public abstract class Brush {
    protected double stroke;
    protected Color color;
    //protected Pane pane;
    
    public abstract Shape startBrush(double x, double y);
    public abstract Shape brush(double x, double y);
    public abstract Shape stopBrush(double x, double y);
    
    public double getStroke() {
        return stroke;
    }
    
    public void setStroke(double stroke) {
        this.stroke = stroke;
    }
    
    public Color getColor() {
        return color;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }
    
//    public Pane getPane() {
//        return pane;
//    }
//    
//    public void setPane(Pane pane) {
//        this.pane = pane;
//    }
}
