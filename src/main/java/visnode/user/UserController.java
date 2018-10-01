package visnode.user;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import visnode.application.ExceptionHandler;
import visnode.application.VISNode;
import visnode.repository.RepositoryException;
import visnode.repository.UserRepository;
import visnode.ws.WebServiceException;
import visnode.ws.WebService;

/**
 * The user controller
 */
public class UserController {

    /** Instance */
    private static UserController intance;
    /** User name */
    private String userName;
    /** User */
    private User user;
    /** Has challenge */
    private final BehaviorSubject<Boolean> has;

    private UserController() {
        userName = VISNode.get().getModel().getUserPreferences().getUser();
        has = BehaviorSubject.createDefault(userName != null);
    }

    /**
     * Returns true if has a user logged
     *
     * @return boolean
     */
    public Observable<Boolean> isLogged() {
        return has;
    }

    /**
     * Logout
     */
    public void logout() {
        userName = null;
        user = null;
        VISNode.get().getModel().getUserPreferences().setUser(null);
        VISNode.get().getModel().getUserPreferences().setUserToken(null);
        has.onNext(Boolean.FALSE);
    }
    
    /**
     * Executes the Login
     *
     * @param user
     * @param password
     * @return boolean
     */
    public boolean login(String user, String password) {
        try {
            String token = WebService.get().login(user, password);
            this.userName = user;
            VISNode.get().getModel().getUserPreferences().setUser(user);
            VISNode.get().getModel().getUserPreferences().setUserToken(token);
            has.onNext(Boolean.TRUE);
            return true;
        } catch (WebServiceException ex) {
            return false;
        }
    }

    /**
     * Returns the user name
     *
     * @return String
     */
    public String getUserName() {
        return userName;
    }
    
    /**
     * Returns the user information
     * 
     * @return User
     */
    public User getUser() {
        if (userName == null) {
            return new User();
        }
        if (user == null) {
            try {
                user = UserRepository.get().get(userName);
            } catch (RepositoryException ex) {
            }
        }
        if (user == null) {
            return new User();
        }
        return user;
    }

    /**
     * Returns the instance
     *
     * @return UserController
     */
    public static UserController get() {
        if (intance == null) {
            intance = new UserController();
        }
        return intance;
    }
}
