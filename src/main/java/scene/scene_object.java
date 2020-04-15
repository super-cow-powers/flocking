/*
 *This describes an interface for a 
 */
package scene;

import drawing.Canvas;
import geometry.CartesianCoordinate;

/**
 *
 * @author david
 * defines an interface for the objects being drawn to the scene
 * draw will draw the objects,
 * update will update their current state.
 */
public interface scene_object {
    public void draw(Canvas canvas);
    public void update(Canvas canvas);
    public CartesianCoordinate get_location();
    public double get_size();
}
