/*
 * This is the line class from the Labs.
 * I have added some colouring methods.
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
    public void set_colour(int r, int g, int b, int alpha){ //Integers between 0 and 255 to specify the colour
        colour = new Color(r,g,b,alpha);
    }
    
    public void set_colour(Color Colour){ //Overloaded, set colour using a Java Color
        colour = Colour;
    }
    
}
