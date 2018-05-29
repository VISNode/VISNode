package visnode.repository;

import java.util.ArrayList;
import java.util.List;
import visnode.challenge.Challenge;
import visnode.challenge.ChallengeDifficulty;
import visnode.challenge.ChallengeValue;
import visnode.challenge.ChallengeValueType;

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
        challenges.add(getDayOrNight());
        challenges.add(getCoin());
        return challenges;
    }

    /**
     * Returns a challenge
     *
     * @return Challenge
     */
    private Challenge getObjectsSegmentation() {
        Challenge challenge = new Challenge();
        challenge.setId(1);
        challenge.setName("Segmentação de objetos");
        challenge.setDescription("Execute a segmentação de objetos de uma imagem");
        challenge.setDifficulty(ChallengeDifficulty.EASY);
        challenge.setInput(getFile("/challenges/objectsSegmentation/input.png"));
        challenge.setOutput(valueImage(getFile("/challenges/objectsSegmentation/output.png")));
        challenge.setProblem("https://raw.githubusercontent.com/VISNode/VISNode/master/src/main/resources/challenges/objectsSegmentation/problem.md");
        challenge.setXp(50);
        return challenge;
    }
  
    /**
     * Returns a challenge
     *
     * @return Challenge
     */
    private Challenge getDayOrNight() {
        Challenge challenge = new Challenge();
        challenge.setId(2);
        challenge.setName("Dia ou noite");
        challenge.setDescription("Duscubra se é dia ou noite");
        challenge.setDifficulty(ChallengeDifficulty.EASY);
        challenge.setInput(getFile("/challenges/dayOrNight/input.jpg"));
        challenge.setOutput(value("1"));
        challenge.setProblem("https://raw.githubusercontent.com/VISNode/VISNode/master/src/main/resources/challenges/dayOrNight/problem.md");
        challenge.setXp(75);
        return challenge;
    }

    /**
     * Returns a coin
     *
     * @return Challenge
     */
    private Challenge getCoin() {
        Challenge challenge = new Challenge();
        challenge.setId(3);
        challenge.setName("Valor monetário");
        challenge.setDescription("Cálcule o valor das moedas");
        challenge.setDifficulty(ChallengeDifficulty.HARD);
        challenge.setInput(getFile("/challenges/coin/input.jpg"));
        challenge.setOutput(value("R$ 2,90"));
        challenge.setProblem("https://raw.githubusercontent.com/VISNode/VISNode/master/src/main/resources/challenges/coin/problem.md");
        challenge.setXp(100);
        return challenge;
    }

    /**
     * Create a challenge value
     *
     * @param value
     * @return ChallengeValue
     */
    private ChallengeValue valueImage(String value) {
        return new ChallengeValue(ChallengeValueType.IMAGE, value);
    }

    /**
     * Create a challenge value
     *
     * @param value
     * @return ChallengeValue
     */
    private ChallengeValue value(String value) {
        return new ChallengeValue(ChallengeValueType.TEXT, value);
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
