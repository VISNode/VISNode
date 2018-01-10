package visnode.challenge;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * The challenge scope
 */
public class ChallengeScope {

    /** The scope instance */
    private static ChallengeScope instance;
    /** The challenge data */
    private Challenge challenge;
    /** Has challenge */
    private final BehaviorSubject<Boolean> has;
    
    private ChallengeScope() {
        has = BehaviorSubject.createDefault(Boolean.FALSE);
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
    public static ChallengeScope get() {
        if (instance == null) {
            instance = new ChallengeScope();
        }
        return instance;
    }

}
