/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import animals.*;
import drawing.Canvas;
import geometry.CartesianCoordinate;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class flock implements scene_object {

    //private flockingBird flock_members[];
    private List<flockingBird> flock_members = new ArrayList<>();
    private float radius = 50;
    private ArrayList<obstacle>obstacles;

    flock(int size, double cohesion, double alignment, double separation, ArrayList<obstacle>Obstacles) {
        obstacles = Obstacles;
        int j = 0,i = 0;
        while (j < size) {
            for (int k = 0; k < (int)(25 * ((double)size / 4)); k += 25) {
                //System.out.printf("%d %d\n", size, j);
                flock_members.add(new flockingBird(10 + k, 10 + i, cohesion, alignment, separation));
                j++;
                if (j == size) {
                    break;
                }
            }
            i += 25;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        for (animal flock_member : flock_members) {
            //canvas.draw_triangle(flock_member.get_position(), 10, flock_member.get_angle(), Color.blue, Color.black, Color.blue); //Slower
            //canvas.drawLineSegments(flock_member.get_segments());
            canvas.drawBird(flock_member);
            /*canvas.draw_circle(flock_member.get_local_COM(), 3, 4, 0, Color.blue);
            canvas.drawLineBetweenPoints(flock_member.get_position(), flock_member.get_local_COM(), Color.red);
            canvas.drawLineBetweenPoints(flock_member.get_position(), flock_member.Get_Target_Position(), Color.yellow);
            canvas.drawLineBetweenPoints(flock_member.get_position(), flock_member.Get_Actual_Position(), Color.green);*/
        }
    }

    /*
    *@param ammount number between -1 and 1
     */
    public void set_cohesion(double ammount) {
        for (flockingBird bird : flock_members) {
            bird.set_cohesion(ammount);
        }
    }

    /*
    *@param ammount number between -1 and 1
     */
    public void set_alignment(double ammount) {
        for (flockingBird bird : flock_members) {
            bird.set_alignment(ammount);
        }
    }

    //@Override
    public void update_st(Canvas canvas) {
        double canvas_X = canvas.getWidth(), canvas_Y = canvas.getHeight();
        //Single Threaded version. DEBUG ONLY
        update_members(1, canvas_X, canvas_Y, canvas);
        update_members(2, canvas_X, canvas_Y, canvas);
        update_members(3, canvas_X, canvas_Y, canvas);
        //for (animal flock_member : flock_members) {
        //  System.out.printf("ANG: (%f)\n", flock_member.get_angle());
        //}
    }

    private void update_members(int number, double canvas_X, double canvas_Y, Canvas canvas) {
        
        for (int i = (((number - 1) * flock_members.size()) / 3); i < (int) ((number * flock_members.size()) / 3); i++) {
            flock_members.get(i).navigate(flock_members, canvas_X, canvas_Y, obstacles);
        }
    }

    @Override
    public void update(Canvas canvas) {
        double canvas_X = canvas.getWidth(), canvas_Y = canvas.getHeight();
        //update_ST();

        /*Threaded version. 
        Kind of trashy, but has no need to be anything other really - as it's the most simple way
        to implement what I want. Still Pegs my G5 though.
         */
        Thread t, t1, t2; //Create 3 calculation threads to split the large task
        t = new Thread(new Runnable() { // Create an anonymous inner class that implements Runnable interface
            @Override
            public void run() {
                // Thread's running behavior
                update_members(1, canvas_X, canvas_Y, canvas);

            }
        });
        t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                update_members(2, canvas_X, canvas_Y, canvas);

            }
        });
        t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                update_members(3, canvas_X, canvas_Y, canvas);

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

    @Override
    public CartesianCoordinate get_location() {
        throw new UnsupportedOperationException("Not supported for this object."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double get_size() {
        return flock_members.size();
    }

}
