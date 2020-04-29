/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import animals.predatorBird;
import drawing.Canvas;
import geometry.CartesianCoordinate;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.ThreadLocalRandom;
import javax.sql.rowset.serial.SerialArray;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author REDACTED
 */
public class scene implements ActionListener, ChangeListener, PropertyChangeListener {

    private ArrayList<flock> bird_flocks;
    private ArrayList<obstacle> Obstacles;
    private ArrayList<scene_object> scene_objects;
    private JFrame frame;
    private Canvas canvas;
    private final int WINDOW_X_SIZE = 1024;
    /* Set the JFrame to a standard resolution. */
    private final int WINDOW_Y_SIZE = 768;
    /* XGA is used, as it will fit easily into anything recent
                                            * without being too small */
    private final double default_cohesion = 0.5;
    private final double default_alignment = 0.5;
    private final double default_separation = 0.5;
    private double cohesion = default_cohesion;
    private double alignment = default_alignment;
    private double separation = default_separation;
    private final int viewRadiusDEF = 100;
    private int viewRadius = viewRadiusDEF;
    private Timer timer;
    private boolean paused = false;
    private boolean debug = false;
    private final int updates_sMIN = 0;
    private final int updates_sMAX = 300;
    private final int updates_sDEF = 60;
    private final int flock_sizeDEF = 20;
    private int flock_size = flock_sizeDEF;

    private JPanel BottomPanel;
    private JPanel RightPanel;
    private JPanel boids_panel;

    public scene(CartesianCoordinate obstacle_loc, int obstacle_radius) {
        int obstacle_complexity = (int) (obstacle_radius * 1.5);
        Obstacles = new ArrayList<obstacle>();
        /* Can use multiple obstacles */
        bird_flocks = new ArrayList<flock>();
        /* I have the ability to use multiple flocks, but it is not used currently as it is not required. */
        Obstacles.add(new obstacle(obstacle_loc, obstacle_radius, obstacle_complexity));
        bird_flocks.add(new flock(flock_size, default_cohesion, default_alignment, default_separation, Obstacles));
        establish();
    }

