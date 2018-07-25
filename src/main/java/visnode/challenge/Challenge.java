package visnode.challenge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import visnode.user.User;

/**
 * The challenge information
 */
public class Challenge {

    /** Owner */
    private User owner;
    /** Id */
    private long id;
    /** Mission */
    private transient Mission mission;
    /** Name */
    private String name;
    /** Description */
    private String description;
    /** Difficulty */
    private ChallengeDifficulty difficulty;
    /** Input */
    private final List<ChallengeValue> input;
    /** Output */
    private final List<ChallengeValue> output;
    /** Problem */
    private String problem;
    /** Point */
    private int xp;
    /** Level */
    private int level;

    public Challenge() {
        this.input = new ArrayList<>();
        this.output = new ArrayList<>();
    }

    /**
     * Returns the owner
     *
     * @return User
     */
    public User getOwner() {
        return owner;
    }

    /**
     * Sets the owner
     *
     * @param owner
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }

    /**
     * Returns the challenge id
     *
     * @return long
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the challenge id
     *
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns the mission id
     *
     * @return Mission
     */
    public Mission getMission() {
        return mission;
    }

    /**
     * Sets the mission
     *
     * @param mission
     */
    public void setMission(Mission mission) {
        this.mission = mission;
    }

    /**
     * Returns the challenge name
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the challenge name
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the challenge description
     *
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the challenge description
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the challenge difficulty
     *
     * @return ChallengeDifficulty
     */
    public ChallengeDifficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Sets the challenge difficulty
     *
     * @param difficulty
     */
    public void setDifficulty(ChallengeDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Returns the challenge input
     *
     * @return {@code List<ChallengeValue>}
     */
    public List<ChallengeValue> getInput() {
        return Collections.unmodifiableList(input);
    }

    /**
     * Removes the input
     *
     * @param value
     */
    public void removeInput(ChallengeValue value) {
        input.remove(value);
    }

    /**
     * Adds the challenge input
     *
     * @param input
     */
    public void addInput(ChallengeValue input) {
        this.input.add(input);
    }

    /**
     * Returns the challenge output
     *
     * @return {@code List<ChallengeValue>}
     */
    public List<ChallengeValue> getOutput() {
        return Collections.unmodifiableList(output);
    }

    /**
     * Removes the output
     *
     * @param value
     */
    public void removeOutput(ChallengeValue value) {
        output.remove(value);
    }

    /**
     * Adds a new challenge output
     *
     * @param output
     */
    public void addOutput(ChallengeValue output) {
        this.output.add(output);
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
     * Returns true if the problem is a url
     *
     * @return boolean
     */
    public boolean isProblemUrl() {
        return problem != null && problem.startsWith("http");
    }

    /**
     * Returns the points
     *
     * @return int
     */
    public int getXp() {
        return xp;
    }

    /**
     * Sets the points
     *
     * @param xp
     */
    public void setXp(int xp) {
        this.xp = xp;
    }

    /**
     * Returns the level
     *
     * @return int
     */
    public int getLevel() {
        return level;
    }

    /**
     * Returns true if is the first level
     *
     * @return boolean
     */
    public boolean isFirtLevel() {
        return level == 1;
    }

    /**
     * Sets the level
     *
     * @param level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 37 * hash + this.level;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Challenge other = (Challenge) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.level != other.level) {
            return false;
        }
        return true;
    }

    
    
}
