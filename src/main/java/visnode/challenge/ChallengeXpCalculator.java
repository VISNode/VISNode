package visnode.challenge;

import java.util.List;
import visnode.application.ExceptionHandler;
import visnode.repository.ChallengeUserRepository;
import visnode.repository.RepositoryException;
import visnode.user.User;

/**
 * Class responsible for calculate de user experience
 */
public class ChallengeXpCalculator {
    
    private static final int MAX_ERROR = 3;
    
    /**
     * Returns the challenge experience
     * 
     * @param user
     * @param challenge
     * @return int
     */
    public int calculate(User user, Challenge challenge) {
        try {
            List<ChallengeUser> challenges = ChallengeUserRepository.
                    get().get(user, challenge);
            if (challenges.isEmpty()) {
                return challenge.getXp();
            }
            // Ifs the user already have submited this challenge
            if (challenges.stream().anyMatch((c) -> c.isStatusSuccess())) {
                return 0;
            }
            float size = challenges.size();
            if (size > MAX_ERROR) {
                size = MAX_ERROR;
            }
            size = (10 - size) / 10;
            return (int) Math.floor(challenge.getXp() * size);
        } catch (RepositoryException e) {
            ExceptionHandler.get().handle(e);
        }
        return challenge.getXp();
    }
}
