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
    
    public flockingBird(double xLoc, double yLoc) {
        name="flocking bird";
        speed=0;
        angle=0; //-Math.PI/2; //Default face upwards
        angular_velocity=0;
        position=new CartesianCoordinate(xLoc, yLoc);
        segments=new LineSegment[3];
        update_segments();
    }
    
    @Override
    protected void Set_RGBA(){
        segments[1].set_rgba(0, 0, 0, 200);
        segments[0].set_rgba(0, 100, 255, 200);
        segments[2].set_rgba(0, 100, 255, 200);
    }
    
    
    
    
}
