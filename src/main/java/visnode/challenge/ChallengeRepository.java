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
        Challenge challenge = new Challenge();
        challenge.setName("Sobel");
        challenge.setDescription("Convert the image to Sobel");
        challenge.setDifficulty(ChallengeDifficulty.EASY);
        challenge.setInput("/home/jonatabecker/Desktop/Lenna.png");
        challenge.setOutput("/home/jonatabecker/Desktop/LennaSobel.png");
        challenges.add(challenge);
        return challenges;
    }
    
}
