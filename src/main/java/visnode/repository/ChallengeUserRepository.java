package visnode.repository;

import com.google.gson.reflect.TypeToken;
import java.util.List;
import visnode.challenge.Challenge;
import visnode.challenge.ChallengeUser;
import visnode.user.User;
import visnode.ws.WebServiceException;
import visnode.ws.WebService;
import visnode.ws.WebServiceQuery;

/**
 * The mission user repository
 */
public class ChallengeUserRepository {

    /** Instance */
    private static ChallengeUserRepository instance;

    /**
     * Returns true if has a value
     *
     * @param user
     * @param mission
     * @return boolean
     * @throws RepositoryException
     */
    public boolean has(User user, Challenge mission) throws RepositoryException {
        return !get(user, mission).isEmpty();
    }
        
    /**
     * Returns the mission from a user
     *
     * @param user
     * @param challenge
     * @return {@code List<ChallengeUser>}
     * @throws RepositoryException
     */
    public List<ChallengeUser> get(User user, Challenge challenge) throws RepositoryException {
        try {
            return WebService.get().
                    get(
                            "challengeuser",
                            WebServiceQuery.create().
                                    put("user.id", user.getId()).
                                    put("idChallenge", challenge.getId())
                    ).
                    get(new TypeToken<List<ChallengeUser>>() {
                    });
        } catch (WebServiceException ex) {
            throw new RepositoryException("Não foi possível gravar registro!", ex);
        }
    }
   
    /**
     * Returns the mission from a user
     *
     * @param user
     * @return {@code List<ChallengeUser>}
     * @throws RepositoryException
     */
    public List<ChallengeUser> get(User user) throws RepositoryException {
        try {
            return WebService.get().
                    get(
                            "challengeuser",
                            WebServiceQuery.create().
                                    put("user.id", user.getId())
                    ).
                    get(new TypeToken<List<ChallengeUser>>() {
                    });
        } catch (WebServiceException ex) {
            throw new RepositoryException("Não foi possível gravar registro!", ex);
        }
    }

    /**
     * Returns the mission
     *
     * @param challenge
     * @return {@code List<ChallengeUser>}
     * @throws RepositoryException
     */
    public List<ChallengeUser> get(Challenge challenge) throws RepositoryException {
        try {
            return WebService.get().
                    get(
                            "challengeuser",
                            WebServiceQuery.create().
                                    put("idChallenge", challenge.getId())
                    ).
                    get(new TypeToken<List<ChallengeUser>>() {
                    });
        } catch (WebServiceException ex) {
            throw new RepositoryException("Não foi possível gravar registro!", ex);
        }
    }

    /**
     * Returns the mission user repository instance
     *
     * @return ChallengeUserRepository
     */
    public static ChallengeUserRepository get() {
        if (instance == null) {
            instance = new ChallengeUserRepository();
        }
        return instance;
    }

}
