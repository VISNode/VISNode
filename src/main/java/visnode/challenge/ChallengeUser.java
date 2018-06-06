package visnode.challenge;

import java.util.Date;

/**
 * Challenge user relationship
 */
public class ChallengeUser {

    /** User */
    private String user;
    /** Challenge */
    private int idChallenge;
    /** Submission */
    private String submission;
    /** Points */
    private int xp;
    /** Date initial */
    private Date dateInitial;
    /** Date final */
    private Date dateFinal;

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
        return idChallenge;
    }

    /**
     * Sets the challenge
     *
     * @param challenge
     */
    public void setChallenge(int challenge) {
        this.idChallenge = challenge;
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

    /**
     * Returns the points
     *
     * @return int
     */
    public int getXp() {
        return xp;
    }

    /**
     * Sets the points
     *
     * @param xp
     */
    public void setXp(int xp) {
        this.xp = xp;
    }

    /**
     * Returns the initial date
     *
     * @return Date
     */
    public Date getDateInitial() {
        return dateInitial;
    }

    /**
     * Sets the initial date
     *
     * @param dateInitial
     */
    public void setDateInitial(Date dateInitial) {
        this.dateInitial = dateInitial;
    }

    /**
     * Returns the final date
     *
     * @return Date
     */
    public Date getDateFinal() {
        return dateFinal;
    }

    /**
     * Sets the final date
     *
     * @param dateFinal
     */
    public void setDateFinal(Date dateFinal) {
        this.dateFinal = dateFinal;
    }
}
