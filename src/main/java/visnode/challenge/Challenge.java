package visnode.challenge;

import com.google.gson.Gson;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import visnode.user.Base64Image;

/**
 * Challenge
 */
public class Challenge {

    /** Gson */
    private final transient Gson gson;
    /** Id */
    private int id;
    /** Name */
    private String name;
    /** Description */
    private String description;
    /** Problem */
    private String problem;
    /** Point */
    private int xp;
    /** Level */
    private int level;
    /** Payment */
    private String payment;
    /** Puzzle */
    private String puzzle; 
    /** Base64 to image */
    private Base64Image base64Image;
    /** Missions */
    private List<Mission> missions;

    public Challenge() {
        this.missions = new ArrayList<>();
        this.gson = new Gson();
    }

    /**
     * Returns the id
     *
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the description
     *
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the problem
     *
     * @return String
     */
    public String getProblem() {
        return problem;
    }

    /**
     * Sets the problem
     *
     * @param problem
     */
    public void setProblem(String problem) {
        this.problem = problem;
    }

    /**
     * Returns the experience
     *
     * @return int
     */
    public int getXp() {
        return xp;
    }

    /**
     * Sets the experience
     *
     * @param xp
     */
    public void setXp(int xp) {
        this.xp = xp;
    }

    /**
     * Returns the mission level
     *
     * @return int
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets the mission level
     *
     * @param level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Returns the missions
     *
     * @return {@code List<Mission>}
     */
    public List<Mission> getMissions() {
        return Collections.unmodifiableList(missions);
    }

    /**
     * Adds a new mission
     *
     * @param mission
     */
    public void addMission(Mission mission) {
        mission.setChallenge(this);
        this.missions.add(mission);
        this.xp += mission.getXp();
        this.level++;
    }

    /**
     * Removes the mission
     *
     * @param mission
     */
    public void removeMission(Mission mission) {
        missions.remove(mission);
        int l = 1;
        for (Mission it : missions) {
            it.setLevel(l++);
        }
        this.level = missions.size();
    }

    /**
     * Sets the missions
     *
     * @param missions
     */
    public void setMissions(List<Mission> missions) {
        this.missions = missions;
    }

    /**
     * Returns number of missions
     *
     * @return int
     */
    public int size() {
        return missions.size();
    }
    
    /**
     * Returns the payment
     * 
     * @return String
     */
    public String getPayment() {
        return payment;
    }
    
    /**
     * Returns true if the payment is available
     * 
     * @return boolean
     */
    public boolean isPaymentAvailable() {
        return payment != null && !payment.isEmpty();
    }
    
    /**
     * Returns the payment image
     * 
     * @return 
     */
    public BufferedImage getPaymentBuffered() {
        return getBase64Image().fromBase64(getPayment());
    }
    
    /**
     * Sets the payment
     * 
     * @param payment 
     */
    public void setPayment(String payment) {
        this.payment = payment;
    }

    /**
     * Returns the challenge puzzle
     * 
     * @return ChallengePuzzle
     */
    public ChallengePuzzle getPuzzle() {
        return gson.fromJson(puzzle, ChallengePuzzle.class);
    }

    /**
     * Sets the challenge puzzle
     * 
     * @param puzzle 
     */
    public void setPuzzle(ChallengePuzzle puzzle) {
        this.puzzle = gson.toJson(puzzle);
    }
    
    private Base64Image getBase64Image() {
        if (base64Image == null) {
            base64Image = new Base64Image();
        }
        return base64Image;
    }
}
