/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sketchit;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 *
 * @author Mason
 */
public class RectangleBrush extends Brush {
    private Rectangle rect;
    private double originX;
    private double originY;
    
    @Override
    public Shape startBrush(double x, double y) {
        rect = new Rectangle();
        rect.setStroke(color);
        rect.setStrokeWidth(stroke);
        rect.setFill(Color.TRANSPARENT);
        
        originX = x;
        originY = y;
        rect.setX(x);
        rect.setY(y);
        
        return rect;
    }
    
    @Override
    public Shape brush(double x, double y) {
        if(x < originX) {
            rect.setX(x);
        }
        rect.setWidth(Math.abs(x - originX));
        
        if(y < originY) {
            rect.setY(y);
        }
        rect.setHeight(Math.abs(y - originY));
        return rect;
    }
    
    @Override
    public Shape stopBrush(double x, double y) {
        brush(x, y);
        return rect;
    }
}
