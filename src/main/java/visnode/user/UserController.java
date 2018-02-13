package visnode.user;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

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
        has = BehaviorSubject.createDefault(Boolean.FALSE);
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
     * Login
     *
     * @param user
     */
    public void login(String user) {
        this.user = user;
        has.onNext(Boolean.TRUE);
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
