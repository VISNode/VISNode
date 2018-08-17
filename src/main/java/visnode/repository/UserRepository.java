package visnode.repository;

import com.google.gson.reflect.TypeToken;
import java.util.List;
import visnode.user.User;
import visnode.ws.WebServiceException;
import visnode.ws.WebService;
import visnode.ws.WebServiceQuery;

/**
 * The user repository
 */
public class UserRepository {

    /** Instance */
    private static UserRepository instance;

    /**
     * Creates a new user
     *
     * @param user
     * @throws RepositoryException
     */
    public void create(User user) throws RepositoryException {
        try {
            WebService.get().post("user/create", user);
        } catch (WebServiceException ex) {
            throw new RepositoryException(ex);
        }
    }
    
    /**
     * Creates a new user
     *
     * @param user
     * @throws RepositoryException
     */
    public void update(User user) throws RepositoryException {
        try {
            WebService.get().post("user/" + user.getId(), user);
        } catch (WebServiceException ex) {
            throw new RepositoryException(ex);
        }
    }

    /**
     * Returns the users
     *
     * @return {@code List<User>}
     * @throws RepositoryException
     */
    public List<User> getAll() throws RepositoryException {
        try {
            return WebService.get().
                    get("user").
                    get(new TypeToken<List<User>>() {
                    });
        } catch (WebServiceException ex) {
            throw new RepositoryException(ex);
        }
    }
    
    /**
     * Returns a user
     *
     * @param userName
     * @return {@code User}
     * @throws RepositoryException
     */
    public User get(String userName) throws RepositoryException {
        try {
            List<User> list = WebService.get().
                    get("user", WebServiceQuery.create().
                                    put("name", userName)).
                    get(new TypeToken<List<User>>() {
                    });
            if (list.isEmpty()) {
                return new User();
            }
            return list.get(0);
        } catch (WebServiceException ex) {
            throw new RepositoryException(ex);
        }
    }

    /**
     * Returns a new instance
     *
     * @return UserRepository
     */
    public static UserRepository get() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

}
