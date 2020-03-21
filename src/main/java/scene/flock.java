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

    private animal flock_members[];
    private CartesianCoordinate flock_centre;
    private float cohesion;
    private float separation;
    private float alignment;
    private float update_radius;
    private float radius = 1000;

    flock(int size, String flock_of) throws ClassNotFoundException {
        switch (flock_of) {
            case ("flocking bird"):

                int j = 0,
                 i = 0;
                flock_members = new flockingBird[size];
                while (j < size) {
                    for (int k = 0; k < 25 * (int) (size / 4); k += 25) {
                        //System.out.printf("%d\n", j);
                        flock_members[j] = new flockingBird(10 + k, 10 + i);
                        j++;
                        if (j == size) {
                            break;
                        }
                    }
                    i += 25;
                }
                break;

            default:
                throw new ClassNotFoundException();
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

    public void draw(Canvas canvas) {
        for (animal flock_member : flock_members) {
            canvas.drawLineSegments(flock_member.get_segments());
        }
    }


    public void update() {
        //Threaded version
        Thread t, t1, t2; //Create 3 calculation threads to split the large task
        t = new Thread(new Runnable() { // Create an anonymous inner class that implements Runnable interface
            @Override
            public void run() {
                // Thread's running behavior
                // Can access the private variables and methods of the outer class
                animal member;
                double averageX, averageY;
                for (int i = 0; i < (int) (flock_members.length / 3); i++) {
                    averageX = 0;
                    averageY = 0;
                    member = flock_members[i];
                    for (int j = 0; j < flock_members.length; j++) {
                        if ((separation(flock_members[i], flock_members[j]) < radius)) {
                            averageX += flock_members[j].get_position().getX();
                            averageY += flock_members[j].get_position().getY();
                            //System.out.printf("X Y: (%f,%f)\n", flock_members[j].get_position().getX(),flock_members[j].get_position().getY());
                        }
                    }
                    //System.out.printf("AVG: (%f,%f)\n", averageX,averageY);
                    averageX = averageX / (flock_members.length);
                    averageY = averageY / (flock_members.length);
                    member.set_local_COM(new CartesianCoordinate(averageX, averageY));
                    member.set_angle(Math.atan2(averageY - member.get_position().getY(), member.get_position().getX() - averageX));
                    member.update_segments();
                    
                }
            }
        });
        t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                animal member;
                double averageX, averageY;
                for (int i = (int) (flock_members.length / 3); i < (int) (2 * flock_members.length / 3); i++) {
                    averageX = 0;
                    averageY = 0;
                    member = flock_members[i];
                    for (int j = 0; j < flock_members.length; j++) {
                        if ((separation(flock_members[i], flock_members[j]) < radius)) {
                            averageX += flock_members[j].get_position().getX();
                            averageY += flock_members[j].get_position().getY();
                            //System.out.printf("X Y: (%f,%f)\n", flock_members[j].get_position().getX(),flock_members[j].get_position().getY());
                        }
                    }
                    //System.out.printf("AVG: (%f,%f)\n", averageX,averageY);
                    averageX = averageX / (flock_members.length);
                    averageY = averageY / (flock_members.length);
                    member.set_local_COM(new CartesianCoordinate(averageX, averageY));
                    member.set_angle(Math.atan2(averageY - member.get_position().getY(), member.get_position().getX() - averageX));
                    member.update_segments();
                    
                }
            }
        });
        t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                animal member;
                double averageX, averageY;
                for (int i = (int) (2 * flock_members.length / 3); i < (int) (flock_members.length); i++) {
                    averageX = 0;
                    averageY = 0;
                    member = flock_members[i];
                    for (int j = 0; j < flock_members.length; j++) {
                        if ((separation(flock_members[i], flock_members[j]) < radius)) {
                            averageX += flock_members[j].get_position().getX();
                            averageY += flock_members[j].get_position().getY();
                            //System.out.printf("X Y: (%f,%f)\n", flock_members[j].get_position().getX(),flock_members[j].get_position().getY());
                        }
                    }
                    //System.out.printf("AVG: (%f,%f)\n", averageX,averageY);
                    averageX = averageX / (flock_members.length);
                    averageY = averageY / (flock_members.length);

                    member.set_local_COM(new CartesianCoordinate(averageX, averageY));
                    member.set_angle(Math.atan2(averageY - member.get_position().getY(), member.get_position().getX() - averageX));
                    member.update_segments();
                    
                }
            }
        });
        t.start();
        t1.start();
        t2.start();
        try {
            t2.join(); //pause current thread till calculation threads complete
            t1.join();
            t.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(flock.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (animal flock_member : flock_members) {
            //System.out.printf("COM: (%f,%f)\n", flock_member.get_local_COM().getX(),flock_member.get_local_COM().getX());

        }
        //Single Threaded version
        /*animal member;
        double averageX, averageY, angle;
        for (int i = 0; i < (int) (flock_members.length); i++) {
            averageX = 0;
            averageY = 0;
            member = flock_members[i];
            for (int j = 0; j < flock_members.length; j++) {
                if ((separation(flock_members[i], flock_members[j]) < radius)) {
                    averageX += flock_members[j].get_position().getX();
                    averageY += flock_members[j].get_position().getY();
                    System.out.printf("X Y: (%f,%f)\n", flock_members[j].get_position().getX(), flock_members[j].get_position().getY());
                }
            }
            System.out.printf("AVG: (%f,%f)\n", averageX, averageY);
            averageX = averageX / (flock_members.length);
            averageY = averageY / (flock_members.length);

            member.set_local_COM(new CartesianCoordinate(averageX, averageY));
            member.set_angle(Math.atan2(averageY - member.get_position().getY(), member.get_position().getX() - averageX));
            System.out.printf("ANGLE: (%f)\n", Math.atan2(averageY - member.get_position().getY(), member.get_position().getX() - averageX));
            member.update_segments();
        }
        
        for (animal flock_member : flock_members) {
            System.out.printf("ANG: (%f)\n", flock_member.get_angle());

        }*/

    }

}
