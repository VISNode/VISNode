package visnode.repository;

import com.google.gson.reflect.TypeToken;
import java.util.List;
import visnode.user.User;
import visnode.ws.WebServiceException;
import visnode.ws.WebService;

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
            WebService.get().post("user", user);
        } catch (WebServiceException ex) {
            throw new RepositoryException(ex);
        }
    }
    
      /**
     * Returns the challenge
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
