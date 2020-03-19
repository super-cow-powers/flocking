/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometry;

/**
 *
 * @author david
 */
public class CartesianCoordinate {

    private double xPosition;
    private double yPosition;

    public CartesianCoordinate(double x, double y) {
        xPosition = x;
        yPosition = y;
    }
    
    public double getX(){
        return xPosition;
    }
    public double getY(){
        return yPosition;
    }
}
