/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g5.assesment;

import geometry.CartesianCoordinate;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    
    private void setup_scene(CartesianCoordinate obstacle_location, int obstacle_radius, int flock_size){
        active_scene = new scene(obstacle_location,obstacle_radius,(int)(obstacle_radius*1.5),flock_size);
        
        //active_scene = new scene(obstacle_location,obstacle_radius,3,flock_size);
        //draw flock
        //return scene
    }
    
    private void loop(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(assesment.class.getName()).log(Level.SEVERE, null, ex);
        }
        active_scene.update_scene();
        while(true){
         //Run main loop
     }
    }
    

    public static void main(String[] args) {
     CartesianCoordinate obstacle_location = new CartesianCoordinate(350, 250);  
     assesment assesmentMain = new assesment();
     assesmentMain.setup_scene(obstacle_location,75,10);
     assesmentMain.loop();
    }

}
