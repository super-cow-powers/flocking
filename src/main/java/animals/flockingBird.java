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
public final class flockingBird extends animal {
    
    double cohesion;
    double alignment;
    
    public flockingBird(double xLoc, double yLoc) {
        name="flocking bird";
        speed=0;
        angle=0; //-Math.PI/2; //Default face upwards
        angular_velocity=0;
        position=new CartesianCoordinate(xLoc, yLoc);
        segments=new LineSegment[3];
        update_segments();
    }
    
    public void set_cohesion(double ammount){
        if (ammount <= 1){
            cohesion = ammount;
        } 
        else if (ammount > 1){
            System.out.println("Hey, so your Cohesion should be between -1 and 1, yours is >1. Would you check it please? I've set it to 1 for now.");
            cohesion = 1;
        }
        else if (ammount < -1){
            System.out.println("Hey, so your Cohesion should be between -1 and 1, yours is <-1. Would you check it please? I've set it to -1 for now.");
            cohesion = -1;
        }
    }
    
    public double cohesion(){
        return cohesion;
    }
    public void set_alignment(double ammount){
        if (ammount <= 1){
            alignment = ammount;
        } 
        else if (ammount > 1){
            System.out.println("Hey, so your Alignment should be between -1 and 1, yours is >1. Would you check it please? I've set it to 1 for now.");
            alignment = 1;
        }
        else if (ammount < -1){
            System.out.println("Hey, so your Alignment should be between -1 and 1, yours is <-1. Would you check it please? I've set it to -1 for now.");
            alignment = -1;
        }    
    }
    
    public double alignment(){
        return alignment;
    }
    
    @Override
    protected void Set_RGBA(){
        segments[1].set_rgba(0, 0, 0, 200);
        segments[0].set_rgba(0, 100, 255, 200);
        segments[2].set_rgba(0, 100, 255, 200);
    }
    
    
    
    
}
