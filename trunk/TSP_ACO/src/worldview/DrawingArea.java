/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package worldview;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

/**
 *
 * @author ikattey
 */
public class DrawingArea extends JPanel implements MouseInputListener {

    List<Point> clickedPoints;
    Dimension preferredSize = new Dimension(967, 495);
    Color gridColor;
    TSPMain controller;

    public DrawingArea(TSPMain controller) {
        super();
        this.controller = controller;
        setBorder(BorderFactory.createMatteBorder(1, 5, 5, 1, Color.DARK_GRAY));
        addMouseListener(this);
        addMouseMotionListener(this);
        setBackground(Color.WHITE);
        setOpaque(true);
        clickedPoints = new ArrayList<Point>();
    }

    @Override
    public Dimension getPreferredSize() {
        return preferredSize;
    }

    protected void paintComponent(Graphics g) {
        // Paint background if we're opaque.
        

        if (isOpaque()) {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }

    //draw lines to all other points
        for (int i = 0; i < clickedPoints.size(); i++) {
            Point p1 = clickedPoints.get(i);
            for (int j = 0; j < clickedPoints.size(); j++) {
                if (j != i) {
                    Point p2 = clickedPoints.get(j);
                    g.setColor(Color.RED);
                    g.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            }
        }

        // If user has chosen a point, paint a small dot on top.
        final int radius = 5;

        for (Point currentPoint : clickedPoints) {
            g.setColor(Color.BLUE);
            g.fillOval(currentPoint.x - radius, currentPoint.y - radius, radius * 2, radius * 2);

           // Shape theCircle = new Ellipse2D.Double(currentPoint.x - radius, currentPoint.y - radius, 2.0 * radius, 2.0 * radius);
           // g2d.draw(theCircle);

        }

    
    }

    public void mouseClicked(MouseEvent e) {
        Point currentPoint;
        int x = e.getX();
        int y = e.getY();

        currentPoint = new Point(x, y);

        controller.addClickPoint(currentPoint);
        clickedPoints.add(currentPoint);
        repaint();
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }
}
