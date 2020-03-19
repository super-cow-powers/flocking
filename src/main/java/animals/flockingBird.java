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
        angle=-Math.PI/2; //Default face upwards
        angular_velocity=0;
        position=new CartesianCoordinate(xLoc, yLoc);
        segments=new LineSegment[3];
        update_segments();
    }
    
    @Override
    public void update_segments(){
       CartesianCoordinate pointA,pointB;
        double TempAngle = angle;
        int i=0;
        while (i<3){
                    pointA=new CartesianCoordinate((position.getX() + (Math.sin(TempAngle))*10),(position.getY() + (Math.cos(TempAngle))*10));
                    pointB=new CartesianCoordinate((position.getX() + (Math.sin(TempAngle+((2*Math.PI)/3)))*10),(position.getY() + (Math.cos(TempAngle+((2*Math.PI)/3)))*10));
                    TempAngle=(TempAngle +((2*Math.PI)/3));
                    segments[i] = new LineSegment(pointA, pointB);
                    i++;
                }
        segments[1].set_rgba(0, 100, 255, 200);
        segments[0].set_rgba(0, 0, 0, 200);
        segments[2].set_rgba(0, 0, 0, 200);
    }
    
    
    
    
}
