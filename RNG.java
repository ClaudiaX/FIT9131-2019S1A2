
/**
 * Write a description of class RNG here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class RNG {

    public static int getRandomNumber(int minimumValue, int maximumValue) {
        return (int) (Math.random() * (maximumValue - minimumValue + 1) + minimumValue);
    }
    
}
