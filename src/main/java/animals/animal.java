/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animals;

import geometry.CartesianCoordinate;
import geometry.LineSegment;
import java.awt.Color;

/**
 *
 * @author david
 */
public class animal {

    double speed;
    double angle;
    double angular_velocity;
    double cohesion;
    double alignment;
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
        if (new_angle>2*Math.PI){
            new_angle-=2*Math.PI;
        } else if (new_angle<(-2)*Math.PI){
            new_angle+=2*Math.PI;
        }
        
        angle = new_angle;
        
        //System.out.printf("angle %f\n", angle);
    }

    public void set_angular_velocity(double angular_vel) {
        angular_velocity = angular_vel;
    }
    
    public void set_cohesion(double ammount){
        cohesion = ammount;
    }
    
    public double cohesion(){
        return cohesion;
    }
    public void set_alignment(double ammount){
        alignment = ammount;
    }
    
    public double alignment(){
        return alignment;
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
    
    protected void Set_RGBA(){ //Override to set custom colour to sub-class
        segments[1].set_rgba(0, 0, 0, 200);
        segments[0].set_rgba(0,0,0,200);
        segments[2].set_rgba(0,0,0,200);
    }
    
    public void update_segments(){
        CartesianCoordinate pointA,pointB;
        double TempAngle = angle;
        int i=0;
        while (i<3){
                    pointA=new CartesianCoordinate((position.getX() + (Math.sin(TempAngle-(Math.PI/2)))*10),(position.getY() + (Math.cos(TempAngle-(Math.PI/2)))*10));
                    pointB=new CartesianCoordinate((position.getX() + (Math.sin(TempAngle-(Math.PI/2)+((2*Math.PI)/3)))*10),(position.getY() + (Math.cos(TempAngle-(Math.PI/2)+((2*Math.PI)/3)))*10));
                    TempAngle=(TempAngle +((2*Math.PI)/3));
                    segments[i] = new LineSegment(pointA, pointB);
                    i++;
                }
        Set_RGBA();
    }
}
