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
import java.util.Observable;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author ikattey
 */
public class World extends Observable {

    public static final double a = -1.5;
    public static final double b = 1.5;
    public static final int numberOfAnts = 100;
    public static final double initialPheromoneValue = 1;
    public static final double pheromoneDecayFactor = 0.1;
    public static final double maxIterations = 20;
    private List<City> cities;
    private List<Road> roads;
    private double worstTourValue;
    private double weightingFactor;
    private Random random;

    public World(List<City> cities, List<Road> roads) {
        random = new Random();
        this.cities = new ArrayList<City>(cities);
        this.roads = new ArrayList<Road>(roads);
        worstTourValue = sumRoadDistances();
        weightingFactor = cities.size() / roads.size();

    }

    public World(WorldBuilder worldBuilder) {
        this(worldBuilder.getCities(), worldBuilder.getRoads());
    }

    private double sumRoadDistances() {
        double distanceSum = 0.0;

        for (int i = 0; i < roads.size(); i++) {
            distanceSum += roads.get(i).getDistance();
        }
        return distanceSum;
    }

    public List<City> findTour() {
        List<City> bestTour = null;
        double bestTourValue = worstTourValue;
        int iterations = 0;
        int iterationsWithoutChange = 0;
        int numberOfFailures = 0;
        double mostRecentTourValue = 0;
        int numberOfSucesses = 0;

        for (Road road : roads) {
            road.setPheromoneLevel(initialPheromoneValue);
        }

        double antPheromoneCapacity = 0.2;
        double overallDecayValue = pheromoneDecayFactor * initialPheromoneValue * roads.size();

        while (iterationsWithoutChange < maxIterations) {
            iterations++;
            List<Ant> ants = new ArrayList<Ant>(numberOfAnts);
            Map<Ant, Boolean> antSuccess = new HashMap<Ant, Boolean>(numberOfAnts);
            int randomIndex;

            for (int i = 0; i < numberOfAnts; i++) {
                // a random starting city is picked  in order to distribute the pheromones for the
                //last route of the tour randomly.
                randomIndex = (int) (random.nextDouble() * cities.size());
                ants.add(new Ant(antPheromoneCapacity, cities.get(randomIndex)));
            }

            mostRecentTourValue = 0;
            numberOfSucesses = 0;

            //Iterator itr = ants.iterator();
            for (Iterator itr = ants.iterator(); itr.hasNext();) {
                Ant ant = (Ant) itr.next();
                boolean success = ant.searchTour() && ant.getVisitedCities().size() == cities.size();
                antSuccess.put(ant, success);
                if (success) {
                    double delta = ant.getTourValue() - bestTourValue;
                    if (Math.abs(delta) <= 0.01) {
                        iterationsWithoutChange++;
                    } else if (delta < 0) {
                        bestTourValue = ant.getTourValue();
                        bestTour = ant.getVisitedCities();
                        iterationsWithoutChange = 0;
                    } else if (delta <= bestTourValue * 0.01) {
                        // The difference is too small to yield correct phermone
                        // values (that is: the tour is nearly as good as the
                        // current best one): We give up.
                        iterationsWithoutChange++;
                    } else {
                        iterationsWithoutChange = 0;
                    }

                    mostRecentTourValue += ant.getTourValue();
                    numberOfSucesses++;
                } else {
                    iterationsWithoutChange = 0;
                    numberOfFailures++;
                }
            }

            mostRecentTourValue /= numberOfSucesses;

            //  for (Iterator itr = roads.iterator(); itr.hasNext();) {
            for (Road road : roads) {
                //    Road road = (Road) itr.next();
                boolean isRoadInBestTour = false;

                if (bestTour != null && bestTour.size() > 0) {
                    City firstCity = bestTour.get(0);

                    for (int i = 0; i < bestTour.size(); i++) {
                        if (firstCity.getRoad(bestTour.get(i)) == road) {
                            isRoadInBestTour = true;
                            break;
                        }
                        firstCity = bestTour.get(i);
                    }

                    if (bestTour.get(bestTour.size() - 1).getRoad(bestTour.get(0)) == road) {
                        isRoadInBestTour = true;
                    }
                }

                if (!isRoadInBestTour) {
                    updatePheromoneLevel(road);
                }
            }

            double individualPheromoneLevel = overallDecayValue;

            Set entries = antSuccess.entrySet();
            Iterator it = entries.iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                Ant annotatedAnt = (Ant) entry.getKey();

                if ((Boolean) entry.getValue()) {
                    annotatedAnt.setPheromones(individualPheromoneLevel);
                    List<City> visitedCities = annotatedAnt.getVisitedCities();
                    double tourBonus = TourPheromoneBonus(annotatedAnt);
                    for (int i = 0; i < visitedCities.size(); i++) {
                        Road road = visitedCities.get(i).getRoad(visitedCities.get(i));
                        road.setPheromoneLevel(road.getPheromoneLevel() + tourBonus);
                    }

                    Road lastRoadTravelled = visitedCities.get(visitedCities.size() - 1).getRoad(visitedCities.get(0));
                    lastRoadTravelled.setPheromoneLevel(lastRoadTravelled.getPheromoneLevel() + tourBonus);
                }
            }
            raiseUpdate();
        }
        return bestTour;
    }

    private void updatePheromoneLevel(Road road) {
        final double remainingPheromoneFactor = 1.0 - pheromoneDecayFactor;
        road.setPheromoneLevel(road.getPheromoneLevel() * remainingPheromoneFactor);
    }

    private void raiseUpdate() {
        setChanged();
        notifyObservers();
    }

    private double TourPheromoneBonus(Ant annotatedAnt) {
        // We penalize long tours and try to get the worst possible tour
        // to yield 0 pheromone.
        // The square tries to "stretch" the range of possible bonuses.
        return Math.pow((annotatedAnt.getPheromones() * (worstTourValue / annotatedAnt.getTourValue()) - 1), 2);
    }
}
