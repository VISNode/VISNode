package visnode.repository;

import com.google.gson.reflect.TypeToken;
import java.util.List;
import visnode.challenge.ChallengeUser;
import visnode.ws.HttpException;
import visnode.ws.WebService;
import visnode.ws.WebServiceQuery;

/**
 * The challenge user repository
 */
public class ChallengeUserRepository {

    /** Instance */
    private static ChallengeUserRepository instance;

    /**
     * Adds a value
     *
     * @param challengeUser
     * @throws RepositoryException
     */
    public void put(ChallengeUser challengeUser) throws RepositoryException {
        try {
            WebService.get().post("challengeuser", challengeUser);
        } catch (HttpException ex) {
            throw new RepositoryException("Não foi possível gravar registro!", ex);
        }
    }

    /**
     * Returns true if has a value
     *
     * @param user
     * @param challenge
     * @return boolean
     * @throws RepositoryException
     */
    public boolean has(String user, int challenge) throws RepositoryException {
        return get(challenge).stream().
                anyMatch((it) -> it.getUser().equals(user) && it.getChallenge() == challenge);
    }

    /**
     * Returns the challenge
     *
     * @param challenge
     * @return {@code List<ChallengeUser>}
     * @throws RepositoryException
     */
    public List<ChallengeUser> get(int challenge) throws RepositoryException {
        try {
            return WebService.get().
                    get(
                            "challengeuser",
                            WebServiceQuery.create().
                                    put("idChallenge", challenge)
                    ).
                    get(new TypeToken<List<ChallengeUser>>() {
                    });
        } catch (HttpException ex) {
            throw new RepositoryException("Não foi possível gravar registro!", ex);
        }
    }

    /**
     * Returns the challenge user repository instance
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
