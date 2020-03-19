/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import drawing.Canvas;
import geometry.CartesianCoordinate;
import geometry.LineSegment;

/**
 *
 * @author david
 */
public final class obstacle implements scene_object{

    private CartesianCoordinate centre;
    private int radius;
    private LineSegment segments[];


    public obstacle(CartesianCoordinate centre_loc, int size, int complexity) {
        centre = centre_loc;
        radius = size;
        segments = new LineSegment[complexity];
        float angle=0;
        int i=0;
        CartesianCoordinate pointA, pointB;
        while (angle<2*Math.PI){
                    pointA=new CartesianCoordinate((centre.getX() + (Math.sin(angle))*radius),(centre.getY()+ (Math.cos(angle))*radius));
                    pointB=new CartesianCoordinate((centre.getX() + (Math.sin(angle+((2*Math.PI)/complexity)))*radius),(centre.getY()+ (Math.cos(angle+((2*Math.PI)/complexity)))*radius));
                    angle=(float) (angle+((2*Math.PI)/complexity));
                    segments[i]=new LineSegment(pointA, pointB);
                    segments[i].set_rgba(255, 100, 0, 255);
                    i++;
                }
    }

    public int get_radius() {
        return radius;
    }

    public CartesianCoordinate get_location() {
        return centre;
    }
    

    @Override
    public void draw(Canvas canvas) {
        canvas.drawLineSegments(segments);
    }

    @Override
    public void update() {
        //Not applicable to this (static) object
    }
}
