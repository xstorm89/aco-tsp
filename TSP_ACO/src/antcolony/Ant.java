/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package antcolony;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author ikattey
 */
public class Ant {

    private Random random;
    private double pheromones;
    private List<City> visitedCities;
    private City currentPosition;
    private double tourValue;

    public Ant(double pheromones, City startPosition) {
        this.pheromones = pheromones;
        currentPosition = startPosition;
        visitedCities = new ArrayList<City>();
        visitedCities.add(startPosition);

        random = new Random();
    }

    public City getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(City currentPosition) {
        this.currentPosition = currentPosition;
    }

    public double getPheromones() {
        return pheromones;
    }

    public void setPheromones(double pheromones) {
        this.pheromones = pheromones;
    }

    public double getTourValue() {
        return tourValue;
    }

    public void setTourValue(double tourValue) {
        this.tourValue = tourValue;
    }

    public List<City> getVisitedCities() {
        return visitedCities;
    }

    public boolean searchTour() {
        tourValue = 0.0;
        while (canTravelOn()) {
            //do nothing
        }

        Road closingRoad = currentPosition.getRoad(visitedCities.get(0));
        if (closingRoad != null) {
            tourValue += closingRoad.getDistance();
            return true;
        }
        return false;
    }

    private boolean canTravelOn() {
        City nextCity = getNextCity();
        if (nextCity == null) {
            return false;
        }

        currentPosition = nextCity;

        //increment the tour value by the distance to the most recent visited city.
        //get(visitedCities.size()-1) gets the most recent city added to the list
        tourValue += visitedCities.get(visitedCities.size() - 1).getRoad(currentPosition).getDistance();
        visitedCities.add(currentPosition);
        return true;
    }

    private City getNextCity() {
        Map<City, Double> cityWeights = new HashMap<City, Double>();
        double sumOfWeights = 0.0;


        for (City city : currentPosition.getNeighbouringCities()) {
            if (!visitedCities.contains(city)) {
                double weight = currentPosition.getRoad(city).getWeightedValue();
                cityWeights.put(city, weight);
                sumOfWeights += weight;
            }
        }

        double randomNumber = random.nextDouble();
        double sum = 0.0;

        Iterator itr = cityWeights.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry pair = (Map.Entry) itr.next();

            //the entrySet returns Object classes. We convert the key/value pair to doubles
            //before use.
            sum += Double.parseDouble(pair.getValue().toString()) / sumOfWeights;
            if (sum > randomNumber) {
                return (City) pair.getKey();
            }
        }
        return null;
    }
}
