/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sketchit;

import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

/**
 *
 * @author Mason
 */
public class LineBrush extends Brush {
    Line line;
    
    @Override
    public Shape startBrush(double x, double y) {
        line = new Line();
        line.setStroke(color);
        line.setStrokeWidth(stroke);
        
        line.setStartX(x);
        line.setStartY(y);
        line.setEndX(x);
        line.setEndY(y);
        
        return line;
    }
    
    @Override
    public Shape brush(double x, double y) {
        line.setEndX(x);
        line.setEndY(y);
        return line;
    }
    
    @Override
    public Shape stopBrush(double x, double y) {
        brush(x, y);
        return line;
    }
}
