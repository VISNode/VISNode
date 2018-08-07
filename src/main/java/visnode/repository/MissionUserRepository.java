package visnode.repository;

import com.google.gson.reflect.TypeToken;
import java.util.List;
import visnode.challenge.Mission;
import visnode.challenge.MissionUser;
import visnode.user.User;
import visnode.ws.WebServiceException;
import visnode.ws.WebService;
import visnode.ws.WebServiceQuery;

/**
 * The mission user repository
 */
public class MissionUserRepository {

    /** Instance */
    private static MissionUserRepository instance;

    /**
     * Returns true if has a value
     *
     * @param user
     * @param mission
     * @return boolean
     * @throws RepositoryException
     */
    public boolean has(User user, Mission mission) throws RepositoryException {
        return !get(user, mission).isEmpty();
    }
        
    /**
     * Returns the mission from a user
     *
     * @param user
     * @param mission
     * @return {@code List<MissionUser>}
     * @throws RepositoryException
     */
    public List<MissionUser> get(User user, Mission mission) throws RepositoryException {
        try {
            return WebService.get().
                    get(
                            "missionuser",
                            WebServiceQuery.create().
                                    put("user.id", user.getId()).
                                    put("idMission", mission.getId())
                    ).
                    get(new TypeToken<List<MissionUser>>() {
                    });
        } catch (WebServiceException ex) {
            throw new RepositoryException("Não foi possível gravar registro!", ex);
        }
    }
   
    /**
     * Returns the mission from a user
     *
     * @param user
     * @return {@code List<MissionUser>}
     * @throws RepositoryException
     */
    public List<MissionUser> get(User user) throws RepositoryException {
        try {
            return WebService.get().
                    get(
                            "missionuser",
                            WebServiceQuery.create().
                                    put("user.id", user.getId())
                    ).
                    get(new TypeToken<List<MissionUser>>() {
                    });
        } catch (WebServiceException ex) {
            throw new RepositoryException("Não foi possível gravar registro!", ex);
        }
    }

    /**
     * Returns the mission
     *
     * @param mission
     * @return {@code List<MissionUser>}
     * @throws RepositoryException
     */
    public List<MissionUser> get(Mission mission) throws RepositoryException {
        try {
            return WebService.get().
                    get(
                            "missionuser",
                            WebServiceQuery.create().
                                    put("idMission", mission.getId())
                    ).
                    get(new TypeToken<List<MissionUser>>() {
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
    public static MissionUserRepository get() {
        if (instance == null) {
            instance = new MissionUserRepository();
        }
        return instance;
    }

}
