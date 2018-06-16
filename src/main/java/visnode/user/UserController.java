package visnode.user;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import visnode.application.VISNode;
import visnode.ws.WebServiceException;
import visnode.ws.WebService;

/**
 * The user controller
 */
public class UserController {

    /** Instance */
    private static UserController intance;
    /** User */
    private String user;
    /** Has challenge */
    private final BehaviorSubject<Boolean> has;

    private UserController() {
        user = VISNode.get().getModel().getUserPreferences().getUser();
        has = BehaviorSubject.createDefault(user != null);
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
            this.user = user;
            VISNode.get().getModel().getUserPreferences().setUser(user);
            VISNode.get().getModel().getUserPreferences().setUserToken(token);
            has.onNext(Boolean.TRUE);
            return true;
        } catch (WebServiceException ex) {
            return false;
        }
    }

    /**
     * Returns the user
     *
     * @return String
     */
    public String getUser() {
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
