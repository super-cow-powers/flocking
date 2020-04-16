/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animals;

import geometry.CartesianCoordinate;
import geometry.LineSegment;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.ImageIcon;
import scene.obstacle;
import scene.scene_object;

/**
 *
 * @author david
 */
public class animal {

    double speed;/* In px/s */
    double target_angle;
    /* in rads */
    double view_radius;
    /* In px */
    double angular_velocity;
    double direction_angle;
    BufferedImage image;
    CartesianCoordinate position;
    String name;
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
        length = position.distance_to(point_to);
        return length;
    }

    public CartesianCoordinate Get_Direction_Position() {
        double distance = speed / 60;
        return new CartesianCoordinate(position.getX() + (Math.cos((direction_angle)) * distance) + 0.5, position.getY() + (Math.sin((direction_angle)) * distance));

        //System.out.printf("New Pos (%f,%f) delX %f delY %f angle %f dist %f\n", position.getX(),position.getY(),Math.cos(angle)*distance, Math.sin(angle)*distance, angle, distance);
    }

    public CartesianCoordinate Get_Target_Position() {
        double distance = speed / 60;
        return new CartesianCoordinate(position.getX() + (Math.cos((target_angle)) * distance) + 0.5, position.getY() + (Math.sin((target_angle)) * distance));
        //System.out.printf("New Pos (%f,%f) delX %f delY %f angle %f dist %f\n", position.getX(),position.getY(),Math.cos(angle)*distance, Math.sin(angle)*distance, angle, distance);
    }

    public double get_target_angle() {
        return target_angle;
    }

    public double get_angular_velocity() {
        return angular_velocity;
    }

    public double get_speed() {
        return speed;
    }

    public void set_speed(double new_speed) { //Always positive
        speed = Math.abs(new_speed);
    }

    public void set_target_angle(double new_angle) {
        if (new_angle < -Math.PI) {
            new_angle += Math.PI * 2;
        } else if (new_angle > Math.PI) {
            new_angle -= Math.PI * 2;
        }

        target_angle = new_angle;

        //System.out.printf("angle %f\n", angle);
    }

    public double get_direction_angle() {
        return direction_angle;
    }

    protected void set_direction_angle(double new_angle) {
        if (new_angle < -Math.PI) {
            new_angle += Math.PI * 2;
        } else if (new_angle > Math.PI) {
            new_angle -= Math.PI * 2;
        }

        direction_angle = new_angle;

        //System.out.printf("angle %f\n", angle);
    }

    public void set_angular_velocity(double angular_vel) {
        angular_velocity = angular_vel;
    }

    protected void set_position(CartesianCoordinate new_position) {
        position = new_position;
    }

    public CartesianCoordinate get_position() {
        return position;
    }

    public CartesianCoordinate get_local_COM() {
        return local_centre;
    }

    protected void set_local_COM(CartesianCoordinate COM) {
        local_centre = COM;
    }

    public BufferedImage get_image() {
        return image;
    }

    protected boolean bounce(List<obstacle> obstacles) {
        //Code to bounce animal away from obstacle
        if (bouncing == 0) {
            for (scene_object obstacle : obstacles) {
                if (this.get_distance(obstacle.get_location()) <= obstacle.get_size() + 10) {
                    set_target_angle(target_angle + Math.PI);
                    bouncing = 30;
                }
            }
        }

        if (bouncing != 0) {
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
