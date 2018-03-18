package visnode.repository;

import visnode.user.User;
import visnode.ws.HttpException;
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
        } catch (HttpException ex) {
            throw new RepositoryException("Não foi possível gravar registro!", ex);
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
