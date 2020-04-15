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

    private ArrayList<flock> bird_flocks;
    private ArrayList<obstacle> Obstacles;
    private ArrayList<scene_object> scene_objects;
    private JFrame frame;
    private Canvas canvas;
    private final int WINDOW_X_SIZE = 800;
    private final int WINDOW_Y_SIZE = 600;
    private final double default_cohesion=0.2;
    private final double default_alignment=0.3;
    private final double default_separation=0.4;
    
    public scene(CartesianCoordinate obstacle_loc, int obstacle_radius, int obstacle_complexity, int flock_num) {
            Obstacles = new ArrayList<obstacle>();
            bird_flocks = new ArrayList<flock>();
            Obstacles.add(new obstacle(obstacle_loc, obstacle_radius, obstacle_complexity));
            bird_flocks.add(new flock(flock_num, default_cohesion, default_alignment, default_separation, Obstacles));
            establish();
    }

    private void establish() {
        scene_objects = new ArrayList<scene_object>();
        frame = new JFrame();
        frame.setTitle("Boids");
        frame.setSize(WINDOW_X_SIZE, WINDOW_Y_SIZE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        canvas = new Canvas(frame);
        frame.getContentPane().add(canvas);
        JFrame.setDefaultLookAndFeelDecorated(true);
        scene_objects.addAll(Obstacles);
        scene_objects.addAll(bird_flocks);
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