    private void setup_RightPanel() {
        RightPanel = new JPanel();
        RightPanel.setLayout(new GridLayout(15, 1));
        JButton reset_Button = new JButton("Set Size & Reset");
        reset_Button.setToolTipText("Resets behavoiur and creates new flock with set size at the origin");
        reset_Button.setActionCommand("reset");
        reset_Button.addActionListener(this);
        RightPanel.add(reset_Button);
        JButton extra_Button = new JButton("Show Extras");
        /*Shows targeting stuff on screen*/
        extra_Button.setActionCommand("debug");
        extra_Button.addActionListener(this);
        RightPanel.add(extra_Button);

        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        for (int i = 0; i <= 10; i += 2) {
            double label = ((double) i / 10);
            labelTable.put(i, new JLabel(Double.toString(label)));
        }

        JSlider cohesionSlider = new JSlider(JSlider.HORIZONTAL,
                0, 10, 5);
        cohesionSlider.setLabelTable(labelTable);
        cohesionSlider.setName("cohesion");
        cohesionSlider.setMinorTickSpacing(1);
        cohesionSlider.setPaintTicks(true);
        cohesionSlider.setPaintLabels(true);
        cohesionSlider.addChangeListener(this);
        JLabel cohesionlabel = new JLabel("Cohesion", SwingConstants.CENTER);
        RightPanel.add(cohesionlabel);
        RightPanel.add(cohesionSlider);

        JSlider separationSlider = new JSlider(JSlider.HORIZONTAL,
                0, 10, 5);
        separationSlider.setLabelTable(labelTable);
        separationSlider.setName("separation");
        separationSlider.setMinorTickSpacing(1);
        separationSlider.setPaintTicks(true);
        separationSlider.setPaintLabels(true);
        separationSlider.addChangeListener(this);
        JLabel separationlabel = new JLabel("Separation", SwingConstants.CENTER);
        RightPanel.add(separationlabel);
        RightPanel.add(separationSlider);

        JSlider alignmentSlider = new JSlider(JSlider.HORIZONTAL,
                0, 10, 5);
        alignmentSlider.setLabelTable(labelTable);
        alignmentSlider.setName("alignment");
        alignmentSlider.setMinorTickSpacing(1);
        alignmentSlider.setPaintTicks(true);
        alignmentSlider.setPaintLabels(true);
        alignmentSlider.addChangeListener(this);
        JLabel alignmentlabel = new JLabel("Alignment", SwingConstants.CENTER);
        RightPanel.add(alignmentlabel);
        RightPanel.add(alignmentSlider);

        JSlider viewJSlider = new JSlider(JSlider.HORIZONTAL,
                20, 100, viewRadiusDEF);
        viewJSlider.setMajorTickSpacing(20);
        viewJSlider.setMinorTickSpacing(1);
        viewJSlider.setPaintTicks(true);
        viewJSlider.setPaintLabels(true);
        viewJSlider.addChangeListener(this);
        viewJSlider.setName("view");
        JLabel viewJLabel = new JLabel("View Radius", SwingConstants.CENTER);
        RightPanel.add(viewJLabel);
        RightPanel.add(viewJSlider);

        JLabel sizelabel = new JLabel("Flock Size", SwingConstants.CENTER);
        JFormattedTextField flock_size_input = new JFormattedTextField();
        flock_size_input.setValue(flock_size);
        flock_size_input.addPropertyChangeListener(this);
        RightPanel.add(sizelabel);
        RightPanel.add(flock_size_input);
        RightPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 0, 5, Color.black));
        RightPanel.revalidate();
        frame.add(RightPanel, BorderLayout.EAST);
        frame.revalidate();/* Important! The frame must be validated to make it lay itself out */
        frame.repaint();
    }

    private void setup_BottomPanel() {
        BottomPanel = new JPanel();
        BottomPanel.setLayout(new BorderLayout());
        JPanel topBottom = new JPanel(new GridLayout(1, 10));
        JButton pause_Button = new JButton("►/❚❚");
        pause_Button.setActionCommand("pause");
        pause_Button.addActionListener(this);
        topBottom.add(pause_Button);
        JSlider speedSlider = new JSlider(JSlider.HORIZONTAL,
                updates_sMIN, updates_sMAX, updates_sDEF);
        speedSlider.setMajorTickSpacing(10);
        speedSlider.setMinorTickSpacing(1);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.addChangeListener(this);
        speedSlider.setName("speed");
        JLabel speedlabel = new JLabel("Updates per Second", SwingConstants.CENTER);
        BottomPanel.add(speedlabel, BorderLayout.CENTER);
        BottomPanel.add(speedSlider, BorderLayout.SOUTH);
        BottomPanel.add(topBottom, BorderLayout.NORTH);
        BottomPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.black));
        BottomPanel.revalidate();
        frame.add(BottomPanel, BorderLayout.SOUTH);
        frame.revalidate();/* Important! The frame must be validated to make it lay itself out */
    }

    private void setup_BoidsPanel() {
        boids_panel = new JPanel(new FlowLayout());
        /* Stops the BorderLayout resizing my canvas, as BorderLayout does not respect preferred size but FlowLayout does */
 /* For production code I'd use a better layout manager, but it isn't worth it for this */
        canvas = new Canvas(frame);
        canvas.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
        boids_panel.add(canvas);
        frame.add(boids_panel, BorderLayout.WEST);
        frame.revalidate();/* Important! The frame must be validated to make it lay itself out */
        frame.repaint();
    }

    private void setup_Frame() {
        frame = new JFrame();
        frame.setTitle("Boids");
        frame.setSize(WINDOW_X_SIZE, WINDOW_Y_SIZE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true); //Hello
        frame.setResizable(false);/* I can't have just anyone resizing the window now, can I? */
        frame.setLayout(new BorderLayout());
    }

    private void setup_GUI() {
        setup_Frame();
        /* Create Timer */
        timer = new Timer((int) (1000 / updates_sDEF), this);
        timer.setActionCommand("timer");
        /* Set up UI elements */
        setup_BottomPanel();
        setup_RightPanel();
        setup_BoidsPanel();
        /* Add stuff to the window */
        frame.validate();
    }

    private void establish() {
        scene_objects = new ArrayList<scene_object>();
        setup_GUI();
        
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) { //Goodbye
                System.exit(0);
            }
        });

        
        scene_objects.addAll(Obstacles);
        scene_objects.addAll(bird_flocks);
        redraw_scene();
        timer.start(); //Start animation
    }

    private void set_Flock_parameters(double newCohesion, double newAlignment, double newSeparation, int newRadius) { /*Sets the parameters of all flocks   */
        for (scene_object obj : scene_objects) {                                                                      /*If I were using multiple flocks     */
            if (obj.get_class().equals("scene.flock")) {                                                              /*this should be modified. But I'm not*/
                flock flk = (flock) obj;
                flk.set_cohesion(newCohesion);
                flk.set_alignment(newAlignment);
                flk.set_separation(newSeparation);
                flk.set_viewRadius(newRadius);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /* Respond to input. programme.is_vegitable() == 0*/
        if ("timer".equals(e.getActionCommand())) {
            update_scene();
            redraw_scene();
            timer.start();

        } else if ("reset".equals(e.getActionCommand())) {
            reset_defaults();
            for (int i = 0; i < scene_objects.size(); i++) {
                if (scene_objects.get(i).get_class().equals("scene.flock")) {
                    scene_objects.set(i, new flock(flock_size, default_cohesion, default_alignment, default_separation, Obstacles));
                }
            }
        } else if ("pause".equals(e.getActionCommand())) {
            if (paused) {
                timer.start();
                paused = false;
            } else {
                timer.stop();
                paused = true;
            }
        } else if ("debug".equals(e.getActionCommand())) {
            debug = !debug;
            redraw_scene();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if ("speed".equals(source.getName())) {
            if (!source.getValueIsAdjusting()) {
                int fps = (int) source.getValue();
                if (fps != 0) {
                    timer.setDelay(1000 / fps);
                    timer.restart();
                } else {
                    timer.stop();
                }
            }
        } else if ("cohesion".equals(source.getName())) {
            cohesion = (double) source.getValue() / 10;
            set_Flock_parameters(cohesion, alignment, separation, viewRadius);
        } else if ("separation".equals(source.getName())) {
            separation = (double) source.getValue() / 10;
            set_Flock_parameters(cohesion, alignment, separation, viewRadius);
        } else if ("alignment".equals(source.getName())) {
            alignment = (double) source.getValue() / 10;
            set_Flock_parameters(cohesion, alignment, separation, viewRadius);
        } else if ("view".equals(source.getName())) {
            viewRadius = source.getValue();
            set_Flock_parameters(cohesion, alignment, separation, viewRadius);
        }
    }

    private void reset_defaults() { /* Resets to initial conditions */
        timer.stop();
        timer.setDelay(1000 / updates_sDEF);
        frame.getContentPane().removeAll();
        setup_BoidsPanel();
        setup_BottomPanel();
        setup_RightPanel();
        timer.restart();
    }

    /*
    This method must be called to draw, usually after an update.
    It is likely NOT thread safe! 
            You have been warned!!
     */
    private void redraw_scene() {
        canvas.clear();
        for (scene_object object : scene_objects) {
            object.draw(canvas, debug);
        }
    }

    private void update_scene() {
        for (scene_object object : scene_objects) {
            object.update(canvas);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        JFormattedTextField sourceField = (JFormattedTextField) pce.getSource();
        flock_size = ((Number) sourceField.getValue()).intValue();
    }

}
