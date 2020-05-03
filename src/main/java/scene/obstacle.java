/*
 * This is an obstacle. It is a scene_object
 * It's quite boring.
 */
package scene;

import drawing.Canvas;
import geometry.CartesianCoordinate;
import java.awt.Color;

/**
 *
 * @author REDACTED
 */
public final class obstacle implements scene_object {

    private final CartesianCoordinate centre;
    private final int radius;
    
    /*
     * Size is radius in px, complexity is number of edges to draw; more complex=more circular but slower
     */
    public obstacle(CartesianCoordinate centre_loc, int size, int complexity) {
        centre = centre_loc;
        radius = size;
    }
    
    @Override
    public CartesianCoordinate get_location() {
        return centre;
    }

    @Override
    public void draw(Canvas canvas, boolean debug) {
        canvas.draw_circle(centre, radius, 100, 0, Color.red); //Red is obstacle-ish, right?
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
