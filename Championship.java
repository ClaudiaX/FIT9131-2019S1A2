
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Scanner;

/**
 * Write a description of class Championship here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Championship {

    private ListOfDrivers drivers;
    private ListOfVenues venues;

    public Championship() {
        drivers = new ListOfDrivers();
        venues = new ListOfVenues();
    }

    public Championship(ListOfDrivers drivers, ListOfVenues venues) {
        this.drivers = drivers;
        this.venues = venues;
    }

    public static void main(String[] args) {
        Championship championship = new Championship();
        championship.startGP();
    }

    public void startGP() {
        readDrivers();
        readVenues();
        int raceNumber = setRaceNumber();
        startRace(raceNumber);
        setRank();
        displayWinner();
        writeDrivers();
        endGame();
    }

    public void startRace(int raceNumber) {
        for (int race = 0; race < raceNumber; race++) {
            // list all venues
            listVenues();
            // select venue
            int venueIndex = selectVenue(race);
            displayVenueDetails(venueIndex);
            // set eligible to race
            setEligibleToRace();
            // set driver penalty based on raking
            setDriverPenalty();
            // chance to rain
            boolean rain = rain(venueIndex);
            //run laps
            for (int lap = 0; lap < venues.getVenueTotalLaps(venueIndex); lap++) {
                System.out.println("===== Lap " + (lap + 1) + " =====");
                // if rain, change tyer
                if (rain && lap == 1) {
                    changeTyer();
                }
                if (rain) {
                    setTyerPenalty();
                }
                // add average time
                addAverageTime(venueIndex);
                // reduce time by skill
                reduceTimeBySkill(lap);
                // set mechanical fault
                setMechanicalFault();
            }
            // sort drivers
            bubbleSortDrivers();
            // add points
            addPoints();
            // display time & score
            displayDrivers();
            // remove used venue
            venues.removeVenue(venueIndex);
        }
    }

    public int setRaceNumber() {
        System.out.println("**** Welcome to Formula 9131 Grand Prix *****");
        Scanner sc = new Scanner(System.in);
        int raceNumber = 0;
        do {
            System.out.print("Please enter the race number between 3 and 5: ");
            raceNumber = intToString(sc.nextLine());
        } while (raceNumber < 3 || raceNumber > 5);
        return raceNumber;
    }

    public void listVenues() {
        System.out.println("\n***** Venues *****");
        for (int i = 0; i < venues.getSize(); i++) {
            System.out.println((i + 1) + ". " + venues.getVenueByIndex(i).getVenueName());
        }
    }

    public int selectVenue(int race) {
        Scanner sc = new Scanner(System.in);
        int venue = 0;
        do {
            System.out.print("Please select venue " + (race + 1) + ": ");
            venue = intToString(sc.nextLine()) - 1;
        } while (venue < 0 || venue > venues.getSize() - 1);
        return venue;
    }

    public void displayVenueDetails(int venueIndex){
        System.out.println("Venue selected: " + venues.getVenueByIndex(venueIndex).getVenueName());
        System.out.println("Total laps: " + venues.getVenueByIndex(venueIndex).getNoOfLaps());
        System.out.println("Average lap time: " + venues.getVenueByIndex(venueIndex).getAverageLapTime());
    }

    public void setTyerPenalty() {
        for (int i = 0; i < drivers.getSize(); i++) {
            if (!drivers.getDriverByIndex(i).isWetTyer()) {
                drivers.getDriverByIndex(i).addAccumulatedTime(5);
                System.out.println(drivers.getDriverByIndex(i).getName() + " loses 5 seconds with dry tyer");
            }
        }
    }

    public boolean rain(int venueIndex) {
        int chance = RNG.getRandomNumber(1, 100);
        if (chance <= venues.getVenueByIndex(venueIndex).getChanceOfRain() * 100) {
            System.out.println("\nRain in this venue!\n");
            return true;
        } else {
            return false;
        }
    }

    public void changeTyer() {
        for (int i = 0; i < drivers.getSize(); i++) {
            int change = RNG.getRandomNumber(0, 1);
            if (change == 1) {
                drivers.getDriverByIndex(i).setWetTyer(true);
                drivers.getDriverByIndex(i).addAccumulatedTime(10);
                System.out.println(drivers.getDriverByIndex(i).getName() + " chooses to change typer, lose 10 seconds.");
            }
        }
    }

    public void setEligibleToRace() {
        for (int i = 0; i < drivers.getSize(); i++) {
            drivers.getDriverByIndex(i).setEligibleToRace(true);
        }
    }

    public void setDriverPenalty() {
        System.out.println("\n==== Ready ====");
        for (int i = 0; i < drivers.getSize(); i++) {
            int penalty = 0;
            switch (drivers.getDriverByIndex(i).getRanking()) {
                case 1:
                penalty = 0;
                break;
                case 2:
                penalty = 3;
                break;
                case 3:
                penalty = 5;
                break;
                case 4:
                penalty = 7;
                break;
                default:
                penalty = 10;
                break;
            }
            drivers.getDriverByIndex(i).setAccumulatedTime(penalty);
            System.out.println(drivers.getDriverByIndex(i).getName() 
                + " ranks at " + drivers.getDriverByIndex(i).getRanking() 
                + " got penalty " + penalty);
        }
    }

    public void addAverageTime(int venueIndex){
        int averageTime = venues.getVenueByIndex(venueIndex).getAverageLapTime();
        for (int i = 0; i < drivers.getSize(); i++){
            drivers.getDriverByIndex(i).addAccumulatedTime(averageTime);
        }
    }

    public void reduceTimeBySkill(int lap) {
        int reduce = 0;
        for (int i = 0; i < drivers.getSize(); i++) {
            if (!drivers.getDriverByIndex(i).getSpecialSkill().equals("Overtaking")) {
                reduce = RNG.getRandomNumber(1, 8);
                drivers.getDriverByIndex(i).addAccumulatedTime(-reduce);
                System.out.println(drivers.getDriverByIndex(i).getName() 
                    + " got time reduce " + reduce
                    + " by " + drivers.getDriverByIndex(i).getSpecialSkill());
            } else if ((lap + 1) % 3 == 0) {
                reduce = RNG.getRandomNumber(10, 20);
                drivers.getDriverByIndex(i).addAccumulatedTime(-reduce);
                System.out.println(drivers.getDriverByIndex(i).getName() 
                    + " got time reduce " + reduce 
                    + " by " + drivers.getDriverByIndex(i).getSpecialSkill());
            }
        }
    }

    public void setMechanicalFault() {
        for (int i = 0; i < drivers.getSize(); i++) {
            int luck = RNG.getRandomNumber(1, 100);
            if (luck <= 5) {
                drivers.getDriverByIndex(i).addAccumulatedTime(20);
                System.out.println(drivers.getDriverByIndex(i).getName() + " got minor mechanical fault, get penalty 20");
            } else if (luck <= 8) {
                drivers.getDriverByIndex(i).addAccumulatedTime(120);
                System.out.println(drivers.getDriverByIndex(i).getName() + " got major mechanical fault, get penalty 120");
            } else if (luck == 9) {
                drivers.getDriverByIndex(i).setEligibleToRace(false);
                System.out.println(drivers.getDriverByIndex(i).getName() + " got serious mechanical fault");
            }
        }
    }

    public void bubbleSortDrivers() {
        for (int i = 0; i < drivers.getSize() - 1; i++) {
            for (int j = 0; j < drivers.getSize() - 1 - i; j++) {
                if (drivers.getDriverByIndex(j).getAccumulatedTime() > drivers.getDriverByIndex(j + 1).getAccumulatedTime()) {
                    Collections.swap(drivers.getDrivers(), j, j + 1);
                }
            }
        }
    }

    public void addPoints() {
        // System.out.println("==== Accumulated Score =====");
        int rank = 0;
        int[] points = {8, 5, 3, 1};
        for (int i = 0; i < drivers.getSize(); i++) {
            if (drivers.getDriverByIndex(i).isEligibleToRace()) {
                drivers.getDriverByIndex(i).addAccumulatedScore(points[rank]);
                rank++;
            }
            if (rank > 3) {
                break;
            }
        }
    }

    public void displayDrivers(){
        System.out.println("===== Results =====");
        for (int i = 0; i < drivers.getSize(); i++){
            System.out.println(drivers.getDriverByIndex(i).getName()
                + " : time " + drivers.getDriverByIndex(i).getAccumulatedTime()
                + ", score " + drivers.getDriverByIndex(i).getAccumulatedScore());
        }
    }

    public void setRank() {
        int rank = 1;
        for (int i = 0; i < drivers.getSize(); i++) {
            if (drivers.getDriverByIndex(i).isEligibleToRace() && rank <= 4) {
                drivers.getDriverByIndex(i).setRanking(rank);
                rank++;
            } else {
                drivers.getDriverByIndex(i).setRanking(5);
            }
        }
    }

    public void displayWinner() {
        int maxScore = 0;
        String name = "";
        for (int i = 0; i < drivers.getSize(); i++) {
            if (drivers.getDriverByIndex(i).getAccumulatedScore() >= maxScore) {
                maxScore = drivers.getDriverByIndex(i).getAccumulatedScore();
                name = drivers.getDriverByIndex(i).getName();
            }
        }
        System.out.println("\nThe winner is: " + name + " with score " + maxScore);
    }

    public void endGame() {
        System.out.println("*****Thanks for playing!*****");
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        do {
            System.out.println("Press 0 to exit");
            System.out.println("Press 1 to start a new GP");
            choice = intToString(sc.nextLine());
        } while (choice != 0 && choice != 1);
        if (choice == 1) {
            startGP();
        } else {
            System.exit(0);
        }

    }

    public int intToString(String input) {
        int returnInt = 0;
        try {
            returnInt = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            returnInt = -1;
        }
        return returnInt;
    }

    public void readDrivers() {
        drivers = new ListOfDrivers();
        try {
            FileReader fileReader = new FileReader("drivers.txt");
            Scanner sc = new Scanner(fileReader);
            while (sc.hasNextLine()) {
                String[] driverLine = sc.nextLine().split(",");
                String name = driverLine[0];
                int ranking = Integer.parseInt(driverLine[1]);
                String specialSkill = driverLine[2];
                drivers.addDriver(name, ranking, specialSkill);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to read drivers file!");
        }
    }

    public void readVenues() {
        venues = new ListOfVenues();
        try {
            FileReader fileReader = new FileReader("venues.txt");
            Scanner sc = new Scanner(fileReader);
            while (sc.hasNextLine()) {
                String[] venueLine = sc.nextLine().split(",");
                String venueName = venueLine[0];
                int noOfLaps = Integer.parseInt(venueLine[1]);
                int averageLapTime = Integer.parseInt(venueLine[2]);
                double chanceOfRain = Double.parseDouble(venueLine[3]);
                venues.addVenue(venueName, noOfLaps, averageLapTime, chanceOfRain);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to read venues file!");
        }
    }

    public void writeDrivers() {
        try {
            PrintWriter pw = new PrintWriter("drivers.txt");
            int index = 0;
            while (index < drivers.getSize()) {
                pw.println(drivers.getDriverByIndex(index).getName() + "," + drivers.getDriverByIndex(index).getRanking() + "," + drivers.getDriverByIndex(index).getSpecialSkill());
                index++;
            }
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write drivers file!");
        }
    }

}
