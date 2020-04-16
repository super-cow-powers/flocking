/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import drawing.Canvas;
import geometry.CartesianCoordinate;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EtchedBorder;

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
    private final int WINDOW_X_SIZE = 1024; /* Set the JFrame to a standard resolution. */
    private final int WINDOW_Y_SIZE = 768; /* XGA is used, as it will fit easily into anything recent
                                            * without being too small */
    private final double default_cohesion=0.4;
    private final double default_alignment=0.5;
    private final double default_separation=0.3;
    
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
        frame.setLayout(new BorderLayout());
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        JPanel PanelB = new JPanel();
        PanelB.add(new JButton("Reset"));
        JPanel Panel = new JPanel();
        Panel.add(new JButton("Reset"));
        Panel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.black));
        PanelB.setBorder(BorderFactory.createMatteBorder(5, 5, 0, 5, Color.black));
        JPanel HackyMcHackface_panel = new JPanel(new FlowLayout());
        canvas = new Canvas(frame);
        canvas.setBorder(BorderFactory.createLineBorder(Color.black));
        HackyMcHackface_panel.add(canvas); /* Stops the BorderLayout resizing my canvas, as BorderLayout does not respect preferred size but FlowLayout does */
        frame.add(HackyMcHackface_panel, BorderLayout.WEST);
        frame.add(PanelB, BorderLayout.EAST);
        frame.add(Panel, BorderLayout.SOUTH);
        frame.validate();
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
