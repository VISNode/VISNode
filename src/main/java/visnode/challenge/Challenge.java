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
    private int id;
    /** Name */
    private String name;
    /** Description */
    private String description;
    /** Difficulty */
    private ChallengeDifficulty difficulty;
    /** Input */
    private final List<String> input;
    /** Output */
    private ChallengeValue output;
    /** Problem */
    private String problem;
    /** Point */
    private int xp;

    public Challenge() {
        this.input = new ArrayList<>();
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
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the challenge id
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
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
     * @return {@code List<String>}
     */
    public List<String> getInput() {
        return Collections.unmodifiableList(input);
    }

    /**
     * Adds the challenge input
     *
     * @param input
     */
    public void addInput(String input) {
        this.input.add(input);
    }

    /**
     * Returns the challenge output
     *
     * @return ChallengeValue
     */
    public ChallengeValue getOutput() {
        return output;
    }

    /**
     * Sets the challenge output
     *
     * @param output
     */
    public void setOutput(ChallengeValue output) {
        this.output = output;
    }

    /**
     * Returns the problem md file
     *
     * @return String
     */
    public String getProblem() {
        return problem;
    }

    /**
     * Sets the problem md file
     *
     * @param problem
     */
    public void setProblem(String problem) {
        this.problem = problem;
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

}
