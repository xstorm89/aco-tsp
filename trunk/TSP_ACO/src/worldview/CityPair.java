/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package worldview;

/**
 *
 * @author ikattey
 */
public class CityPair {

    private CityView cityFrom;
    private CityView cityTo;

    public CityPair(CityView cityFrom, CityView cityTo) {
        this.cityFrom = cityFrom;
        this.cityTo = cityTo;
    }

    public CityView getCityFrom() {
        return cityFrom;
    }

    public void setCityFrom(CityView cityFrom) {
        this.cityFrom = cityFrom;
    }

    public CityView getCityTo() {
        return cityTo;
    }

    public void setCityTo(CityView cityTo) {
        this.cityTo = cityTo;
    }
    

}
