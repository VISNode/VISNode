package visnode.challenge;

import java.text.SimpleDateFormat;
import java.util.Date;
import visnode.user.User;

/**
 * Challenge user relationship
 */
public class ChallengeUser {

    public static transient final int STATUS_SUCESS = 1;
    public static transient final int STATUS_ERROR = 2;

    /** User */
    private User user;
    /** Challenge */
    private long idChallenge;
    /** Mission */
    private long idMission;
    /** Submission */
    private String submission;
    /** Points */
    private int xp;
    /** Date initial */
    private Date dateInitial;
    /** Date final */
    private Date dateFinal;
    /** Status */
    private int status;
    /** Level */
    private int level;

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
     * Returns the challenge
     *
     * @return long
     */
    public long getChallenge() {
        return idChallenge;
    }

    /**
     * Sets the challenge
     *
     * @param challenge
     */
    public void setChallenge(long challenge) {
        this.idChallenge = challenge;
    }

    /**
     * Returns the mission id
     *
     * @return long
     */
    public long getIdMission() {
        return idMission;
    }

    /**
     * Sets the mission id
     *
     * @param idMission
     */
    public void setIdMission(long idMission) {
        this.idMission = idMission;
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
     * Sets the status error
     */
    public void setStatusError() {
        this.status = STATUS_ERROR;
    }

    /**
     * Sets the status success
     */
    public void setStatusSuccess() {
        this.status = STATUS_SUCESS;
    }

    /**
     * Returns true if the status is success
     *
     * @return boolean
     */
    public boolean isStatusSuccess() {
        return this.status == STATUS_SUCESS;
    }

    /**
     * Returns the level
     *
     * @return int
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets the level
     *
     * @param level
     */
    public void setLevel(int level) {
        this.level = level;
    }
}
