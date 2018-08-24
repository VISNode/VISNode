package visnode.challenge;

import java.util.List;
import visnode.application.ExceptionHandler;
import visnode.repository.MissionUserRepository;
import visnode.repository.RepositoryException;
import visnode.user.User;

/**
 * Class responsible for calculate de user experience
 */
public class MissionXpCalculator {
    
    private static final int MAX_ERROR = 3;
    
    /**
     * Returns the mission experience
     * 
     * @param user
     * @param mission
     * @return int
     */
    public int calculate(User user, Mission mission) {
        try {
            List<MissionUser> challenges = MissionUserRepository.
                    get().get(user, mission);
            if (challenges.isEmpty()) {
                return mission.getXp();
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
            return (int) Math.floor(mission.getXp() * size);
        } catch (RepositoryException e) {
            ExceptionHandler.get().handle(e);
        }
        return mission.getXp();
    }
}
