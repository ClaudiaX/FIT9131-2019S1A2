
import java.util.ArrayList;

/**
 * Write a description of class ListOfDrivers here.
 *
 * @author Qingyun Xu
 * @version (a version number or a date)
 */
public class ListOfDrivers {

    private ArrayList<Driver> drivers;

    public ListOfDrivers() {
        this.drivers = new ArrayList<>();
    }

    public ListOfDrivers(ArrayList<Driver> drivers) {
        this.drivers = drivers;
    }

    public ArrayList<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(ArrayList<Driver> drivers) {
        this.drivers = drivers;
    }

    public void addDriver(String name, int ranking, String specialSkill) {
        Driver driver = new Driver(name, ranking, specialSkill, true, 0, 0, false);
        this.drivers.add(driver);
    }

    public int getSize() {
        return this.drivers.size();
    }

    public Driver getDriverByIndex(int index) {
        return this.drivers.get(index);
    }

}
