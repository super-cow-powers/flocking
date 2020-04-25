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
 * @author REDACTED
 */
public final class obstacle implements scene_object {

    private final CartesianCoordinate centre;
    private final int radius;
    private LineSegment segments[];

    public obstacle(CartesianCoordinate centre_loc, int size, int complexity) {
        centre = centre_loc;
        radius = size;
    }

    public int get_radius() {
        return radius;
    }
    
    @Override
    public CartesianCoordinate get_location() {
        return centre;
    }

    @Override
    public void draw(Canvas canvas, boolean debug) {
        canvas.draw_circle(centre, radius, 100, 0, Color.red);
    }

    @Override
    public void update(Canvas canvas) {
        //Not applicable to this (static) object
    }

    @Override
    public double get_size() {
        return radius;
    }

    @Override
    public String get_class() {
        return this.getClass().getName();
    }
}
