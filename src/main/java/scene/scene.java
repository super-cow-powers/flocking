/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import drawing.Canvas;
import geometry.CartesianCoordinate;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author david
 */
public class scene extends Canvas {

    private flock bird_flock;
    private obstacle Obstacle;
    private ArrayList<scene_object> scene_objects;
    private JFrame frame;
    private Canvas canvas;
    private final int WINDOW_X_SIZE = 800;
    private final int WINDOW_Y_SIZE = 600;

    public scene(CartesianCoordinate obstacle_loc, int obstacle_radius, int obstacle_complexity, int flock_num) {
        try {
            Obstacle = new obstacle(obstacle_loc, obstacle_radius, obstacle_complexity);
            bird_flock = new flock(flock_num, "flocking bird");
            establish();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(scene.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void establish() {
        scene_objects = new ArrayList<scene_object>();
        frame = new JFrame();
        frame.setTitle("Canvas");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        canvas = new Canvas();
        frame.getContentPane().add(canvas);
        
        scene_objects.add(Obstacle);
        scene_objects.add(bird_flock);
        redraw_scene();
    }
    private void redraw_scene(){
        for (scene_object object : scene_objects){
            object.draw(canvas);
        }
    }
    
    public void update_scene(){
        for (scene_object object : scene_objects){
            object.update();
        }
        canvas.clear();
        bird_flock.draw_COM(canvas);
        redraw_scene();
    }
}
