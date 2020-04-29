/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animals;

import geometry.CartesianCoordinate;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import scene.obstacle;
import scene.scene_object;

/**
 * This is a predator bird. Hunts down flocking birds.
 *
 * @author REDACTED
 */
public class predatorBird extends animal {

    private List<flockingBird> prey;

    public predatorBird(double xLoc, double yLoc, List<flockingBird> flock) {
        prey = flock;
        speed = ThreadLocalRandom.current().nextInt(70, 100);
        target_angle = 0;
        direction_angle = 0;
        bouncing = 0;
        position = new CartesianCoordinate(xLoc, yLoc);
        try {
            image = ImageIO.read(new File("predator.png"));
        } catch (IOException e) {
            try {
                image = ImageIO.read(new File("assets/predator.png"));
            } catch (IOException ex) {
                Logger.getLogger(flockingBird.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void seek() {
        set_direction_angle(target_angle);
        set_position(Get_Direction_Position());
    }

    public void navigate(double canvas_X, double canvas_Y, List<obstacle> obstacles) {
        wrap(canvas_X, canvas_Y);
        bounce(obstacles);
        double new_target_angle = 0;
        for (flockingBird bird : prey) {
            new_target_angle += (CartesianCoordinate.angle_between(position, bird.get_position()) / (this.get_distance(bird.get_position()) / 10));
        }
        set_target_angle(new_target_angle);
        seek();
    }

    @Override
    protected boolean bounce(List<obstacle> obstacles) {
        /* Kill the bird if it hits the obstacle. */
        if (bouncing == 0) {
            for (scene_object obstacle : obstacles) {
                if (this.get_distance(obstacle.get_location()) <= obstacle.get_size()+5) {
                    set_position(new CartesianCoordinate((double) ThreadLocalRandom.current().nextInt(400, 600), (double) ThreadLocalRandom.current().nextInt(400, 600)));
                    /* Resurrect it elsewhere */
                    return true;
                }
            }
        }
        return false;
    }

}
