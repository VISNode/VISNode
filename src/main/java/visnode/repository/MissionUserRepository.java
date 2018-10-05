package visnode.repository;

import com.google.gson.reflect.TypeToken;
import java.util.List;
import visnode.challenge.Mission;
import visnode.challenge.MissionUser;
import visnode.challenge.Challenge;
import visnode.user.User;
import visnode.ws.WebServiceException;
import visnode.ws.WebService;
import visnode.ws.WebServiceQuery;

/**
 * The challenge user repository
 */
public class MissionUserRepository {

    /** Instance */
    private static MissionUserRepository instance;

    /**
     * Adds a value
     *
     * @param missionUser
     * @throws RepositoryException
     */
    public void put(MissionUser missionUser) throws RepositoryException {
        try {
            WebService.get().post("missionuser", missionUser);
        } catch (WebServiceException ex) {
            throw new RepositoryException("Não foi possível gravar registro!", ex);
        }
    }

    /**
     * Returns true if has a value
     *
     * @param user
     * @param mission
     * @return boolean
     * @throws RepositoryException
     */
    public boolean has(User user, Mission mission) throws RepositoryException {
        return has(user.getName(), mission.getId());
    }
    
    /**
     * Returns true if has a value
     *
     * @param user
     * @param mission
     * @return boolean
     * @throws RepositoryException
     */
    public boolean has(String user, long mission) throws RepositoryException {
        return get(mission).stream().
                anyMatch((it) -> it.getUser().getName().equals(user)
                && it.getIdMission()== mission
                && it.isStatusSuccess());
    }

    /**
     * Returns the challenge
     *
     * @param mission
     * @return {@code List<MissionUser>}
     * @throws RepositoryException
     */
    public List<MissionUser> get(long mission) throws RepositoryException {
        try {
            return WebService.get().
                    get("missionuser",
                            WebServiceQuery.create().
                                    put("idMission", mission).
                                    put("status", MissionUser.STATUS_SUCESS)
                    ).
                    get(new TypeToken<List<MissionUser>>() {
                    });
        } catch (WebServiceException ex) {
            throw new RepositoryException("Não foi possível gravar registro!", ex);
        }
    }
    
    /**
     * Returns the challenge
     *
     * @param mission
     * @return {@code List<MissionUser>}
     * @throws RepositoryException
     */
    public List<MissionUser> getAll(long mission) throws RepositoryException {
        try {
            return WebService.get().
                    get("missionuser",
                            WebServiceQuery.create().
                                    put("idMission", mission)
                    ).
                    get(new TypeToken<List<MissionUser>>() {
                    });
        } catch (WebServiceException ex) {
            throw new RepositoryException("Não foi possível gravar registro!", ex);
        }
    }
  
    /**
     * Returns the challenge
     *
     * @param user
     * @return {@code List<MissionUser>}
     * @throws RepositoryException
     */
    public List<MissionUser> get(User user) throws RepositoryException {
        try {
            return WebService.get().
                    get("missionuser",
                            WebServiceQuery.create().
                                    put("user.id", user.getId()).
                                    put("status", MissionUser.STATUS_SUCESS)
                    ).
                    get(new TypeToken<List<MissionUser>>() {
                    });
        } catch (WebServiceException ex) {
            throw new RepositoryException("Não foi possível gravar registro!", ex);
        }
    }
    
    /**
     * Returns the challenge from a user
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
     * Returns the challenge from a user
     *
     * @param user
     * @param challenge
     * @param level
     * @return {@code List<MissionUser>}
     * @throws RepositoryException
     */
    public List<MissionUser> get(User user, Challenge challenge, int level) throws RepositoryException {
        try {
            return WebService.get().
                    get(
                            "missionuser",
                            WebServiceQuery.create().
                                    put("user.id", user.getId()).
                                    put("idChallenge", challenge.getId()).
                                    put("level", level)
                    ).
                    get(new TypeToken<List<MissionUser>>() {
                    });
        } catch (WebServiceException ex) {
            throw new RepositoryException("Não foi possível gravar registro!", ex);
        }
    }
    
    /**
     * Returns the challenge from a user
     *
     * @param user
     * @param challenge
     * @return {@code List<MissionUser>}
     * @throws RepositoryException
     */
    public List<MissionUser> get(User user, Challenge challenge) throws RepositoryException {
        try {
            return WebService.get().
                    get(
                            "missionuser",
                            WebServiceQuery.create().
                                    put("user.id", user.getId()).
                                    put("idChallenge", challenge.getId()).
                                    put("status", 1)
                    ).
                    get(new TypeToken<List<MissionUser>>() {
                    });
        } catch (WebServiceException ex) {
            throw new RepositoryException("Não foi possível gravar registro!", ex);
        }
    }

    /**
     * Returns the challenge user repository instance
     *
     * @return MissionUserRepository
     */
    public static MissionUserRepository get() {
        if (instance == null) {
            instance = new MissionUserRepository();
        }
        return instance;
    }

}
