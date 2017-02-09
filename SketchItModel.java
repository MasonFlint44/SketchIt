/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sketchit;

import java.util.Stack;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

/**
 *
 * @author Mason
 */
public class SketchItModel {
    private double stroke;
    private Color color;
    private Brush brush;
    
    private Stack<Shape> undoList = new Stack<>();
    private Stack<Shape> redoList = new Stack<>();
    
    public void addToUndo(Shape shape) {
        undoList.push(shape);
    }
    
    public void clearUndoRedo() {
        undoList.clear();
        redoList.clear();
    }
    
    public Shape undo() {
        Shape shape = null;
        if(!undoList.empty()) {
            shape = undoList.pop();
            addToRedo(shape);  
        }
        return shape;
    }
    
    public void addToRedo (Shape shape) {
        redoList.push(shape);
    }
    
    public Shape redo() {
        Shape shape = null;
        if(!redoList.empty()) {
            shape = redoList.pop();
            addToUndo(shape);
        }
        return shape;
    }

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
    
    public Brush getBrush() {
        return brush;
    }
    
    public void setBrush(Brush brush) {
        this.brush = brush;
    }
}
