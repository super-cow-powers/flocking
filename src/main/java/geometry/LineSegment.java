/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometry;
import java.awt.Color;
import java.lang.Math;
/**
 *
 * @author REDACTED
 */
public class LineSegment {
    
    private CartesianCoordinate startPoint;
    private CartesianCoordinate endPoint;
    private Color colour;
    
    public LineSegment(CartesianCoordinate start, CartesianCoordinate end){
        startPoint = start;
        endPoint = end; 
    }
    
    public CartesianCoordinate getStartPoint(){
        return startPoint;
    }
    
    public CartesianCoordinate getEndPoint(){
        return endPoint;
    }
    
    public double length(){
        double length;
        
        length = Math.sqrt(Math.abs(Math.pow(startPoint.getX()-endPoint.getX(),2))+Math.abs(Math.pow(startPoint.getY()-endPoint.getY(),2)));
        
        return length;
    }
    
    public Color get_colour(){
     return colour;   
    }
    public void set_rgba(int r, int g, int b, int alpha){ //Integer between 0 and 255 to specify the colour
        colour = new Color(r,g,b,alpha);
    }
    
    public void set_colour(Color Colour){ //Integer between 0 and 255 to specify the colour
        colour = Colour;
    }
    
}
