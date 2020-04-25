/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animals;

import drawing.Canvas;
import geometry.CartesianCoordinate;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import scene.scene_object;

/**
 * Not yet built. This is a predator bird.
 *
 * @author REDACTED
 */
public class predatorBird extends animal implements scene_object {

    public predatorBird(double xLoc, double yLoc) {
        speed = ThreadLocalRandom.current().nextInt(70, 100);
        target_angle = 0;
        direction_angle = 0;
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

    @Override
    public void draw(Canvas canvas, boolean debug) {
        canvas.drawBird(this);
        throw new UnsupportedOperationException("WIP"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Canvas canvas) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CartesianCoordinate get_location() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double get_size() {
        return 1;
    }

    @Override
    public String get_class() {
        return this.getClass().getName();
    }

}
