/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package antcolony;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ikattey
 */
public class WorldBuilder {

    private Map<String, City> cities;
    private List<Road> roads;

    public WorldBuilder() {
        cities = new HashMap<String, City>();
        roads = new ArrayList<Road>();
    }

    public City addCity(String name) {
        City city = new City(name);
        cities.put(name, city);
        return city;
    }

    public void addCity(City city) {
        cities.put(city.getName(), city);
    }

    public void addCities(List<String> names) {
        for (String name : names) {
            addCity(name);
        }
    }

    public void addCities(String[] names) {
        for (String name : names) {
            addCity(name);
        }
    }

    public Road addRoad(double distance, String fromCityName, String toCityName) {
        City fromCity = null;
        City toCity = null;
        if (!cities.containsKey(fromCityName)) {
            fromCity = addCity(fromCityName);
        }
        if (!cities.containsKey(toCityName)) {
            toCity = addCity(toCityName);
        }

        return addRoad(distance, fromCity, toCity);
    }

    public Road addRoad(double distance, City fromCity, City toCity) {
        Road road = new Road(distance);
        fromCity.addRoad(toCity, road);
        toCity.addRoad(fromCity, road);
        roads.add(road);
        return road;
    }

    public List<City> getCities() {
        return new ArrayList<City>(cities.values());
    }

    public List<Road> getRoads() {
        return roads;
    }
    

}
