/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animals;

import geometry.CartesianCoordinate;
import geometry.LineSegment;

/**
 *
 * @author david
 */
public class animal {

    double speed;
    double angle;
    double angular_velocity;
    CartesianCoordinate position;
    String name;
    LineSegment[] segments;
    CartesianCoordinate local_centre;
    //String type;

    public animal() {
    }

    public double get_speed() {
        return speed;
    }

    public double get_angle() {
        return angle;
    }

    public double get_angular_velocity() {
        return angular_velocity;
    }

    public LineSegment[] get_segments() {
        return segments;
    }

    public void set_speed(double new_speed) {
        speed = new_speed;
    }

    public void set_angle(double new_angle) {
        angle = new_angle;
    }

    public void set_angular_velocity(double angular_vel) {
        angular_velocity = angular_vel;
    }

    public void set_position(CartesianCoordinate new_position) {
        position = new_position;
        update_segments();
    }

    public CartesianCoordinate get_position() {
        return position;
    }
    public CartesianCoordinate get_local_COM(){
        return local_centre;
    }
    
    public void set_local_COM(CartesianCoordinate COM){
        local_centre = COM;
    }
    
    public void update_segments(){
        CartesianCoordinate pointA,pointB;
        int i=0;
        while (i<3){
                    pointA=new CartesianCoordinate((position.getX() + (Math.sin(angle))*10),(position.getY() + (Math.cos(angle))*10));
                    pointB=new CartesianCoordinate((position.getX() + (Math.sin(angle+((2*Math.PI)/3)))*10),(position.getY() + (Math.cos(angle+((2*Math.PI)/3)))*10));
                    angle=(float) (angle+((2*Math.PI)/3));
                    segments[i] = new LineSegment(pointA, pointB);
                    segments[i].set_rgba(0, 0, 0, 255);
                    i++;
                }
    }
}
