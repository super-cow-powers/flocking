/*
 * This contains the base animal class.
 * All other animals should extend this
 */
package animals;

import geometry.CartesianCoordinate;
import java.awt.image.BufferedImage;
import java.util.List;
import scene.obstacle;
import scene.scene_object;

/**
 *
 * @author REDACTED
 */
public class animal {

    double speed;/* In px/s */
    double target_angle; /* in rads */
    double view_radius; /* In px */
    double direction_angle;
    BufferedImage image;
    CartesianCoordinate position;
    CartesianCoordinate local_centre;
    int bouncing;
    int seeking;

    //String type;
    public animal() {
    }

    /*
    Returns the distance between current bird and a coordinate
     */
    public double get_distance(CartesianCoordinate point_to) {
        double length;
        length = CartesianCoordinate.dist_between(position, point_to);
        return length;
    }

    public CartesianCoordinate Get_Direction_Position() { /* returns the position that the bird will be in if the direction (actual) angle is followed */
        double distance = speed / 60; /* The simulation speed has a multiplier of 1 at 60 updates/second - so speed*1 (px/s) is dist/(1/60) */
        /* Adding 0.2 here just keeps the Boid moving */
        return new CartesianCoordinate(position.getX() + (Math.cos((direction_angle)) * distance) + 0.2, position.getY() + (Math.sin((direction_angle)) * distance));
    }

    public CartesianCoordinate Get_Target_Position() { /* returns the position that the bird will be in if the target angle is followed */
        double distance = speed / 60; 
        /* Adding 0.2 here just keeps the Boid moving */
        return new CartesianCoordinate(position.getX() + (Math.cos((target_angle)) * distance) + 0.2, position.getY() + (Math.sin((target_angle)) * distance));
    }

    public double get_target_angle() {
        return target_angle;
    }
    
    public double get_speed() {
        return speed;
    }

    public void set_speed(double new_speed) { /** sets the bird's speed. Kind of redundant since I'm setting a random speed in the constructor
                                                * but I'm going to keep it in-case I want to over-ride the random speed from the UI or something.
                                                * But it's unlikely.
                                                */
        speed = Math.abs(new_speed);
    }
    
    public void set_viewRadius(int new_radius){
        view_radius = new_radius;
    }

    protected void set_target_angle(double new_angle) { /* Set the target angle, and account for the discontinuity and range */
        if (new_angle < -Math.PI) {
            new_angle += Math.PI * 2;
        } else if (new_angle > Math.PI) {
            new_angle -= Math.PI * 2;
        }
        target_angle = new_angle;
    }

    public double get_direction_angle() {
        return direction_angle;
    }

    protected void set_direction_angle(double new_angle) { /* Set the direction angle, and account for the discontinuity and range.   */
        if (new_angle < -Math.PI) {                        /* Only the object itself should be allowed to decide its actual direction.*/
            new_angle += Math.PI * 2;                      /* Whereas I want to stay open to suggestions as for the target angle      */
        } else if (new_angle > Math.PI) {
            new_angle -= Math.PI * 2;
        }
        direction_angle = new_angle;
    }

    protected void set_position(CartesianCoordinate new_position) { /* Only the object itself should be allowed to decide its position outside the constructor.*/
        position = new_position;
    }

    public CartesianCoordinate get_position() { /* Though the location doesn't have to be kept hidden */
        return position;
    }

    public CartesianCoordinate get_local_COM() { /* Same with the object's Local Centre Of Mass - */
        return local_centre;                     /* If I don't know where it is, I can't draw it  */
    }

    protected void set_local_COM(CartesianCoordinate COM) { /* But I can't have any old riffraff setting the position */
        local_centre = COM;
    }

    public BufferedImage get_image() { /* I need to know what it looks like, don't I? (well, the canvas does) */
        return image;
    }

    protected boolean bounce(List<obstacle> obstacles) { /* This provides a standard bounce if the user does not wish to override */
        if (bouncing == 0) {
            for (scene_object obstacle : obstacles) {
                if (this.get_distance(obstacle.get_location()) <= obstacle.get_size() + 10) {
                    set_target_angle(target_angle + Math.PI);
                    bouncing = 30;
                }
            }
        }

        if (bouncing != 0) {
            for (scene_object obstacle : obstacles) {
                if (this.get_distance(obstacle.get_location()) < obstacle.get_size()) {
                    this.set_position(new CartesianCoordinate(10, 10)); /* This shouldn't be triggered, but sometimes a bird will unfortunately get into the obstacle */
                    bouncing = 0; /* and it's very annoying */
                }
            }
            bouncing--;
            set_direction_angle(target_angle);
            set_position(Get_Direction_Position());
            return true;
        } else {
            return false;
        }
    }

    protected void wrap(double canvas_X, double canvas_Y) {
        //Code to wrap animal to other side of window
        if (position.getX() > canvas_X) {
            set_position(new CartesianCoordinate(10, position.getY()));
        } else if (position.getX() < 0) {
            set_position(new CartesianCoordinate(canvas_X - 10, position.getY()));
        }
        if (position.getY() > canvas_Y) {
            set_position(new CartesianCoordinate(position.getX(), 10));
        } else if (position.getY() < 0) {
            set_position(new CartesianCoordinate(position.getX(), canvas_Y - 10));
        }
    }

}
