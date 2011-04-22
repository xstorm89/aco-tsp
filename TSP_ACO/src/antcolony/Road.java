/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package antcolony;

/**
 *
 * @author ikattey
 */
public class Road {

    private double distance;
    private double pheromoneLevel;

    public Road(double distance) {
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getPheromoneLevel() {
        return pheromoneLevel;
    }

    public void setPheromoneLevel(double pheromoneLevel) {
        this.pheromoneLevel = pheromoneLevel;
    }

    public double getWeightedValue() {
        return Math.pow(distance, World.a) * Math.pow(pheromoneLevel, World.b);
    }
}
