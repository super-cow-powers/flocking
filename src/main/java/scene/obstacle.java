/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import drawing.Canvas;
import geometry.CartesianCoordinate;
import geometry.LineSegment;
import java.awt.Color;

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
            }

    public int get_radius() {
        return radius;
    }

    public CartesianCoordinate get_location() {
        return centre;
    }
    

    @Override
    public void draw(Canvas canvas) {
        canvas.draw_circle(centre, radius, 100, Color.red);
    }

    @Override
    public void update() {
        //Not applicable to this (static) object
    }
}
