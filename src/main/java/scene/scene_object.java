/**
 * This describes an interface for objects to be included directly in the scene.
 * 
 * For the benefit of the tape: A flock counts as a scene object, which then draws its' members contained therein.
 * A Flocking Bird is not a scene object, as it is drawn to the scene indirectly.
 * A possible Predator bird would use the scene object interface, since it would be a lone hunter drawn directly.
 */
package scene;

import drawing.Canvas;
import geometry.CartesianCoordinate;

/**
 *
 * @author REDACTED
 * Defines an interface for the objects being drawn to the scene
 * draw draw's the object - if debug is true extra information should be shown.
 * update update's the object's current state.
 * get_location returns the object's co-ordinates. Not defined for objects with multiple contained members (i.e flock).
 * get_size returns a double as the size of the object (i.e obstacle radius, flock number).
 * get class returns the contained class' name as a string.
 */
public interface scene_object {
    public void draw(Canvas canvas, boolean debug);
    public void update(Canvas canvas);  
    public CartesianCoordinate get_location();
    public double get_size();
    public String get_class();
}
