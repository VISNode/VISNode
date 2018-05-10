package visnode.challenge;

/**
 * Challenge user relationship
 */
public class ChallengeUser {

    /** User */
    private String user;
    /** Challenge */
    private int challenge;
    /** Submission */
    private String submission;

    /**
     * Returns the user
     *
     * @return String
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the user
     *
     * @param user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Returns the challenge
     *
     * @return int
     */
    public int getChallenge() {
        return challenge;
    }

    /**
     * Sets the challenge
     *
     * @param challenge
     */
    public void setChallenge(int challenge) {
        this.challenge = challenge;
    }

    /**
     * Returns the submission
     *
     * @return String
     */
    public String getSubmission() {
        return submission;
    }

    /**
     * Sets the submission
     *
     * @param submission
     */
    public void setSubmission(String submission) {
        this.submission = submission;
    }

}