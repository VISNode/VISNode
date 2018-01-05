package visnode.challenge;

/**
 * The challenge information
 */
public class Challenge {
    
    /** The name */
    private String name;
    /** The description */
    private String description;
    /** The difficulty */
    private ChallengeDifficulty difficulty;
    /** Input */
    private String input;
    /** Output */
    private String output;

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
     * @return String
     */
    public String getInput() {
        return input;
    }

    /**
     * Sets the challenge input
     * 
     * @param input 
     */
    public void setInput(String input) {
        this.input = input;
    }

    /**
     * Returns the challenge output
     * 
     * @return String
     */
    public String getOutput() {
        return output;
    }

    /**
     * Sets the challenge output
     * 
     * @param output 
     */
    public void setOutput(String output) {
        this.output = output;
    }
    
}
