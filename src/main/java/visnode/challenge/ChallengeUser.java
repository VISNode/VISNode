package visnode.challenge;

import java.text.SimpleDateFormat;
import java.util.Date;
import visnode.user.User;

/**
 * Challenge user relationship
 */
public class ChallengeUser {

    public static transient final int STATUS_SUCESS = 1;

    /** User */
    private User user;
    /** Mission */
    private int idChallenge;
    /** Submission */
    private String submission;
    /** Points */
    private int xp;
    /** Date final */
    private Date dateFinal;
    /** Status */
    private int status;

    /**
     * Returns the user
     *
     * @return User
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Returns the mission id
     *
     * @return int
     */
    public int getIdChallenge() {
        return idChallenge;
    }

    /**
     * Sets the mission id
     *
     * @param idChallenge
     */
    public void setIdChallenge(int idChallenge) {
        this.idChallenge = idChallenge;
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
     * Returns the final date
     *
     * @return Date
     */
    public Date getDateFinal() {
        return dateFinal;
    }

    /**
     * Returns the date final format
     *
     * @return String
     */
    public String getDateFinalFormat() {
        SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dt1.format(getDateFinal());
    }

    /**
     * Sets the final date
     *
     * @param dateFinal
     */
    public void setDateFinal(Date dateFinal) {
        this.dateFinal = dateFinal;
    }

    /**
     * Returns the status
     *
     * @return int
     */
    public int getStatus() {
        return status;
    }

    /**
     * Returns true if the status is success
     *
     * @return boolean
     */
    public boolean isStatusSuccess() {
        return this.status == STATUS_SUCESS;
    }

}
