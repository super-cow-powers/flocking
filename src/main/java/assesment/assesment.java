/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assesment;

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
 * @author REDACTED
 */
public class assesment {

    /**
     * @param args the command line arguments
     */
    private scene active_scene;

    private void setup_scene(CartesianCoordinate obstacle_location, int obstacle_radius) {
        active_scene = new scene(obstacle_location, obstacle_radius, (int) (obstacle_radius * 1.5));
    }

    public static void main(String[] args) throws InterruptedException { //If either thread is interrupted - terminate.
        CartesianCoordinate obstacle_location = new CartesianCoordinate(350, 250);
        assesment assesmentMain = new assesment();
        assesmentMain.setup_scene(obstacle_location, 75);
        System.out.println("g5.assesment.assesment.main()");

    }
    
    
}
