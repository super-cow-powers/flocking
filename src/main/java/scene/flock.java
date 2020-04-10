/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import animals.*;
import drawing.Canvas;
import geometry.CartesianCoordinate;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author david
 */
public class flock implements scene_object {

    private flockingBird flock_members[];
    private CartesianCoordinate flock_centre;
    private float radius = 200;

    flock(int size, double cohesion, double alignment){

                int j = 0,
                 i = 0;
                flock_members = new flockingBird[size];
                while (j < size) {
                    for (int k = 0; k < 25 * (int) (size / 4); k += 25) {
                        //System.out.printf("%d\n", j);
                        flock_members[j] = new flockingBird(10 + k, 10 + i, cohesion, alignment);
                        j++;
                        if (j == size) {
                            break;
                        }
                    }
                    i += 25;
                }
        }
    

    /*
    Returns the distance between birds, birdA and birdB
     */
    private double separation(animal birdA, animal birdB) {
        CartesianCoordinate birdApos = birdA.get_position(), birdBpos = birdB.get_position();

        double length;
        length = Math.sqrt(Math.abs(Math.pow(birdApos.getX() - birdBpos.getX(), 2))
                + Math.abs(Math.pow(birdApos.getY() - birdBpos.getY(), 2)));
        return length;
    }

    @Override
    public void draw(Canvas canvas) {
        for (animal flock_member : flock_members) {
            //canvas.draw_triangle(flock_member.get_position(), 10, flock_member.get_angle(), Color.blue, Color.black, Color.blue); //Slower
            canvas.drawLineSegments(flock_member.get_segments());
        }
    }
    /*
    *@param ammount number between -1 and 1
    */
    public void set_cohesion(double ammount){
        for (flockingBird bird : flock_members){
            bird.set_cohesion(ammount);
        }
    }
    
    /*
    *@param ammount number between -1 and 1
    */
    public void set_alignment(double ammount){
        for (flockingBird bird : flock_members){
            bird.set_alignment(ammount);
        }
    }
    //@Override
    public void update_st(Canvas canvas){
        double canvas_X=canvas.getWidth(),canvas_Y=canvas.getHeight();
        //Single Threaded version. DEBUG ONLY
        update_members(1, canvas_X, canvas_Y, canvas);
        update_members(2, canvas_X, canvas_Y, canvas);
        update_members(3, canvas_X, canvas_Y, canvas);
        //for (animal flock_member : flock_members) {
          //  System.out.printf("ANG: (%f)\n", flock_member.get_angle());
        //}
    }
    
