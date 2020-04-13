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
import javax.swing.ImageIcon;


/**
 *
 * @author david
 */
public class animal {
    static final Random r = new Random();
    double speed;/* In px/s */
    double angle; /* in rads */
    double view_angle; /* in rads. How far the animal can see */
    double view_radius; /* In px */ 
    double angular_velocity;
    BufferedImage image;
    CartesianCoordinate position;
    String name;
    CartesianCoordinate local_centre;
    
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
    
    public CartesianCoordinate Get_New_Position(){
        double distance = speed/60;
        return new CartesianCoordinate(position.getX()+(Math.cos(angle)*distance), (Math.sin(angle)*distance)+position.getY());
        //System.out.printf("New Pos (%f,%f) delX %f delY %f angle %f dist %f\n", position.getX(),position.getY(),Math.cos(angle)*distance, Math.sin(angle)*distance, angle, distance);
    }
    
   

    public double get_angle() {
        return angle;
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
    

    public void set_position(CartesianCoordinate new_position) {
        position = new_position;
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
    
    public BufferedImage get_image(){
     return image;
    }


}
