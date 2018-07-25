package visnode.challenge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Challenge mission
 */
public class Mission {

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
    /** Challenges */
    private List<Challenge> challenges;

    public Mission() {
        this.challenges = new ArrayList<>();
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
     * Returns the challenges
     *
     * @return {@code List<Challenge>}
     */
    public List<Challenge> getChallenges() {
        return Collections.unmodifiableList(challenges);
    }

    /**
     * Adds a new challenge
     *
     * @param challenge
     */
    public void addChallange(Challenge challenge) {
        challenge.setMission(this);
        this.challenges.add(challenge);
        this.xp += challenge.getXp();
        this.level++;
    }

    /**
     * Removes the challenge
     *
     * @param challenge
     */
    public void removeChallenge(Challenge challenge) {
        challenges.remove(challenge);
        int l = 1;
        for (Challenge it : challenges) {
            it.setLevel(l++);
        }
    }

    /**
     * Sets the challenges
     *
     * @param challenges
     */
    public void setChallenges(List<Challenge> challenges) {
        this.challenges = challenges;
    }

    /**
     * Returns number of challenges
     *
     * @return int
     */
    public int size() {
        return challenges.size();
    }
}
