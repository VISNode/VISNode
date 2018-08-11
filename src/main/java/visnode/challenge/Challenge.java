package visnode.challenge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Challenge
 */
public class Challenge {

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
    /** Missions */
    private List<Mission> missions;

    public Challenge() {
        this.missions = new ArrayList<>();
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
}
