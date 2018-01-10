package visnode.challenge;

import java.util.ArrayList;
import java.util.List;

/**
 * The challenge repository
 */
public class ChallengeRepository {

    /**
     * Returns the challenges
     *
     * @return {@code List<Challenge>}
     */
    public List<Challenge> getChallenges() {
        List<Challenge> challenges = new ArrayList<>();
        challenges.add(getObjectsSegmentation());
        return challenges;
    }
    
    /**
     * Returns a challenge
     * 
     * @return Challenge
     */
    private Challenge getObjectsSegmentation() {
        Challenge challenge = new Challenge();
        challenge.setName("Segmentação de objetos");
        challenge.setDescription("Execute a segmentação de objetos de uma imagem");
        challenge.setDifficulty(ChallengeDifficulty.EASY);
        challenge.setInput(getFile("/challenges/objectsSegmentation/input.png"));
        challenge.setOutput(getFile("/challenges/objectsSegmentation/output.png"));
        challenge.setProblem("https://raw.githubusercontent.com/VISNode/VISNode/master/src/main/resources/visnode/pdi/process/AverageBlurProcess_en_US.md");
        return challenge;
    }

    /**
     * Returns the resource file nae
     *
     * @param file
     * @return String
     */
    private String getFile(String file) {
        return getClass().getResource(file).getFile();
    }

}
