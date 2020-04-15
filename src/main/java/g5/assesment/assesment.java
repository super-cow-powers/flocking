/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g5.assesment;

import geometry.CartesianCoordinate;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import scene.scene;

/**
 *
 * @author david
 */
public class assesment {

    /**
     * @param args the command line arguments
     */
    private scene active_scene;
    private double updates_s = 60;

    private void setup_scene(CartesianCoordinate obstacle_location, int obstacle_radius, int flock_size) {
        active_scene = new scene(obstacle_location, obstacle_radius, (int) (obstacle_radius * 1.5), flock_size);

        //active_scene = new scene(obstacle_location,obstacle_radius,3,flock_size);
        //draw flock
        //return scene
    }

    private void update() {

        
        ActionListener animate = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               active_scene.update_scene();
               active_scene.redraw_scene();
            }
        };
        Timer timer = new Timer((int)(1/updates_s) ,animate);
        timer.setRepeats(true);
        timer.start();
    }

    public static void main(String[] args) throws InterruptedException { //If either thread is interrupted - terminate.
        CartesianCoordinate obstacle_location = new CartesianCoordinate(350, 250);
        assesment assesmentMain = new assesment();
        assesmentMain.setup_scene(obstacle_location, 75, 30);
        System.out.println("g5.assesment.assesment.main()");
        assesmentMain.update();

    }
}
