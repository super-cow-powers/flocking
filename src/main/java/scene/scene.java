/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import drawing.Canvas;
import geometry.CartesianCoordinate;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author david
 */
public class scene{

    private flock bird_flock;
    private obstacle Obstacle;
    private ArrayList<scene_object> scene_objects;
    private JFrame frame;
    private Canvas canvas;
    private final int WINDOW_X_SIZE = 800;
    private final int WINDOW_Y_SIZE = 600;
    private final double default_cohesion=0.7;
    private final double default_alignment=0.4;
    private final double default_separation=0.4;
    
    public scene(CartesianCoordinate obstacle_loc, int obstacle_radius, int obstacle_complexity, int flock_num) {
            Obstacle = new obstacle(obstacle_loc, obstacle_radius, obstacle_complexity);
            bird_flock = new flock(flock_num, default_cohesion, default_alignment, default_separation);
            establish();
    }

    private void establish() {
        scene_objects = new ArrayList<scene_object>();
        frame = new JFrame();
        frame.setTitle("Canvas");
        frame.setSize(WINDOW_X_SIZE, WINDOW_Y_SIZE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        canvas = new Canvas(frame);
        frame.getContentPane().add(canvas);
        Canvas newcan; 
        newcan = (Canvas)frame.getContentPane().getComponent(0);
        JFrame.setDefaultLookAndFeelDecorated(true);
        scene_objects.add(Obstacle);
        scene_objects.add(bird_flock);
        redraw_scene();
    }
    
    /*
    This method must be called to draw, usually after an update.
    It is likely NOT thread safe! You have been warned!!
    */
    public void redraw_scene(){
        canvas.clear();
        //long startTime = System.nanoTime();
        for (scene_object object : scene_objects){
            object.draw(canvas);
        }
        //long endTime = System.nanoTime();
        //System.out.printf("time %d\n", (endTime-startTime));
    }
    

    public void update_scene(){
        for (scene_object object : scene_objects){
            object.update(canvas);
        }
    }
}
