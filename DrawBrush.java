/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mbf437sketchit;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;

/**
 *
 * @author Mason
 */
public class DrawBrush extends Brush {
    private Path path;
    
    @Override
    public Shape startBrush(double x, double y) {
        path = new Path();
        path.setStroke(color);
        path.setStrokeWidth(stroke);
        
        MoveTo moveTo = new MoveTo();
        moveTo.setX(x);
        moveTo.setY(y);
        
        path.getElements().add(moveTo);
        //pane.getChildren().add(path);
        return path;
    }
    
    @Override
    public Shape brush(double x, double y) {
        LineTo lineTo = new LineTo();
        lineTo.setX(x);
        lineTo.setY(y);
        
        MoveTo moveTo = new MoveTo();
        moveTo.setX(x);
        moveTo.setY(y);
                
        path.getElements().addAll(lineTo, moveTo);
        return path;
    }
    
    @Override
    public Shape stopBrush(double x, double y) {
        ClosePath closePath = new ClosePath();
        path.getElements().add(closePath);
        return path;
    }
}