    private void update_members(int number, double canvas_X, double canvas_Y, Canvas canvas){
        flockingBird member;
                double averageX, averageY, averageAng, newAng;
                for (int i = (((number-1)*flock_members.length) / 3); i < (int) ((number*flock_members.length) / 3); i++) {
                    averageX = 0;
                    averageY = 0;
                    averageAng = 0;
                    member = flock_members[i];
                    /* //Code to wrap animal to other side of window
                    if (member.get_position().getX() > Canvaswidth){
                        member.set_position(new CartesianCoordinate(0, member.get_position().getY()));
                    } else if (member.get_position().getX() < 0){
                        member.set_position(new CartesianCoordinate(Canvaswidth, member.get_position().getY()));
                    }
                    if (member.get_position().getY() > Canvasheight){
                        member.set_position(new CartesianCoordinate(member.get_position().getX(), 0));
                    } else if (member.get_position().getY() < 0){
                        member.set_position(new CartesianCoordinate(member.get_position().getX(), Canvasheight));
                    }*/
                    for (int j = 0; j < flock_members.length; j++) {
                        if ((separation(flock_members[i], flock_members[j]) < radius)) {
                            averageX += flock_members[j].get_position().getX();
                            averageY += flock_members[j].get_position().getY();
                            averageAng += flock_members[j].get_angle();
                            //System.out.printf("X Y: (%f,%f)\n", flock_members[j].get_position().getX(),flock_members[j].get_position().getY());
                        }
                    }
                    //System.out.printf("AVG: (%f,%f)\n", averageX,averageY);
                    averageX = averageX / (flock_members.length);
                    averageY = averageY / (flock_members.length);
                    member.set_local_COM(new CartesianCoordinate(averageX, averageY));
                    averageAng = averageAng / (flock_members.length);
                    newAng = Math.atan2(averageY - member.get_position().getY(), member.get_position().getX() - averageX)*member.cohesion();
                    newAng = newAng+(averageAng - newAng)*member.alignment(); //Sum of centre of mass angle, and average angle*alignment factor.
                    member.set_angle(newAng); //If alignment == 1, then the angle here will equal the average angle.
                    member.update_segments();
                    
                }
    }
    @Override
    public void update(Canvas canvas) {
        double canvas_X=canvas.getWidth(),canvas_Y=canvas.getHeight();
        //update_ST();
        
        /*Threaded version. 
        Kind of trashy, but has no need to be anything other really - as it's the most simple way
        to implement what I want.
        */
        Thread t, t1, t2; //Create 3 calculation threads to split the large task
        t = new Thread(new Runnable() { // Create an anonymous inner class that implements Runnable interface
            @Override
            public void run() {
                // Thread's running behavior
                //update_members(1, canvas_X, canvas_Y, canvas);
                flockingBird member;
                double averageX, averageY, averageAng, newAng;
                for (int i = (((0)*flock_members.length) / 3); i < (int) ((1*flock_members.length) / 3); i++) {
                    averageX = 0;
                    averageY = 0;
                    averageAng = 0;
                    member = flock_members[i];
                    /* //Code to wrap animal to other side of window
                    if (member.get_position().getX() > Canvaswidth){
                        member.set_position(new CartesianCoordinate(0, member.get_position().getY()));
                    } else if (member.get_position().getX() < 0){
                        member.set_position(new CartesianCoordinate(Canvaswidth, member.get_position().getY()));
                    }
                    if (member.get_position().getY() > Canvasheight){
                        member.set_position(new CartesianCoordinate(member.get_position().getX(), 0));
                    } else if (member.get_position().getY() < 0){
                        member.set_position(new CartesianCoordinate(member.get_position().getX(), Canvasheight));
                    }*/
                    for (int j = 0; j < flock_members.length; j++) {
                        if ((separation(flock_members[i], flock_members[j]) < radius)) {
                            averageX += flock_members[j].get_position().getX();
                            averageY += flock_members[j].get_position().getY();
                            averageAng += flock_members[j].get_angle();
                            //System.out.printf("X Y: (%f,%f)\n", flock_members[j].get_position().getX(),flock_members[j].get_position().getY());
                        }
                    }
                    //System.out.printf("AVG: (%f,%f)\n", averageX,averageY);
                    averageX = averageX / (flock_members.length);
                    averageY = averageY / (flock_members.length);
                    member.set_local_COM(new CartesianCoordinate(averageX, averageY));
                    averageAng = averageAng / (flock_members.length);
                    newAng = Math.atan2(averageY - member.get_position().getY(), member.get_position().getX() - averageX)*member.cohesion();
                    newAng = newAng+(averageAng - newAng)*member.alignment(); //Sum of centre of mass angle, and average angle*alignment factor.
                    member.set_angle(newAng); //If alignment == 1, then the angle here will equal the average angle.
                    member.update_segments();
                    
                }
            }
        });
        t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                //update_members(2, canvas_X, canvas_Y, canvas);
                flockingBird member;
                double averageX, averageY, averageAng, newAng;
                for (int i = (((2-1)*flock_members.length) / 3); i < (int) ((2*flock_members.length) / 3); i++) {
                    averageX = 0;
                    averageY = 0;
                    averageAng = 0;
                    member = flock_members[i];
                    /* //Code to wrap animal to other side of window
                    if (member.get_position().getX() > Canvaswidth){
                        member.set_position(new CartesianCoordinate(0, member.get_position().getY()));
                    } else if (member.get_position().getX() < 0){
                        member.set_position(new CartesianCoordinate(Canvaswidth, member.get_position().getY()));
                    }
                    if (member.get_position().getY() > Canvasheight){
                        member.set_position(new CartesianCoordinate(member.get_position().getX(), 0));
                    } else if (member.get_position().getY() < 0){
                        member.set_position(new CartesianCoordinate(member.get_position().getX(), Canvasheight));
                    }*/
                    for (int j = 0; j < flock_members.length; j++) {
                        if ((separation(flock_members[i], flock_members[j]) < radius)) {
                            averageX += flock_members[j].get_position().getX();
                            averageY += flock_members[j].get_position().getY();
                            averageAng += flock_members[j].get_angle();
                            //System.out.printf("X Y: (%f,%f)\n", flock_members[j].get_position().getX(),flock_members[j].get_position().getY());
                        }
                    }
                    //System.out.printf("AVG: (%f,%f)\n", averageX,averageY);
                    averageX = averageX / (flock_members.length);
                    averageY = averageY / (flock_members.length);
                    member.set_local_COM(new CartesianCoordinate(averageX, averageY));
                    averageAng = averageAng / (flock_members.length);
                    newAng = Math.atan2(averageY - member.get_position().getY(), member.get_position().getX() - averageX)*member.cohesion();
                    newAng = newAng+(averageAng - newAng)*member.alignment(); //Sum of centre of mass angle, and average angle*alignment factor.
                    member.set_angle(newAng); //If alignment == 1, then the angle here will equal the average angle.
                    member.update_segments();
                    
                }
            }
        });
        t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                //update_members(3, canvas_X, canvas_Y, canvas);
                flockingBird member;
                double averageX, averageY, averageAng, newAng;
                for (int i = (((3-1)*flock_members.length) / 3); i < (int) ((3*flock_members.length) / 3); i++) {
                    averageX = 0;
                    averageY = 0;
                    averageAng = 0;
                    member = flock_members[i];
                    /* //Code to wrap animal to other side of window
                    if (member.get_position().getX() > Canvaswidth){
                        member.set_position(new CartesianCoordinate(0, member.get_position().getY()));
                    } else if (member.get_position().getX() < 0){
                        member.set_position(new CartesianCoordinate(Canvaswidth, member.get_position().getY()));
                    }
                    if (member.get_position().getY() > Canvasheight){
                        member.set_position(new CartesianCoordinate(member.get_position().getX(), 0));
                    } else if (member.get_position().getY() < 0){
                        member.set_position(new CartesianCoordinate(member.get_position().getX(), Canvasheight));
                    }*/
                    for (int j = 0; j < flock_members.length; j++) {
                        if ((separation(flock_members[i], flock_members[j]) < radius)) {
                            averageX += flock_members[j].get_position().getX();
                            averageY += flock_members[j].get_position().getY();
                            averageAng += flock_members[j].get_angle();
                            //System.out.printf("X Y: (%f,%f)\n", flock_members[j].get_position().getX(),flock_members[j].get_position().getY());
                        }
                    }
                    //System.out.printf("AVG: (%f,%f)\n", averageX,averageY);
                    averageX = averageX / (flock_members.length);
                    averageY = averageY / (flock_members.length);
                    member.set_local_COM(new CartesianCoordinate(averageX, averageY));
                    averageAng = averageAng / (flock_members.length);
                    newAng = Math.atan2(averageY - member.get_position().getY(), member.get_position().getX() - averageX)*member.cohesion();
                    newAng = newAng+(averageAng - newAng)*member.alignment(); //Sum of centre of mass angle, and average angle*alignment factor.
                    member.set_angle(newAng); //If alignment == 1, then the angle here will equal the average angle.
                    member.update_segments();
                    
                }
            }
        });
        t.start();
        t1.start();
        t2.start();
        
        try {
            t2.join(); //pause main thread till calculation threads complete
            t1.join();
            t.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(flock.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /*
        for (animal flock_member : flock_members) {
            System.out.printf("COM: (%f,%f)\n", flock_member.get_local_COM().getX(),flock_member.get_local_COM().getX());

        }
        */

    }

}
