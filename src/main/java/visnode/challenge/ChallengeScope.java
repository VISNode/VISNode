package visnode.challenge;

/**
 * The challenge scope
 */
public class ChallengeScope {

    /** The scope instance */
    private static ChallengeScope instance;
    /** The challenge data */
    private Challenge challenge;

    private ChallengeScope() {
    }

    /**
     * Returns true if had a challenge
     *
     * @return boolean
     */
    public boolean hadChallenge() {
        return challenge != null;
    }

    /**
     * Starts a challenge
     * 
     * @param challenge 
     */
    public void start(Challenge challenge) {
        this.challenge = challenge;
    }

    /**
     * Ends a challenge
     * 
     */
    public void end() {
        this.challenge = null;
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
