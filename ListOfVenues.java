
import java.util.ArrayList;

/**
 * Write a description of class ListOfVenue here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ListOfVenues {

    private ArrayList<Venue> venues;

    public ListOfVenues() {
        this.venues = new ArrayList<>();
    }

    public ListOfVenues(ArrayList<Venue> venues) {
        this.venues = venues;
    }

    public ArrayList<Venue> getVenues() {
        return venues;
    }

    public void setVenues(ArrayList<Venue> venues) {
        this.venues = venues;
    }

    public void addVenue(String venueName, int noOfLaps, int averageLapTime, double chanceOfRain) {
        Venue venue = new Venue(venueName, noOfLaps, averageLapTime, chanceOfRain);
        this.venues.add(venue);
    }

    public int getSize() {
        return this.venues.size();
    }

    public Venue getVenueByIndex(int index) {
        return this.venues.get(index);
    }

    public int getVenueTotalLaps(int index) {
        return getVenueByIndex(index).getNoOfLaps();
    }

    public int getVenueAvgLapTime(int index) {
        return getVenueByIndex(index).getAverageLapTime();
    }

    public double getVenueChanceOfRain(int index) {
        return getVenueByIndex(index).getChanceOfRain();
    }

    public void removeVenue(int index) {
        this.venues.remove(getVenueByIndex(index));
    }

}
