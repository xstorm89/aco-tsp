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
public class City {

    private String name;
    private Map<City, Road> roads;

    public String getName() {
        return name;
    }

    public City(String name) {
        this.name = name;
        roads = new HashMap<City, Road>();
    }

    public List<City> getNeighbouringCities() {
        return new ArrayList<City>(roads.keySet());
    }

    public Road getRoad(City city) {
        return roads.get(city);
    }

    public void addRoad(City newCity, Road road)
    {
        roads.put(newCity, road);
    }
}
