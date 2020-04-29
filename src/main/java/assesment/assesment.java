/*
 *This contains the main method to start the Boids application
 *to initialise the scene. Control is handed over to the active_scene
 *when this file is finished.
 */
package assesment;

import geometry.CartesianCoordinate;
import scene.scene;

/**
 *
 * @author REDACTED
 */
public class assesment {

    private scene active_scene;

    private void setup_scene(CartesianCoordinate obstacle_location, int obstacle_radius) {
        active_scene = new scene(obstacle_location, obstacle_radius);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException { //If either thread is interrupted - terminate.
        CartesianCoordinate obstacle_location = new CartesianCoordinate(350, 250);
        assesment assesmentMain = new assesment();
        assesmentMain.setup_scene(obstacle_location, 75);
        System.out.println("g5.assesment.assesment.main()");

    }

}
