/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package worldview;

import antcolony.City;
import java.awt.Point;
import java.util.Map;
import org.apache.commons.collections.keyvalue.DefaultKeyValue;

/**
 *
 * @author ikattey
 */
public class CityView {

    public static final int radius = 6;
    private Point location;
    private String name;
    private City tspCity;

    public CityView(Point location, String name, City tspCity) {
        this.location = location;
        this.name = name;
        this.tspCity = tspCity;
    }

    public static double getDistance(Point p1, Point p2)
    {
        //using pythagoras' theorem: c² = a² + b², we calculate the distance by
        //calculating for the third side (which is the hypotenuse of the right triangle)
      //  return Math.sqrt(Math.pow((p1.X - p2.X), 2) + Math.Pow((pt1.Y - pt2.Y), 2));
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

    public static double getDistance(DefaultKeyValue line)
    {
        return getDistance((Point)line.getKey(), (Point)line.getValue());
    }
    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getTspCity() {
        return tspCity;
    }

    public void setTspCity(City tspCity) {
        this.tspCity = tspCity;
    }
}
