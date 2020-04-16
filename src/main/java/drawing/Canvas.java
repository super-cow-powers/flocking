/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawing;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import geometry.CartesianCoordinate;
import geometry.LineSegment;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.AffineTransform;
import javax.swing.JFrame;

/**
 * <h2>Canvas</h2> This class represents a canvas object that can be drawn to
 * with various line segments.
 *
 * <P>
 * The list of LineSegment's is stored in a collection within the implementation
 * of the class. This collection is now synchronised to deal with the issue of
 * concurrent accesses to the collection.
 */
public class Canvas extends JPanel {

    private static final long serialVersionUID = 1L;
    private int xSize, ySize;
    private List<LineSegment> lines;
    private List<animals.animal> birds;
    private final static int DEFAULT_X = 800;
    private final static int DEFAULT_Y = 600;

    /**
     * Default constructor which produces a canvas of the default size of 800 x
     * 600.
     */
    public Canvas(JFrame frame) {
        this(DEFAULT_X, DEFAULT_Y);
    }

    /**
     * Constructor which produces a canvas of a specified size.
     *
     * @param x Width of the canvas.
     * @param y Height of the canvas.
     */
    public Canvas(int x, int y) {
        xSize = x;
        ySize = y;
        setupCanvas();
        lines = Collections.synchronizedList(new ArrayList<LineSegment>());
        birds = Collections.synchronizedList(new ArrayList<animals.animal>());
    }
    
    private void setupCanvas() {
        //setSize(xSize, ySize);
        setPreferredSize(new Dimension(xSize, ySize));
        setMaximumSize(new Dimension(xSize, ySize));
        setMinimumSize(new Dimension(xSize, ySize));
        setSize(xSize, ySize);
        setVisible(true);
        repaint();
    }

    /**
     * <b>NB: You never need to call this method yourself.</b> It handles the
     * drawing but is called automatically each time a line segment is drawn.
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Smoother
        // lines
        g2.setStroke(new BasicStroke(3));

        synchronized (birds) {
            for (animals.animal bird : birds) {
                AffineTransform old = g2.getTransform();
                g.translate((int)bird.get_position().getX(), (int)bird.get_position().getY());
                g2.rotate(bird.get_direction_angle()+Math.PI/2);
                g2.drawImage(bird.get_image(), -10, -2, null);
                g2.setTransform(old);
            }
        }
        synchronized (lines) {
            for (LineSegment line : lines) {
                g2.setColor(line.get_colour());
                g2.draw(new Line2D.Double(line.getStartPoint().getX(), line.getStartPoint().getY(),
                        line.getEndPoint().getX(), line.getEndPoint().getY()));
            }
        }
    }

    /**
     * Draws a line between two CartesianCoordinates to the canvas.
     *
     * @param startPoint Starting coordinate.
     * @param endPoint Ending coordinate.
     * @param colour
     */
    public void drawLineBetweenPoints(CartesianCoordinate startPoint, CartesianCoordinate endPoint, Color colour) {
        LineSegment segment = new LineSegment(startPoint, endPoint);
        segment.set_colour(colour);
        synchronized (lines) {
            lines.add(segment);
        }
        repaint();
    }

    /*
    *Returns the segments for the requested polygon
     */
    static public LineSegment[] get_circleLineSegments(CartesianCoordinate centrePoint, float radius, int complexity, double angle, Color colour) {
        LineSegment[] segments = new LineSegment[complexity];
        CartesianCoordinate pointA, pointB;
        double TempAngle = angle;
        for (int i = 0; i < complexity; i++) {
            pointA = new CartesianCoordinate((centrePoint.getX() + (Math.sin(TempAngle - (Math.PI / 2))) * radius), (centrePoint.getY() + (Math.cos(TempAngle - (Math.PI / 2))) * radius));
            pointB = new CartesianCoordinate((centrePoint.getX() + (Math.sin(TempAngle - (Math.PI / 2) + ((2 * Math.PI) / complexity))) * radius),
                    (centrePoint.getY() + (Math.cos(TempAngle - (Math.PI / 2) + ((2 * Math.PI) / complexity))) * radius));
            segments[i] = new LineSegment(pointA, pointB);
            segments[i].set_colour(colour);
            TempAngle = (TempAngle + ((2 * Math.PI) / complexity));
        }

        return segments;

    }

    /*
    Draws a circle or radius, about the centre-point.
    Complexity specifies the number of points to compute.
    This could also be called draw polygon.
     */
    public void draw_circle(CartesianCoordinate centrePoint, float radius, int complexity, double angle, Color colour) {
        LineSegment[] segments = get_circleLineSegments(centrePoint, radius, complexity, angle, colour);
        drawLineSegments(segments);
    }

    public void draw_triangle(CartesianCoordinate centrePoint, float radius, double angle, Color colour0, Color colour1, Color colour2) {
        LineSegment[] segments = get_circleLineSegments(centrePoint, radius, 3, angle, Color.black); //A triangle is a polygon with 3 points.
        segments[1].set_colour(colour1);
        segments[0].set_colour(colour0);
        segments[2].set_colour(colour2);
        drawLineSegments(segments);
    }

    /**
     * Draws a line segment to the canvas.
     *
     * @param lineSegment The LineSegment to draw.
     */
    public void drawLineSegment(LineSegment lineSegment) {
        synchronized (lines) {
            lines.add(lineSegment);
        }
        repaint();
    }

    /**
     * Draws multiple line segments to the canvas.
     *
     * @param lineSegments An array of LineSegment.
     */
    public void drawLineSegments(LineSegment[] lineSegments) {
        for (LineSegment thisLineSegment : lineSegments) {
            synchronized (lines) {
                lines.add(thisLineSegment);
            }
        }
        repaint();
    }

    public void drawBird(animals.animal bird) {
        birds.add(bird);
        repaint();
    }

    /**
     * Removes the most recently added line from the drawing.
     */
    public void removeMostRecentLine() {
        synchronized (lines) {
            lines.remove(lines.size() - 1);
        }
    }

    /**
     * Clears the canvas of all drawing.
     */
    public void clear() {
        synchronized (lines) {
            lines.clear();
        }
        repaint();
    }
}
