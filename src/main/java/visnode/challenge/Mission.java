package visnode.challenge;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.paim.commons.ImageExporter;
import org.paim.commons.ImageFactory;
import visnode.application.ExceptionHandler;
import visnode.user.User;

/**
 * The mission information
 */
public class Mission {

    /** Owner */
    private User owner;
    /** Id */
    private long id;
    /** Challenge */
    private transient Challenge challenge;
    /** Name */
    private String name;
    /** Description */
    private String description;
    /** Difficulty */
    private ChallengeDifficulty difficulty;
    /** Input */
    private final List<MissionValue> input;
    /** Output */
    private final List<MissionValue> output;
    /** Problem */
    private String problem;
    /** Point */
    private int xp;
    /** Level */
    private int level;

    public Mission() {
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
     * Returns the challenge id
     *
     * @return Challenge
     */
    public Challenge getChallenge() {
        return challenge;
    }

    /**
     * Sets the challenge
     *
     * @param challenge
     */
    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
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
     * @return {@code List<MissionValue>}
     */
    public List<MissionValue> getInput() {
        return Collections.unmodifiableList(input);
    }

    /**
     * Returns the challenge input files
     *
     * @return {@code List<File>}
     */
    public List<File> getInputFiles() {
        List<File> list = new ArrayList<>();
        int index = 0;
        try {
            for (MissionValue value : input) {
                File file = new File(System.getProperty("user.home") + "/.visnode/" + System.getProperty("user.name") + "_" + getId() + "_" + index++ + ".jpg");
                ImageExporter.exportBufferedImage(ImageFactory.buildRGBImage(value.getValueBufferedImage()), file);
                list.add(file);
            }
        } catch (IOException ex) {
            ExceptionHandler.get().handle(ex);
        }
        return list;
    }

    /**
     * Removes the input
     *
     * @param value
     */
    public void removeInput(MissionValue value) {
        input.remove(value);
    }

    /**
     * Adds the challenge input
     *
     * @param input
     */
    public void addInput(MissionValue input) {
        this.input.add(input);
    }

    /**
     * Returns the challenge output
     *
     * @return {@code List<MissionValue>}
     */
    public List<MissionValue> getOutput() {
        return Collections.unmodifiableList(output);
    }

    /**
     * Removes the output
     *
     * @param value
     */
    public void removeOutput(MissionValue value) {
        output.remove(value);
    }

    /**
     * Adds a new challenge output
     *
     * @param output
     */
    public void addOutput(MissionValue output) {
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
        final Mission other = (Mission) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.level != other.level) {
            return false;
        }
        return true;
    }

}
