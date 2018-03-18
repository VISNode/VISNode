package visnode.challenge;

import visnode.repository.ChallengeUserRepository;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import visnode.application.VISNode;
import visnode.commons.DynamicValue;
import visnode.user.UserController;

/**
 * The challenge scope
 */
public class ChallengeController {

    /** The scope instance */
    private static ChallengeController instance;
    /** The challenge data */
    private Challenge challenge;
    /** Has challenge */
    private final BehaviorSubject<Boolean> has;
    /** The challenge run */
    private final ChallengeComparator comparator;

    private ChallengeController() {
        this.has = BehaviorSubject.createDefault(Boolean.FALSE);
        this.comparator = new ChallengeComparator();
    }

    /**
     * Returns true if had a challenge
     *
     * @return Observable
     */
    public Observable<Boolean> hasChallenge() {
        return has;
    }

    /**
     * Starts a challenge
     *
     * @param challenge
     */
    public void start(Challenge challenge) {
        this.challenge = challenge;
        has.onNext(Boolean.TRUE);
    }

    /**
     * Execute the challenge comparation
     *
     * @param ouput
     * @return boolean
     */
    public boolean comparate(DynamicValue ouput) {
        if (!comparator.comparate(challenge, ouput)) {
            return false;
        }
        ChallengeUserRepository.get().put(ChallengeUserBuilder.create().
                user(UserController.get().getUser()).
                challenge(getChallenge().getId()).
                submission(VISNode.get().getModel().getNetwork()).
                build());
        return true;
    }

    /**
     * Ends a challenge
     *
     */
    public void end() {
        this.challenge = null;
        has.onNext(Boolean.FALSE);
    }

    /**
     * Returns the challenge
     *
     * @return Challenge
     */
    public Challenge getChallenge() {
        return challenge;
    }

    /**
     * Returns the singleton instance
     *
     * @return ChallangeScope
     */
    public static ChallengeController get() {
        if (instance == null) {
            instance = new ChallengeController();
        }
        return instance;
    }

}
