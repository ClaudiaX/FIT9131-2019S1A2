
/**
 * Write a description of class Driver here.
 *
 * @author Qingyun Xu
 * @version (a version number or a date)
 */
public class Driver {

    private String name;
    private int ranking;
    private String specialSkill;
    private boolean eligibleToRace;
    private int accumulatedScore;
    private int accumulatedTime;
    private boolean wetTyer;

    public Driver() {
        this.name = "";
        this.ranking = 0;
        this.specialSkill = "";
        this.eligibleToRace = true;
        this.accumulatedScore = 0;
        this.accumulatedTime = 0;
        this.wetTyer = false;
    }

    public Driver(String name, int ranking, String specialSkill, boolean eligibleToRace, int accumulatedScore, int accumulatedTime, boolean wetTyer) {
        this.name = name;
        this.ranking = ranking;
        this.specialSkill = specialSkill;
        this.eligibleToRace = eligibleToRace;
        this.accumulatedScore = accumulatedScore;
        this.accumulatedTime = accumulatedTime;
        this.wetTyer = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getSpecialSkill() {
        return specialSkill;
    }

    public void setSpecialSkill(String specialSkill) {
        this.specialSkill = specialSkill;
    }

    public boolean isEligibleToRace() {
        return eligibleToRace;
    }

    public void setEligibleToRace(boolean eligibleToRace) {
        this.eligibleToRace = eligibleToRace;
    }

    public int getAccumulatedScore() {
        return accumulatedScore;
    }

    public void setAccumulatedScore(int accumulatedScore) {
        this.accumulatedScore = accumulatedScore;
    }

    public void addAccumulatedScore(int score) {
        this.accumulatedScore += score;
    }

    public int getAccumulatedTime() {
        return accumulatedTime;
    }

    public void setAccumulatedTime(int accumulatedTime) {
        this.accumulatedTime = accumulatedTime;
    }

    public void addAccumulatedTime(int time) {
        this.accumulatedTime += time;
    }

    public boolean isWetTyer() {
        return wetTyer;
    }

    public void setWetTyer(boolean wetTyer) {
        this.wetTyer = wetTyer;
    }

}
