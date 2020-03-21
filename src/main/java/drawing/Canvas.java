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
    private final static int DEFAULT_X = 800;
    private final static int DEFAULT_Y = 600;

    /**
     * Default constructor which produces a canvas of the default size of 800 x
     * 600.
     */
    public Canvas() {
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
    }

    private void setupCanvas() {
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
    Draws a circle or radius, about the centre-point.
    Complexity specifies the number of points to compute.
    This could also be called draw polygon.
    */
    public void draw_circle(CartesianCoordinate centrePoint, float radius, int complexity, Color colour) { 
        float i = 0;
        CartesianCoordinate pointA, pointB;
        LineSegment segment;
        synchronized (lines) {
            while (i < 2 * Math.PI) {
                pointA = new CartesianCoordinate((centrePoint.getX() + (Math.sin(i)) * radius), (centrePoint.getY() + (Math.cos(i)) * radius));
                pointB = new CartesianCoordinate((centrePoint.getX() + (Math.sin(i + ((2 * Math.PI) / complexity))) * radius), 
                        (centrePoint.getY() + (Math.cos(i + ((2 * Math.PI) / complexity))) * radius));
                segment = new LineSegment(pointA, pointB);
                segment.set_colour(colour);
                lines.add(segment);
                i = (float) (i + ((2 * Math.PI) / complexity));
            }
            repaint();
        }
    }

    public void draw_triangle(CartesianCoordinate centrePoint, float radius, float angle, Color colour) {
        draw_circle(centrePoint, radius, 3, colour); //A triangle is a polygon with 3 points.
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
